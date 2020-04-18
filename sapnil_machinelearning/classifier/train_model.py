'''
Created on Mar 23, 2020

@author: Nasir uddin
'''



from dataset_pre.dataset_load import load_cvs_dataset
from dataset_pre.dataset_load import splitDataset
from feature_eng.count_word import count_ver_word_fit
import pickle
from classifier.multinomial_nativebayes import multi_nativebayes_train
from classifier.multinomial_nativebayes import multi_nativebayes_verna_predict
from classifier.multinomial_nativebayes import live_multi_nativebayes_verna_predict
from classifier.multinomial_nativebayes import accuracy_score
from nltk.lm import vocabulary
from classifier.multinomial_nativebayes import live_multi_nativebayes_verna_predict
import pickle

#this function take separate trainset,testdataset as input
def train_model(input_dataset,test_dataset,vocabulary_path,payload_col_name,payload_label,test_payload_col_name,test_payload_label):
    trainDF = load_cvs_dataset(input_dataset)
    
    txt_label = trainDF[payload_label]
    txt_text = trainDF[payload_col_name]
    
  
    model_input=count_ver_word_fit(txt_text,txt_label)
    train_model_ob=multi_nativebayes_train(model_input)
  
    
    with open(str(vocabulary_path)+'trainmodel', 'wb') as picklefile:  
        pickle.dump(train_model_ob,picklefile)
   
    
    
    trainDF_test = load_cvs_dataset(test_dataset)
   
    
    txt_label1 = trainDF_test[payload_label]
    txt_text1 = trainDF_test[payload_col_name]
    
   
    final_doc_class_label=multi_nativebayes_verna_predict(train_model_ob,txt_text1)
   
    
    return accuracy_score(txt_label1,final_doc_class_label) 

def live_verna_detection(vocabulary_path,web_param):
    with open(str(vocabulary_path)+'trainmodel', 'rb') as training_model:  
        train_model1 = pickle.load(training_model)
    
    doc_class_label=live_multi_nativebayes_verna_predict(train_model1,web_param)     
    #print("this class level is ",doc_class_label)
    return doc_class_label


def test_java_python_function(web_param):
    print("the web param1556 :"+str(web_param))
    
   
        
    return ""
    
    

