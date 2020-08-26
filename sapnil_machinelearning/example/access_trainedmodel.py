'''
Created on Apr 23, 2019

@author: Nasir uddin
'''

import numpy as np
import pandas as pd
import pickle
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.feature_extraction.text import TfidfTransformer
from sklearn.feature_extraction.text import  CountVectorizer
from sklearn import model_selection

def main():
    np.random.seed(500)
   
    ##train_data = pd.read_csv('../response.csv', encoding='latin-1', error_bad_lines=False)
   ## payload=train_data['payload_name']
    ##label=train_data['payload_label']
    
    result=[]
     
    with open('../text_classifier.pickle', 'rb') as training_model:
        model = pickle.load(training_model)
        
    with open('../tfidf.pickle', 'rb') as vocabulary_file:  
        tfidfconverter1= pickle.load(vocabulary_file) 
        
    
       
    tfidf_vect2 = TfidfVectorizer(analyzer='word', max_features=5000)   
    new_observation = [[ 5.2,  3.2,  1.1,  0.1]]
    X= [['nasir','forhad']]
    #matrix=tfidf_vect2.fit_transform(X) 
    result=model.predict(new_observation)
    print(result)
    #for pram in payload:
        #X= [''+str(pram)]
    #matrix=tfidf_vect2.transform(Train_X) 
    #Train_X, Test_X, Train_Y, Test_Y = model_selection.train_test_split(payload, label)   
    ''''
    vectorizer = CountVectorizer()
    trainData = vectorizer.fit_transform(payload)
    tfidf_transformer = TfidfTransformer()
    '''
    #tfidfData = tfidfconverter1.transform(payload).toarray()
    #print(tfidfData)
    #model.predict(tfidfData)
   # for result1 in result:
    #print(result)
    
    #append the to vocabulery maxtrix 
    

if __name__ == '__main__':
    main()