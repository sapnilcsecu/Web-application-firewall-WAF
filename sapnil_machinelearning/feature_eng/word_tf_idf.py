'''
Created on Apr 18, 2019

@author: Nasir uddin
'''
from sklearn import model_selection, preprocessing
from sklearn.feature_extraction.text import TfidfVectorizer

#

    
    # word level tf-idf
def word_tf_idf(txt_text, txt_label):
    Train_X, Test_X, Train_Y, Test_Y = model_selection.train_test_split(txt_text, txt_label)
    # split the dataset into training and validation datasets 
    
    # label encode the target variable 
    '''
    encoder = preprocessing.LabelEncoder()
    Train_Y = encoder.fit_transform(Train_Y)
    Test_Y = encoder.fit_transform(Test_Y)
    '''
    # split the dataset into training and validation datasets 
    
    tfidf_vect = TfidfVectorizer(analyzer='word', max_features=5000)
    tfidf_vect.fit(txt_text)
    Train_X_Tfidf = tfidf_vect.transform(Train_X)
    Test_X_Tfidf = tfidf_vect.transform(Test_X)
    
    return Train_X_Tfidf, Test_X_Tfidf, Train_Y, Test_Y, tfidf_vect

# ngram level tf-idf 
    """
   
    
   

    # create a count vectorizer object 
  
        """
