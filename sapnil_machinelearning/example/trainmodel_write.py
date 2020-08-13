'''
Created on May 7, 2019

@author: Nasir uddin
'''

import numpy as np
import pandas as pd
import pickle
from nltk.corpus import stopwords
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.model_selection import train_test_split
from sklearn import naive_bayes
from sklearn.metrics import accuracy_score


def main():
    
    np.random.seed(500)
    # Add the Data using pandas
    train_data = pd.read_csv('../payload_full_path.csv', encoding='latin-1', error_bad_lines=False)
    payload=train_data['payload']
    label=train_data['label']
    
    
    
    tfidfconverter = TfidfVectorizer(max_features=1500, min_df=5, max_df=0.7, stop_words=stopwords.words('english'))
    X = tfidfconverter.fit_transform(payload).toarray()
    X_train, X_test, y_train, y_test = train_test_split(X,label, test_size=0.2, random_state=0)
    
    classifier=naive_bayes.MultinomialNB()
    classifier.fit(X_train, y_train)
    
    # predict the labels on validation dataset
    predictions = classifier.predict(X_test)
   
   
    acc=accuracy_score(predictions, y_test)*100
    print("acc is ",acc)
  
    pickle.dump(classifier, open("../text_classifier.pickle", "wb"))
    pickle.dump(tfidfconverter, open("../tfidf.pickle", "wb"))
    
    #  Build Text Classification Model and Evaluating the Model
if __name__ == '__main__':
    main()