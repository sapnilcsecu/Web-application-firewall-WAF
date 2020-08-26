'''
Created on Aug 4, 2019

@author: Nasir uddin
'''

import pandas as pd
import numpy as np


import csv
import random
 
   
def load_cvs_dataset(dataset_path):
    
    # Set Random seed
    np.random.seed(500)
    # Add the Data using pandas
    Corpus = pd.read_csv(dataset_path, encoding='latin-1', error_bad_lines=False)
   
    
    return Corpus

def load_cvs_dataset1(dataset_path):
    #csvfile = open(dataset_path,encoding='latin-1','r')
    with open(dataset_path,encoding='latin-1') as csvfile:
        readCSV = list(csv.reader(csvfile, delimiter=','))
   
   
   # txt_label = readCSV['label']
    
    payload= []
    label = []
    readCSV.pop(0)
    for row in readCSV:
        payload.append(row[0])
        label.append(row[2])
      
    Corpus=[payload,label]
    
    return Corpus


# ref:https://machinelearningmastery.com/naive-bayes-classifier-scratch-python/
def splitDataset(dataset, dataset_label, splitRatio):
    trainSize = int(len(dataset) * splitRatio)
    trainSet = []
    testcopy = list(dataset)
    while len(trainSet) < trainSize:
        index = random.randrange(len(testcopy))
        trainSet.append(testcopy.pop(index))
        
    labelsize = int(len(dataset_label) * splitRatio)
    labelset = []
    testlabelcopy = list(dataset_label)
    while len(labelset) < labelsize:
        index = random.randrange(len(testlabelcopy))
        labelset.append(testlabelcopy.pop(index))    
        
    return [trainSet, testcopy, labelset, testlabelcopy]

def splitDataset_row(dataset, dataset_label):
    train_dataset = list(dataset)
    #split_part=[]
    count=0
    is_empty=False 
    while is_empty:
        split_part=[]
        while len(train_dataset)>0:
            index = random.randrange(len(train_dataset))
            split_part.append(train_dataset.pop(index)) 
            if count==1000:
                break
            count=count+1
        if len(train_dataset)==0:
            is_empty=True     
        
    return



