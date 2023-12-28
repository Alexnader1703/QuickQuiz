package com.example.quickquiz;

import static com.example.quickquiz.QuestionParser.parseQuestions;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class QuizActivity extends AppCompatActivity {

    private TextView time;
    private int correctAnswersCount = 0;
    private String sqlQuery;
    private String result;
    ServerCommunication serverConnector;
    private TextView textViewQuestion;
    private Button buttonAnswer1, buttonAnswer2, buttonAnswer3, buttonAnswer4;
    private ProgressBar progressBarTimer;

    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private CountDownTimer countDownTimer;
    private String categoryTitle;
    private int categoryPercentage;
    private TextView correct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Инициализация UI-элементов
        correct=findViewById(R.id.otvet);
        textViewQuestion = findViewById(R.id.textViewQuestion);
        buttonAnswer1 = findViewById(R.id.buttonAnswer1);
        buttonAnswer2 = findViewById(R.id.buttonAnswer2);
        buttonAnswer3 = findViewById(R.id.buttonAnswer3);
        buttonAnswer4 = findViewById(R.id.buttonAnswer4);
        progressBarTimer = findViewById(R.id.progressBarTimer);

        // Получение данных из Intent
        Intent intent = getIntent();
        categoryTitle = intent.getStringExtra("categoryTitle");
        categoryPercentage = intent.getIntExtra("categoryPercentage", 0);
        sqlQuery = "SELECT string_agg(QuestionsAndAnswers, ' | ') FROM (SELECT Q.QuestionText || ': ' || string_agg(A.AnswerText || (CASE WHEN A.IsCorrect THEN ' (правильный)' ELSE '' END), ', ') AS QuestionsAndAnswers FROM Questions Q JOIN Answers A ON Q.QuestionID = A.QuestionID WHERE Q.CategoryID = "+Cat()+" GROUP BY Q.QuestionID ORDER BY Q.QuestionID) subquery;";
        // Настройка UI с использованием данных о категории
        setTitle("Quiz: " + categoryTitle); // Устанавливаем заголовок активности
        // Здесь вы можете использовать информацию о категории для получения вопросов
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    serverConnector = ServerCommunication.getInstance();
                    serverConnector.sendMessage(sqlQuery);
                    Log.d("Вопросы","Запрос Отправлен");
                    String response = serverConnector.receiveMessage();
                    Log.d("Вопросы","Ответ Получен");
                    Log.d("Вопросы",response);
                    questions= parseQuestions(response);
                    Log.d("Вопросы","Сработал");
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            // Настройка первого вопроса
                            showQuestion(questions.get(currentQuestionIndex));

                            buttonAnswer1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    checkAnswer(buttonAnswer1, 1);
                                }
                            });
                            buttonAnswer2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    checkAnswer(buttonAnswer2, 2);
                                }
                            });
                            buttonAnswer3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    checkAnswer(buttonAnswer3, 3);
                                }
                            });
                            buttonAnswer4.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    checkAnswer(buttonAnswer4, 4);
                                }
                            });
                            setupTimer();
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }


    private void showQuestion(Question question) {
        textViewQuestion.setText(question.getQuestionText());
        buttonAnswer1.setText(question.getAnswer1());
        buttonAnswer2.setText(question.getAnswer2());
        buttonAnswer3.setText(question.getAnswer3());
        buttonAnswer4.setText(question.getAnswer4());
    }

    private void checkAnswer(Button selectedButton, int selectedAnswer) {
        // Получаем текущий вопрос
        Question currentQuestion = questions.get(currentQuestionIndex);

        // Логика проверки ответа
        if (selectedAnswer == currentQuestion.getCorrectAnswerIndex()) {
            Log.d("Проверка ответа", Integer.toString(currentQuestion.getCorrectAnswerIndex()));
            correctAnswersCount++;

        } else {
            Log.d("Проверка ответа", Integer.toString(currentQuestion.getCorrectAnswerIndex()));
        }

        // Изменение цвета кнопки в зависимости от правильности ответа
        if (selectedAnswer == currentQuestion.getCorrectAnswerIndex()) {
            setButtonColor(selectedButton, R.color.colorCorrect);
        } else {
            setButtonColor(selectedButton, R.color.colorIncorrect);
        }

        // Другие действия, такие как увеличение счета правильных ответов и переход к следующему вопросу...

        // Задержка перед переходом к следующему вопросу
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(Integer.parseInt(correct.getText().toString())!=correctAnswersCount){
                    correct.setText(String.valueOf(correctAnswersCount));// Увеличиваем счет правильных ответов
                }
                // Восстановление цвета кнопки
                setButtonColor(selectedButton, R.color.defaultButtonColor);

                // Переход к следующему вопросу
                currentQuestionIndex++;
                if (currentQuestionIndex < questions.size()) {
                    showQuestion(questions.get(currentQuestionIndex));
                } else {
                    // Если вопросы закончились, показываем результат
                    showResult(" Вы прошли тест за "+(100 - Integer.parseInt(time.getText().toString())));
                }
            }
        }, 500); // 1000 миллисекунд = 1 секунда
    }

    private void setupTimer() {
        int totalTimeMillis = 60000; // Время в миллисекундах (60 секунд)
        int intervalMillis = 1000; // Интервал обновления таймера (1 секунда)
        time= findViewById(R.id.textTime);
        countDownTimer = new CountDownTimer(totalTimeMillis, intervalMillis) {
            @Override
            public void onTick(long millisUntilFinished) {
                int progress = (int) (millisUntilFinished / (float) totalTimeMillis * 100);
                progressBarTimer.setProgress(progress);
                time.setText(String.valueOf(progress));
            }

            @Override
            public void onFinish() {
                showResult("Вы не успели пройти тест до конца");
            }
        };

        // Запуск таймера
        countDownTimer.start();
    }
    private void setButtonColor(Button button, @ColorRes int colorRes) {
        int color = ContextCompat.getColor(this, colorRes);
        button.setBackgroundColor(color);
    }
    private  void showResult(String vivod){
        try{

            String message = "Поздравляем! Вы ответили правильно на " + correctAnswersCount + " вопросов! " +vivod;
            SharedPreferences sharedPreferences = getSharedPreferences("MyApp", MODE_PRIVATE);
            int literatureValue = sharedPreferences.getInt(categoryTitle, 0);
            if(literatureValue<correctAnswersCount*5){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(categoryTitle, correctAnswersCount*5);
                editor.apply();
            }
// Создаем и отображаем AlertDialog
            new AlertDialog.Builder(this)
                    .setTitle("Результаты теста")
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent mainIntent = new Intent(QuizActivity.this, MainActivity.class);
                            startActivity(mainIntent);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();

        }
        catch (Exception e){
            e.printStackTrace();
            Intent mainIntent = new Intent(QuizActivity.this, MainActivity.class);
            startActivity(mainIntent);
        }

    }
    private int Cat() {

        switch (categoryTitle) {
            case "Литература":
                return 1;
            case "Музыка":
                return 2;
            case "Игры":
                return 2;
            case "Спорт":
                return 4;
            case "Политика":
                return 5;
            case "Страны":
                return 6;
            case "Аниме":
                return 7;
            case "Древняя русь":
                return 8;
            case "Космос":
                return 9;
            case "Хип-хоп":
                return 10;
            case "Фильмы":
                return 11;
            case "Животные":
                return 12;
            default:
                return 1;
        }
    }
}
