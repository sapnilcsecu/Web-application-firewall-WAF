'''
Created on Aug 24, 2019

@author: Nasir uddin
'''

class model_data:
    '''
    classdocs
    '''
    
   

    def __init__(self, vocabularyCount,class_eachtoken_count,total_class_token,class_labels,vocabulary):
        '''
        Constructor
        '''
        self.vocabularyCount=vocabularyCount
        self.class_eachtoken_count=class_eachtoken_count
        self.total_class_token=total_class_token
        self.class_labels=class_labels
        self.vocabulary=vocabulary
        
        
        
    def get_vocabularyCount(self): 
        return self.vocabularyCount   
    
    def get_class_eachtoken_count(self):
        return self.class_eachtoken_count
    
    def get_total_class_token(self):
        return self.total_class_token
    
    def get_class_labels(self):
        return self.class_labels
    
    def get_vocabulary(self):
        return self.vocabulary