'''
Created on Aug 7, 2019

@author: Nasir(programmer)
'''
import math
from nltk.tokenize import word_tokenize
from model.train_model import train_model
import re


def multi_nativebayes_train(model_data):
    #
   
    class_eachtoken_likelihood = {} 
    vocabulary = model_data.get_vocabulary()
    for class_label in model_data.get_class_labels(): 
        class_eachtoken_likelihood[class_label] = {}
        for voc in vocabulary:
            class_eachtoken_likelihood[class_label] [voc] = 0
    logprior={}
    vocabularyCount = model_data.get_vocabularyCount()
    class_eachtoken_count = model_data.get_class_eachtoken_count()
    for class_label in model_data.get_class_labels(): 
        
        
        total_class_token = model_data.get_total_class_token()
        
        logprior[class_label]=math.log(total_class_token[class_label] / vocabularyCount)
        
        for word in vocabulary:
           
            if(class_eachtoken_count[class_label][word]==0):
                class_eachtoken_likelihood[class_label][word]=0
               
            else:
                class_eachtoken_likelihood[class_label][word]=math.log(class_eachtoken_count[class_label][word] / total_class_token[class_label])
    train_model_data = train_model(logprior,class_eachtoken_likelihood,vocabulary,model_data.get_class_labels())       
    return train_model_data;



def multi_nativebayes_verna_predict(train_model_data, test_dataset):
    
    condProbabilityOfTermClass = {}
    final_doc_class_label = {}
    doccount = 0;
    logprior = train_model_data.get_logprior()
 
    for doc in test_dataset:
       
        doc=re.sub("\d+", " ", doc)
        final_doc_class_label['doc' + '-' + str(doccount)] = ''
        words = word_tokenize(doc)
        score_Class = 0
        max_score = 0
        final_class_label = ''
        is_norm = 0
      
       
        for class_label in train_model_data.get_class_labels(): 
            condProbabilityOfTermClass[class_label] = 0
          
            logprior_val=logprior[class_label]
            for word in words:
                word=word.lower()
                get_class_eachtoken_likelihood = train_model_data.get_class_eachtoken_likelihood()
                vocabulary = train_model_data.get_vocabulary()
                if(word in vocabulary):
                    
                    if(get_class_eachtoken_likelihood[class_label][word]==0):
                       
                        condProbabilityOfTermClass[class_label] = condProbabilityOfTermClass[class_label]+0;
                    else:
                        condProbabilityOfTermClass[class_label] = condProbabilityOfTermClass[class_label] + get_class_eachtoken_likelihood[class_label][word]
                else:
                    
                    condProbabilityOfTermClass[class_label] = condProbabilityOfTermClass[class_label]+0;
            
            if(condProbabilityOfTermClass[class_label] == 0):
             
                is_norm = 1  
                continue      
            score_Class = logprior_val + condProbabilityOfTermClass[class_label]
            if(max_score > score_Class):
                max_score = score_Class
                final_class_label = class_label
              
        if(is_norm == 1):
            final_doc_class_label['doc' + '-' + str(doccount)] = "norm" 
        else:         
            final_doc_class_label['doc' + '-' + str(doccount)] = final_class_label
      
        doccount = doccount + 1    
   
    
    return final_doc_class_label



def live_multi_nativebayes_verna_predict(train_model_data, input_doc):
    
    condProbabilityOfTermClass = {}
    
    doc=re.sub("\d+", " ", input_doc)
    final_doc_class_label = ''
    words = word_tokenize(doc)
    score_Class = 0
    max_score = 0
    final_class_label = ''
    is_norm = 0
        
    vocabulary = train_model_data.get_vocabulary() 
    logprior = train_model_data.get_logprior()
    class_label_list=train_model_data.get_class_labels()
 
    for class_label in class_label_list: 
        condProbabilityOfTermClass[class_label] = 0
     
        logprior=logprior[class_label]
        for word in words:
            word=word.lower()
            class_eachtoken_likelihood = train_model_data.get_class_eachtoken_likelihood()
            
            if(word in vocabulary):
                
                if(class_eachtoken_likelihood[class_label][word]==0):
                   
                    condProbabilityOfTermClass[class_label] = condProbabilityOfTermClass[class_label]+0;
                else:
                    condProbabilityOfTermClass[class_label] = condProbabilityOfTermClass[class_label] + class_eachtoken_likelihood[class_label][word]
            else:
              
                condProbabilityOfTermClass[class_label] = condProbabilityOfTermClass[class_label]+0;
              
        
        if(condProbabilityOfTermClass[class_label] == 0):
           
            is_norm = 1  
            continue      
        score_Class = logprior + condProbabilityOfTermClass[class_label]
        if(max_score > score_Class):
            max_score = score_Class
            final_class_label = class_label
            
    if(is_norm == 1):
        final_doc_class_label= "norm" 
    else:         
        final_doc_class_label = final_class_label
           
    
    return final_doc_class_label




def accuracy_score(testlabelcopy, final_doc_class_label):
    label_count = 0
    wrong_count = 0
    for label in testlabelcopy:
        #print(final_doc_class_label['doc' + '-' + str(label_count)]+' '+str(label_count))
        if label != final_doc_class_label['doc' + '-' + str(label_count)] :
            wrong_count = wrong_count + 1
        label_count = label_count + 1
    
    accuracy = ((len(testlabelcopy) - wrong_count)*100 )/ len(testlabelcopy)
    
    return accuracy     
        
