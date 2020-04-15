'''
Created on Apr 6, 2020

@author: Nasir(programmer)
'''


class train_model:
    '''
    classdocs
    '''


    def __init__(self, logprior,class_eachtoken_likelihood,vocabulary,class_labels):
        '''
        Constructor
        '''
        self.logprior=logprior
        self.class_eachtoken_likelihood=class_eachtoken_likelihood
        self.vocabulary=vocabulary
        self.class_labels=class_labels
    def get_logprior(self):
        return self.logprior
   
    def get_vocabulary(self):
        return self.vocabulary
    def get_class_labels(self):
        return self.class_labels  
    def get_class_eachtoken_likelihood(self):
        return self.class_eachtoken_likelihood    