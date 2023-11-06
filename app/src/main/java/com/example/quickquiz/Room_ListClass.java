package com.example.quickquiz;

public class Room_ListClass {
    private String name;
    private int count_players;
    private int max_count_players;
    private String category;

    public Room_ListClass(String name,int count_players,int max_count_players,String category){

        this.name=name;
        this.count_players=count_players;
        this.max_count_players=max_count_players;
        this.category=category;
    }
    public String getName(){
        return this.name;
    }
    public int getCount_players(){
        return this.count_players;
    }
    public int getMax_count_players(){
        return this.max_count_players;
    }
    public String getCategory(){
        return this.category;
    }
    public void setName(String name){
        this.name =name;
    }
    public void setCount_players(int count_players ){
        this.count_players=count_players;
    }
    public void setMax_count_players(int max_count_players){
         this.max_count_players=max_count_players;
    }
    public void setCategory(String category){
        this.category=category;
    }
}
