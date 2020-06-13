/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapnil.machinelearning.utility;

import java.io.File;

/**
 *
 * @author Nasir uddin
 */
public class Utility {

    public static boolean is_empty(String input_string) {
        boolean is_emptry = false;
        if (input_string == null) {
            is_emptry = true;
        }
        return is_emptry;
    }
    

    public static boolean del_file(String file_path) {
        File file = new File(file_path);

        if (file.delete()) {
            return true;
        }
        return false;
    }
}
