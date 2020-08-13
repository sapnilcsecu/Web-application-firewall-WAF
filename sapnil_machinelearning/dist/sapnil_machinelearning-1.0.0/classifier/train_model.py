'''
Created on Mar 23, 2020

@author: Nasir uddin
'''
from sklearn import naive_bayes
from feature_eng.word_tf_idf import word_tf_idf
from classifier.Classifier import train_model
from dataset_pre.dataset_load import load_cvs_dataset
import pickle
from classifier.multinomial_nativebayes import live_multi_nativebayes_verna_predict
#from classifier.multinomial_nativebayes import accuracy_score
from sklearn.metrics import accuracy_score
from builtins import str
from sklearn import model_selection
from sklearn.feature_extraction.text import TfidfVectorizer


# this function take separate trainset,testdataset as input


# def train_model(input_dataset,test_dataset,vocabulary_path,payload_col_name,payload_label,test_payload_col_name,test_payload_label):
#partial_fit(self, X, y, classes=None, sample_weight=No
def train_model_write(input_dataset, vocabulary_path, payload_col_name, payload_label):
    # print("the input_dataset22")
    
     #load the dataset
   
    #trainDF=load_cvs_dataset(input_dataset)
    trainDF=load_cvs_dataset(input_dataset)
   
    txt_label=trainDF[payload_label]
    txt_text=trainDF[payload_col_name]
    
    #Text feature engineering 
   

    model_input=word_tf_idf(txt_text,txt_label)
    
    naive=naive_bayes.MultinomialNB()
    accuracy =train_model(naive,model_input[0], model_input[1], model_input[2], model_input[3])
    # print("the write mode"+str(input_dataset))
    
    pickle.dump(naive, open(str(vocabulary_path)+"text_classifier.pickle", "wb"))
    pickle.dump(model_input[4], open(str(vocabulary_path)+"tfidf.pickle", "wb"))   

   
    
    return accuracy*100 


def live_verna_detection(vocabulary_path, input_web_param_path,payload_col_name):
    verify_result = []
    trainDF = load_cvs_dataset(input_web_param_path)

   
    txt_text = trainDF[payload_col_name]
    with open(str(vocabulary_path)+'tfidf.pickle', 'rb') as vocabulary_file:  
       count_vect= pickle.load(vocabulary_file)
    with open(str(vocabulary_path)+'text_classifier.pickle', 'rb') as training_model:  
        model = pickle.load(training_model)
       
    for web_param in txt_text:
        X= [''+str(web_param)]
        #print(model.predict(count_vect.transform(X)))
        
        subj = model.predict(count_vect.transform(X))
       
        verify_result.append(subj[0])
        #verify_result.append(model.predict(count_vect.transform(web_param)))    
   
    
    return verify_result


def bulk_live_detection(train_model1, web_param):
    
    doc_class_label = live_multi_nativebayes_verna_predict(train_model1, web_param)     
    # print("this class level is ",doc_class_label)
    return doc_class_label


def bulk_live_verna_detection(input_dataset, context_path, payload, label):
    bulk_verna_detect_result = []
    
    with open(str(context_path)+'tfidf.pickl', 'rb') as vocabulary_file:  
       count_vect= pickle.load(vocabulary_file)
    with open(str(context_path)+'text_classifier.pickle', 'rb') as training_model:  
        model = pickle.load(training_model)
    
   
    trainDF = load_cvs_dataset(input_dataset)
    #txt_label = trainDF[label]
    txt_text = trainDF[payload]
    
    
    for doc in txt_text:
        X= [''+str(doc)]
        doc_class_label = model.predict(count_vect.transform(X))
        #bulk_verna_detect_result.append("" + str(doc) + "," + doc_class_label + "")
        bulk_verna_detect_result.append(doc_class_label[0])
        #bulk_verna_detect_result.append(doc)
        #print("this doc_class_label is "+str(len(bulk_verna_detect_result)))
    
    return bulk_verna_detect_result


def test_java_python_function(web_param):
    print("the web param1556 :" + str(web_param))
        
    return ""

