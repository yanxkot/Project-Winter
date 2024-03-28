package com.example.proj.controllers;

import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class QuestionController  {

    @FXML
    Label derivativeLbl;
    @FXML
    TextField textfield1;
    public QuestionController(String derivative) {
        derivativeLbl.setText(derivative);
    }

    public QuestionController() {

    }

    public void initialize(){


    }
}
