'''
Created on Aug 6, 2019

@author: Nasir uddin
'''
from nltk.tokenize import word_tokenize
from nltk.corpus import stopwords
import string
from nltk.stem.porter import PorterStemmer
import re


def txt_clean(docList):
    
    documents = []
    for doc in docList:
        
        tokens = word_tokenize(doc)
        # convert to lower case
        tokens = [w.lower() for w in tokens]
        # remove punctuation from each word
        
        table = str.maketrans('', '', string.punctuation)
        stripped = [w.translate(table) for w in tokens]
        # remove remaining tokens that are not alphabetic
        words = [word for word in stripped if word.isalpha()]
        # filter out stop words
        
        stop_words = set(stopwords.words('english'))
        words = [w for w in words if not w in stop_words]
        
        porter = PorterStemmer()
        stemmed = [porter.stem(word) for word in words]
        
        documents.append(str(stemmed))
        
    return documents


def vulnerability_txt_clean(doc):
        doc = re.sub(r'[^a-z]+', ' ', doc)
        doc = re.sub(r'\s+', ' ', doc, flags=re.I)
        result_doc=word_tokenize(doc)
            
        return result_doc

