package com.example.andre.projetovoluntariado;

/**
 * Created by Andre on 17/09/2017.
 */

public class Estados {
    private int id;
    private String name;

    public Estados(){}

    public Estados(int id, String name){
        this.id = id;
        this.name = name;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }
}
