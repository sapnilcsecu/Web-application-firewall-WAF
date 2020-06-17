'''
Created on Aug 19, 2019

@author: Nasir uddin
'''
from stop_words import get_stop_words
from nltk.tokenize import word_tokenize
from nltk.corpus import stopwords
import nltk
# from nltk.stem import WordNetLemmatizer 
import string
import re
from nltk.corpus import wordnet as wn
from nltk.stem.wordnet import WordNetLemmatizer
from nltk import  pos_tag
from collections import defaultdict
from model.model_data import model_data


def count_word_fit(doc_list, class_labels):
    vocabularyCount = 0  
    vocabulary = []
    tag_map = defaultdict(lambda : wn.NOUN)
    tag_map['J'] = wn.ADJ
    tag_map['V'] = wn.VERB
    tag_map['R'] = wn.ADV
    
    # Using Python's stop-words package to get the stop words in English
    
    stop_words = get_stop_words('english')
    for doc in doc_list:
        
        result_doc = re.sub(r'\d+', '', doc)
        
        words = word_tokenize(result_doc)
        
        low_tokens = [w.lower() for w in words]
      
        # REMOVE punctuation mark
        table = str.maketrans('', '', string.punctuation)
        pun_words = [w.translate(table) for w in low_tokens]
        emp_str_list = list(filter(None, pun_words)) 
        # REMOVE punctuation mark
        
        # Lemmatize list of words and join
        # Init the Wordnet Lemmatizer
        # Declaring Empty List to store the words that follow the rules for this step
        
        Final_words = []
        # Initializing WordNetLemmatizer()
        word_Lemmatized = WordNetLemmatizer()
        # pos_tag function below will provide the 'tag' i.e if the word is Noun(N) or Verb(V) or something else.
        for word, tag in pos_tag(emp_str_list):
        # Below condition is to check for Stop words and consider only alphabets
            if word not in stopwords.words('english') and word.isalpha():
                word_Final = word_Lemmatized.lemmatize(word, tag_map[tag[0]])
                Final_words.append(word_Final)
        
        # remove stop words
        stop_words = set(stopwords.words('english'))
        rvm_stop_words = [w for w in Final_words if not w in stop_words]
        # remove stop words
        
        for word in rvm_stop_words:                        
            if word not in vocabulary:
                vocabulary.append(word)
                vocabularyCount = vocabularyCount + 1
    
    temp_class_labels = class_labels
    class_labels = list(dict.fromkeys(class_labels))    
    
    total_class_token = {}
    
    # print(vocabulary)
    class_eachtoken_count = {} 
    
    for class_label in class_labels: 
        total_class_token[class_label] = 0
        class_eachtoken_count[class_label] = {}
        for voc in vocabulary:
            class_eachtoken_count[class_label] [voc] = 0
    
    doccount = 0
    total_voca_count = 0
    for doc in doc_list:
        words = doc.split(" ");
       
        class_label = temp_class_labels[doccount]
      
        for word in words:
            if word in vocabulary:
                class_eachtoken_count[class_label][word] = class_eachtoken_count[class_label][word] + 1 
                total_class_token[class_label] = total_class_token[class_label] + 1
                total_voca_count = total_voca_count + 1
        
        doccount = doccount + 1
         
    data = model_data(vocabularyCount, class_eachtoken_count, total_class_token, class_labels, vocabulary)       
    return data
    # return 0
    
    
def count_ver_word_fit(doc_list, class_labels): 
    vocabularyCount = 0
    vocabulary = []
   
    # Using Python's stop-words package to get the stop words in English
    
    for doc in doc_list:
        doc=re.sub("\d+"," ",doc)
       
        #doc = re.sub(r'[^a-z]+', ' ', doc)
        #doc = re.sub(r'\s+', ' ', doc, flags=re.I)
        #print('the doc is11 ',doc)
        # print('the doc is ',doc)
        # documents.append(str(doc))
        
        # remove stop words
        result_doc=word_tokenize(doc)
        #testing purpose
        '''
        tagged_sentence = nltk.pos_tag(result_doc)
        edited_sentence = [word for word,tag in tagged_sentence if tag != 'NNP' and tag != 'NNPS' and tag != 'NNS' and tag != 'NN' and tag != 'JJ' and tag != 'JJR' and tag != 'JJS']
        '''
        #testing purpose
        # REMOVE punctuation mark
        #print('the doc is11 ',result_doc)
        for word in result_doc:                        
            if word not in vocabulary:
                
                vocabulary.append(word.lower())
                vocabularyCount = vocabularyCount + 1
    
    temp_class_labels = class_labels
    class_labels = list(dict.fromkeys(class_labels))    
    #print("the class label ",class_labels)
    total_class_token = {}
    
    # print(vocabulary)
    class_eachtoken_count = {} 
    
    for class_label in class_labels: 
        total_class_token[class_label] = 0
        class_eachtoken_count[class_label] = {}
        for voc in vocabulary:
            class_eachtoken_count[class_label] [voc] = 0
    
    doccount = 0
    total_voca_count = 0
    for doc in doc_list:
        words = word_tokenize(doc);
       
        class_label = temp_class_labels[doccount]
      
        for word in words:
            if word in vocabulary:
                class_eachtoken_count[class_label][word] = class_eachtoken_count[class_label][word] + 1 
                total_class_token[class_label] = total_class_token[class_label] + 1
                #print("total_class_token is ",total_class_token)
                total_voca_count = total_voca_count + 1
        
        doccount = doccount + 1
         
    data = model_data(vocabularyCount, class_eachtoken_count, total_class_token, class_labels, vocabulary)
    return data   
