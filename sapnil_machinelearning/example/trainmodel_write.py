'''
Created on May 7, 2019

@author: Nasir uddin
'''

from sklearn import naive_bayes
from dataset_pre.dataset_load import load_cvs_dataset
from feature_eng.word_tf_idf import word_tf_idf
from classifier.Classifier import train_model
import pickle 

def main():
    
    #load the dataset
   
    trainDF=load_cvs_dataset("../payload_full.csv")
    #load the dataset
    
    #Text Preprocessing
    
    txt_label=trainDF['label']
    txt_text=trainDF['payload']
    
    #Text feature engineering 
    model_input=word_tf_idf(txt_text,txt_label)
    #Text feature engineering 
    
    #  Build Text Classification Model and Evaluating the Model
    naive=naive_bayes.MultinomialNB()
    accuracy =train_model(naive,model_input[0], model_input[1], model_input[2], model_input[3])
    print ("NB, word_tf_idf accuracy is : ", accuracy*100)
    
    
  
    with open('../vocabulary_file', 'wb') as vocabulary_file:  
        pickle.dump(model_input[4],vocabulary_file)
    
    with open('../text_classifier', 'wb') as picklefile:  
        pickle.dump(naive,picklefile)
    
    
    #  Build Text Classification Model and Evaluating the Model
if __name__ == '__main__':
    main()