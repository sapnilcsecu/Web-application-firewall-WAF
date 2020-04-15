/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapnil.machinelearning.sapnil_machinelearning_example;

import com.alibaba.fastjson.JSONArray;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;

import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

/**
 *
 * @author Nasir(programmer)
 */
interface PyFunction {

    public Double train_model(String input_dataset, String test_dataset, String vocabulary_path, String payload_col_name, String payload_label);
}

public class sapnil_machinelearning {

    //private static final PythonInterpreter intr = new PythonInterpreter();
    static {
        //  intr.exec("import sys");

        try {
            Process p = Runtime.getRuntime().exec(new String[]{"python", "-c", "import sys, json;from classifier.train_model import live_verna_detection; live_verna_detection=live_verna_detection('D:/bitbuket sapnil machinelearning/sapnil_machinelearning/sapnil_machinelearning','welcome to the nasir computer world');print(json.dumps([str(live_verna_detection)]))"});
            //Process p = Runtime.getRuntime().exec(new String[]{"python", "-c", "import sys, json;print(json.dumps(['the is accuracy_score']))"});

            p.waitFor();

            String stdout = IOUtils.toString(p.getInputStream());
            System.out.println("the json is "+stdout);
            JSONArray syspathRaw = JSONArray.parseArray(stdout);
            for (int i = 0; i < syspathRaw.size(); i++) {
                String path = syspathRaw.getString(i);
                System.out.println("the accuracy is " + path);
                /* if (path.contains("site-packages") || path.contains("dist-packages")) {
                    System.out.println("the  path is "+path);
                   // intr.exec(String.format("sys.path.insert(0, '%s')", path));
                }*/
            }

            /*Process p = Runtime.getRuntime().exec(new String[]{
                "python", "-c", "import sys, json;from classifier.train_model import train_model; accuracy_score=train_model(\"D:/bitbuket sapnil machinelearning/sapnil_machinelearning/sapnil_machinelearning/verfullpayload.csv\",\"D:/bitbuket sapnil machinelearning/sapnil_machinelearning/sapnil_machinelearning/xss_test.csv\",'D:/bitbuket sapnil machinelearning/sapnil_machinelearning/sapnil_machinelearning',\"payload\",\"label\")"});
            p.waitFor();
            //String stdout = IOUtils.toString(p.getInputStream());
            InputStream stdout = p.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stdout, StandardCharsets.UTF_8));
            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println("stdout: " + line);
            }*/
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // PythonInterpreter interpreter = new PythonInterpreter();
        /* try {

            intr.exec("from classifier.train_model import train_model");
            PyObject train_model = intr.get("train_model");
            PyFunction function = (PyFunction) train_model.__tojava__(PyFunction.class);
            Double acc = function.train_model("D:/bitbuket sapnil machinelearning/sapnil_machinelearning/sapnil_machinelearning/verfullpayload.csv", "D:/bitbuket sapnil machinelearning/sapnil_machinelearning/sapnil_machinelearning/xss_test.csv", "D:/bitbuket sapnil machinelearning/sapnil_machinelearning/sapnil_machinelearning", "payload", "label");
            System.out.println("the acc is " + acc);
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            e.toString();
        }*/
    }
}
