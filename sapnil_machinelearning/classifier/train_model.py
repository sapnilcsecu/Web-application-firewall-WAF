'''
Created on Mar 23, 2020

@author: Nasir uddin
'''
from sklearn import naive_bayes
from feature_eng.word_tf_idf import word_tf_idf
from classifier.Classifier import train_model
from dataset_pre.dataset_load import load_cvs_dataset
import pickle
from builtins import str
import os
import re




def train_model_write(input_dataset, vocabulary_path, payload_col_name, payload_label):
   
    trainDF=load_cvs_dataset(input_dataset)
    txt_label=trainDF[payload_label]
    txt_text=trainDF[payload_col_name]
    model_input=word_tf_idf(txt_text,txt_label)
    naive=naive_bayes.MultinomialNB()
    accuracy =train_model(naive,model_input[0], model_input[1], model_input[2], model_input[3])
    dirs = os.listdir( vocabulary_path )
    file_no=len(dirs)
    pickle.dump(naive, open(str(vocabulary_path)+"text_classifier-"+str(file_no)+".pickle", "wb"))
    pickle.dump(model_input[4], open(str(vocabulary_path)+"tfidf-"+str(file_no)+".pickle", "wb"))   
    return accuracy*100 



def live_verna_single_detection(model_path,web_param):
    dirs = os.listdir( model_path )
  
    count_limit=len(dirs)-2
    count=0;
    is_anom=False
    for pkl_file in dirs:
        file_num = int(re.search(r'\d+', pkl_file).group())
        classifier = pickle.load(open(str(model_path)+"text_classifier-"+str(file_num)+".pickle", mode='rb'))
        tfidf = pickle.load(open(str(model_path)+"tfidf-"+str(file_num)+".pickle", mode='rb'))
        imput_param= [''+str(web_param)]
        classifier_result = classifier.predict(tfidf.transform(imput_param))
        print("classifier_result is ",classifier_result[0])
       
        if(classifier_result[0]=='anom'):
            is_anom=True
            break;
       
        count=count+1
        if(count>=count_limit):
            break;
        
    
    return is_anom


def live_verna_detection(model_path, input_web_param_path,payload_col_name):
    
    
    verify_result =[]  
    trainDF = load_cvs_dataset(input_web_param_path)
    txt_text = trainDF[payload_col_name]
    for web_param in txt_text:
        result=live_verna_single_detection(model_path,web_param)
       
        if(result==True):    
            verify_result.append('anom')
        elif (result==False) :
            verify_result.append('norm')
    
    return verify_result

     





def bulk_live_verna_detection(input_dataset, context_path, payload, label):
    bulk_verna_detect_result = []
    
    trainDF = load_cvs_dataset(input_dataset)
    #txt_label = trainDF[label]
    txt_text = trainDF[payload]
    for doc in txt_text:
        result=live_verna_single_detection(context_path,doc)
        if(result==True):    
            bulk_verna_detect_result.append('anom')
        elif (result==False) :
            bulk_verna_detect_result.append('norm')
        #bulk_verna_detect_result.append(result)
    return bulk_verna_detect_result




