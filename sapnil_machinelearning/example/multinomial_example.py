'''
Created on Mar 19, 2020

@author: Nasir uddin
'''
'''
from dataset_pre.dataset_load import load_cvs_dataset
from dataset_pre.dataset_load import splitDataset
from feature_eng.count_word import count_ver_word_fit
from classifier.multinomial_nativebayes import multi_nativebayes_verna_predict
from classifier.multinomial_nativebayes import live_multi_nativebayes_verna_predict
from classifier.multinomial_nativebayes import accuracy_score
import pickle
'''
#from classifier.multinomial_nativebayes import live_multi_nativebayes_verna_predict
#from dataset_pre.dataset_load import load_cvs_dataset

from classifier.train_model import train_model_write
from classifier.train_model import live_verna_single_detection
from classifier.train_model import live_verna_detection



#import json
#import pickle

def main():
    
    '''
    trainDF = load_cvs_dataset("../verfullpayload.csv")
    txt_label = trainDF['label']
    txt_text = trainDF['payload']
    
    #trainSet, testcopy, labelset, testlabelcopy=splitDataset(txt_text, txt_label,0.2)
    model_input=count_ver_word_fit(txt_text,txt_label)
    #Saving and Loading the Model
    with open('../text_classifier', 'wb') as picklefile:  
        pickle.dump(model_input,picklefile)
    #Saving and Loading the Model
    
    
    trainDF_test = load_cvs_dataset("../xss_test.csv")
    txt_label1 = trainDF_test['label']
    txt_text1 = trainDF_test['payload']
    
    # classifer=multinomial_nativebayes()
    final_doc_class_label=multi_nativebayes_verna_predict(model_input,txt_text1)
    print(final_doc_class_label)
    print("the accuracy_score ",accuracy_score(txt_label1,final_doc_class_label))
    
    #To load the model and predict
    
    with open('../text_classifier', 'rb') as training_model:  
        train_model = pickle.load(training_model)
    doc_class_label=live_multi_nativebayes_verna_predict(train_model,' or 1=1--')    
    print("this class level is ",doc_class_label)
    #To load the model
    
    accuracy_score=train_model("E:/github_repro/Web-application-firewall-WAF/sapnil_machinelearning/WAF-WEBAPP-EXAMPLE/src/main/webapp/xss_payload22.csv",'E:/github_repro/Web-application-firewall-WAF/sapnil_machinelearning/WAF-WEBAPP-EXAMPLE/src/main/webapp/',"payload","label","append")
    print("the score is ",accuracy_score)
    ''
    
    #To load the model and predict
    
    with open('../trainmodel', 'rb') as training_model:  
        train_model1 = pickle.load(training_model)
    '''
    #doc_class_label=live_multi_nativebayes_verna_predict(train_model1,'nasir select from rupali bank where he get it')
    ''' 
    trainDF = load_cvs_dataset("E:/github_repro/Web-application-firewall-WAF/sapnil_machinelearning/WAF-WEBAPP-EXAMPLE/src/main/webapp/xss_payload_33.csv")
    txt_label = trainDF['label']
    txt_text = trainDF['payload']
    for doc in txt_text:
        doc_class_label=live_verna_detection('E:/github_repro/Web-application-firewall-WAF/sapnil_machinelearning/WAF-WEBAPP-EXAMPLE/src/main/webapp/',doc)     
        print("this doc_class_label is ",doc_class_label)
    '''
    '''    
    bulk_verna_detect_result=bulk_live_verna_detection('E:/github_repro/Web-application-firewall-WAF/sapnil_machinelearning/WAF-WEBAPP-EXAMPLE/src/main/webapp/xss_payload_33.csv','E:/github_repro/Web-application-firewall-WAF/sapnil_machinelearning/WAF-WEBAPP-EXAMPLE/src/main/webapp/',"payload","label")    
    for doc1 in bulk_verna_detect_result:
        print(doc1)    
    '''   
    '''
    accuracy_score=train_model_write('../payload/cmd_payload.csv','../train_model/',"payload","label")
    print("the score is ",accuracy_score)
    '''
    
    '''
    detect_result=live_verna_single_detection('../train_model/',"nasir")
    print('detect_result ',detect_result)
    '''
    '''
    detect_result=live_verna_detection('../train_model/' ,'../payload/test_payload.csv',"payload")
    print('detect_result ',detect_result)
    '''
    #verna_detection_re=bulk_live_verna_detection('../payload_full.csv', '../',"payload","label")
    #print(verna_detection_re)
    
    verna_detection_re=live_verna_detection('../train_model/','../payload/test_payload.csv',"payload")    
    print(verna_detection_re)
    
    
    #To load the model
    
if __name__ == '__main__':
    main()