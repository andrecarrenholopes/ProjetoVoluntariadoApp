package com.example.andre.projetovoluntariado;

/**
 * Created by Andre on 16/10/2017.
 */

public class ChildRow {
    private int icon;
    private String text;
    private int id;

    //construtor para busca sem login
    public ChildRow(int icon, String text) {
        this.icon = icon;
        this.text = text;
        this.id = 0;
    }

    //construtor para busca com login
    public ChildRow(int icon, String text, int id) {
        this.icon = icon;
        this.text = text;
        this.id = id;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
