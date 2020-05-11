/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapnil.machinelearning.utility;

/**
 *
 * @author Nasir uddin
 */
public class Utility {

    public static boolean is_empty(String input_string) {
        boolean is_emptry = false;
        if (input_string.equals("") || input_string.equals(" ") || input_string == null) {
            is_emptry = true;
        }
        return is_emptry;
    }
}
