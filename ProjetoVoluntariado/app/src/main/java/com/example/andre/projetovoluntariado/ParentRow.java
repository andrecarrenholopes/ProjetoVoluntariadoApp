package com.example.andre.projetovoluntariado;

import java.util.ArrayList;

/**
 * Created by Andre on 16/10/2017.
 */

public class ParentRow {
    private String name;
    private ArrayList <ChildRow> childList;

    public ParentRow(String name, ArrayList<ChildRow> childList) {
        this.name = name;
        this.childList = childList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ChildRow> getChildList() {
        return childList;
    }

    public void setChildList(ArrayList<ChildRow> childList) {
        this.childList = childList;
    }
}
