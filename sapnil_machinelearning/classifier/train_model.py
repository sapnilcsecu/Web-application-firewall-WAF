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

# this function take separate trainset,testdataset as input


# def train_model(input_dataset,test_dataset,vocabulary_path,payload_col_name,payload_label,test_payload_col_name,test_payload_label):
def train_model(input_dataset, vocabulary_path, payload_col_name, payload_label, mode):
    # print("the input_dataset22")
    trainDF = load_cvs_dataset(input_dataset)

    txt_label = trainDF[payload_label]
    txt_text = trainDF[payload_col_name]
    
    txt_text, testcopy, txt_label, testlabelcopy = splitDataset(txt_text, txt_label, 0.20)
  
    model_input = count_ver_word_fit(txt_text, txt_label)
    train_model_ob = multi_nativebayes_train(model_input)
    # print("the input_dataset"+str(input_dataset))
    if(mode == 'write'):
        # print("the write mode"+str(input_dataset))
        with open(str(vocabulary_path) + 'trainmodel', 'wb') as picklefile:  
            pickle.dump(train_model_ob, picklefile)
    elif(mode == 'append'):
        # print("the append mode"+str(input_dataset))
        with open(str(vocabulary_path) + 'trainmodel', 'ab+') as picklefile:  
            pickle.dump(train_model_ob, picklefile)
    
    final_doc_class_label = multi_nativebayes_verna_predict(train_model_ob, testcopy)
    
    return accuracy_score(testlabelcopy, final_doc_class_label) 


def live_verna_detection(vocabulary_path, web_param):
    with open(str(vocabulary_path) + 'trainmodel', 'rb') as training_model:  
        train_model1 = pickle.load(training_model)
    
    doc_class_label = live_multi_nativebayes_verna_predict(train_model1, web_param)     
    # print("this class level is ",doc_class_label)
    return doc_class_label


def bulk_live_detection(train_model1, web_param):
    
    doc_class_label = live_multi_nativebayes_verna_predict(train_model1, web_param)     
    # print("this class level is ",doc_class_label)
    return doc_class_label


def bulk_live_verna_detection(input_dataset, context_path, payload, label):
    bulk_verna_detect_result = []
    with open(str(context_path) + 'trainmodel', 'rb') as training_model:  
        train_model1 = pickle.load(training_model)
    trainDF = load_cvs_dataset(input_dataset)
    #txt_label = trainDF[label]
    txt_text = trainDF[payload]

    for doc in txt_text:
        doc_class_label = live_multi_nativebayes_verna_predict(train_model1, doc)
        #bulk_verna_detect_result.append("" + str(doc) + "," + doc_class_label + "")
        bulk_verna_detect_result.append(doc_class_label)
        #bulk_verna_detect_result.append(doc)
        #print("this doc_class_label is "+str(len(bulk_verna_detect_result)))
    
    return bulk_verna_detect_result


def test_java_python_function(web_param):
    print("the web param1556 :" + str(web_param))
        
    return ""

