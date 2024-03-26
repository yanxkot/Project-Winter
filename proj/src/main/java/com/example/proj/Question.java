package com.example.proj;

import java.util.Random;

public class Question {

    private static String question;
    private static int answer;
    String[] coefficients = new String[6];
    String[] exponents = new String[6];

    String[] signs = new String[6];


    //TODO: add properties to question class
    public void derivative() {
        Random random = new Random();



        String exponent;
        String coefficient;
        double sign;

        for (int i = 0; i <= 5; i++) {

            sign = Math.random();


            exponent = Integer.toString(random.nextInt(50));
            exponents[i] = exponent;

            coefficient = Integer.toString(random.nextInt(50));
            coefficients[i] =coefficient;

            System.out.print(coefficient + "X^" + exponent);


            if (sign >= 0 && sign < +0.5) {
                System.out.print("-");
                signs[i] = "-";
            } else {
                System.out.print("+");
                signs[i] = "+";
            }


            if (i == 5)
                System.out.print(random.nextInt(50));
        }
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
