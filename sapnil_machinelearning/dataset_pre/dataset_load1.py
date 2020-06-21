'''
Created on Apr 17, 2019

@author: Nasir uddin
'''
import pandas
import pandas as pd
import numpy as np
    
    
def load_cvs_dataset(dataset_path):
    # Set Random seed
    np.random.seed(500)
    # Add the Data using pandas
    Corpus = pd.read_csv(dataset_path, encoding='latin-1', error_bad_lines=False)

    return Corpus
