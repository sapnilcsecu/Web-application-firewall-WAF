'''
Created on Aug 4, 2019

@author: Nasir uddin
'''
import math

# https://github.com/mayank408/TFIDF/blob/master/TFIDF.ipynb


def computeTF(wordDict, bow):
    tfDict = {}
    bowCount = len(bow)
    for word, count in wordDict.items():
        tfDict[word] = count / float(bowCount)
    return tfDict


def computeIDF(docList):
    
    N = len(docList)
    
    idfDict = dict.fromkeys(docList[0].keys(), 0)
    for doc in docList:
        for word, val in doc.items():
            if val > 0:
                idfDict[word] += 1
    
    for word, val in idfDict.items():
        idfDict[word] = math.log10(N / float(val))
        
    return idfDict


def computeTFIDF(tfBow, idfs):
    tfidf = {}
    for word, val in tfBow.items():
        tfidf[word] = val * idfs[word]
    return tfidf


def generate_TF_IDF_set(docList):
    
    tf_idf_matrix = []
    tfBow_max = []
    bow = []
    temp_bow = []
    wordSet = {}
    for doc in docList:
        temp_bow = doc.split(" ")
        wordSet = set(bow).union(set(temp_bow))
        wordDict = dict.fromkeys(wordSet, 0) 
        for word in temp_bow:
            wordDict[word] += 1
    tf = computeTF(wordDict, temp_bow)        
    tfBow_max.append(tf)
    
    idfs = computeIDF(docList)
    for tfBow in tfBow_max:
        tf_idf_matrix.append(computeTFIDF(tfBow, idfs))
    
    return tf_idf_matrix

