package com.example.quickquiz;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuestionParser {

    public static ArrayList<Question> parseQuestions(String result) {
        ArrayList<Question> questions = new ArrayList<>();

        try {
            // Разбиваем строку по символу "|"
            String[] questionParts = result.split(" \\| ");

            for (String questionPart : questionParts) {
                // Разбиваем строку по символу ":"
                String[] parts = questionPart.split(": ");

                // Первая часть - текст вопроса
                String questionText = parts[0].trim();

                // Вторая часть - ответы
                String[] answers = parts[1].split(", ");

                // Создаем массив для хранения ответов
                String[] formattedAnswers = new String[answers.length];
                int correctAnswerIndex = -1;

                // Извлекаем текст ответов и определяем правильный ответ
                for (int i = 0; i < answers.length; i++) {
                    String answer = answers[i];
                    boolean isCorrect = answer.endsWith("(правильный)");

                    // Убираем метку "(правильный)" из текста ответа
                    answer = answer.replaceAll("\\s*\\(правильный\\)\\s*", "");

                    formattedAnswers[i] = answer;

                    if (isCorrect) {
                        correctAnswerIndex = i;
                    }
                }

                // Перемешиваем ответы и обновляем correctAnswerIndex
                List<String> answerList = new ArrayList<>(Arrays.asList(formattedAnswers));
                Collections.shuffle(answerList);
                correctAnswerIndex = answerList.indexOf(formattedAnswers[correctAnswerIndex])+1;
                formattedAnswers = answerList.toArray(new String[0]);

                // Создаем и добавляем экземпляр класса Question в список
                questions.add(new Question(questionText, formattedAnswers[0], formattedAnswers[1],
                        formattedAnswers[2], formattedAnswers[3], correctAnswerIndex));
            }
        } catch (Exception e) {
            // Возвращаем какой-то стандартный вопрос или бросаем исключение
            questions.add(new Question("Ошибка при парсинге вопроса", "", "", "", "", -1));
        }

        return questions;
    }
}
