/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapnil.machinelearning.model;

/**
 *
 * @author Nasir uddin
 */
public class Train_model_param {
    String payload_name;
    String payload_label;
    String input_dataset_path;
    String input_dataset_filename;

    public void setMod_of_tran(String mod_of_tran) {
        this.mod_of_tran = mod_of_tran;
    }

    public String getMod_of_tran() {
        return mod_of_tran;
    }
    String mod_of_tran;
    

    public void setPayload_name(String payload_name) {
        this.payload_name = payload_name;
    }

    public void setPayload_label(String payload_label) {
        this.payload_label = payload_label;
    }

    public void setInput_dataset_path(String input_dataset_path) {
        this.input_dataset_path = input_dataset_path;
    }

    public void setInput_dataset_filename(String input_dataset_filename) {
        this.input_dataset_filename = input_dataset_filename;
    }

    public String getPayload_name() {
        return payload_name;
    }

    public String getPayload_label() {
        return payload_label;
    }

    public String getInput_dataset_path() {
        return input_dataset_path;
    }

    public String getInput_dataset_filename() {
        return input_dataset_filename;
    }
}
