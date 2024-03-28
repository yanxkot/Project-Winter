package com.example.proj;

import com.example.proj.controllers.QuestionController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class Question {

    private static String question;
    private static int answer;
    String[] coefficients = new String[6];
    String[] exponents = new String[6];

    String[] signs = new String[6];


    //TODO: add properties to question class
    public void derivative() throws IOException {



        Random random = new Random();


        String derivative = null;
        String exponent;
        String coefficient;
        String constant = null;
        double sign;

        for (int i = 0; i <= 5; i++) {

            sign = Math.random();


            exponent = Integer.toString(random.nextInt(50));
            exponents[i] = exponent;

            coefficient = Integer.toString(random.nextInt(50));
            coefficients[i] =coefficient;

            derivative = coefficient + "X^" + exponent;
            System.out.print(derivative);


            if (sign >= 0 && sign < +0.5) {
                System.out.print("-");
                derivative = derivative + "-";
                signs[i] = "-";
            } else {
                System.out.print("+");
                signs[i] = "+";
                derivative = derivative + "+";
            }


            if (i == 5)
                constant = Integer.toString(random.nextInt(5));
                System.out.print(constant);
                derivative = derivative + constant;
        }

        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/question.fxml"));
        QuestionController controller = new QuestionController(derivative);
        loader.setController(controller);
        Pane root = loader.load();

        Scene scene = new Scene(root,600,600);
        stage.setScene(scene);
        stage.alwaysOnTopProperty();
        stage.show();
    }

    public void derivativeSolver(){

        String derivative = null;
        int coefficient;
        int exponent;
        for(int i=0; i<=5; i++){
             coefficient = Integer.parseInt(this.coefficients[i]);
             exponent = Integer.parseInt(this.exponents[i]);
           System.out.print(coefficient *exponent + "X^"+ (exponent-1)+signs[i] );

        }
    }

    public void integral() {

    }

    public void logic() {

    }

    public void matrix() {

    }

    public void balanceEquations() {

    }

    public void sequence() {

    }

    public static String getQuestion() {
        return question;
    }

    public static int getAnswer() {
        return answer;
    }
}
