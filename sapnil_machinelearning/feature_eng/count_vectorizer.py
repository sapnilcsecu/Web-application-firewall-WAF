'''
Created on Apr 19, 2019

@author: Nasir uddin
'''

from sklearn.feature_extraction.text import  CountVectorizer
from sklearn import model_selection

# NB, count_vectorizer:  83.04


def count_vectorizer(txt_text, txt_label):
    Train_X, Test_X, Train_Y, Test_Y = model_selection.train_test_split(txt_text, txt_label,test_size=1)
    #print("test size id ",len(Test_X))
    count_vect = CountVectorizer(analyzer='word')
    count_vect.fit(txt_text)
    
    # transform the training and validation data using count vectorizer object
    Train_X_count = count_vect.transform(Train_X)
    Test_X_count = count_vect.transform(Test_X)   
    
    return Train_X_count, Test_X_count, Train_Y, Test_Y, count_vect
    
