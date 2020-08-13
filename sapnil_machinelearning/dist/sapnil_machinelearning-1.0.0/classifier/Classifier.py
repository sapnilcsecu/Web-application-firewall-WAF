'''
Created on Apr 15, 2019

@author: nasir uddin
'''

from sklearn.metrics import accuracy_score

# load the dataset


def train_model(classifier, train_input, test_input, train_target, test_target, is_neural_net=False):
    # fit the training dataset on the classifier
    
    classifier.fit(train_input, train_target)
    
    # predict the labels on validation dataset
    predictions = classifier.predict(test_input)
    
    if is_neural_net:
        predictions = predictions.argmax(axis=-1)
    
    # print(classifier.predict(gettfidf_vect.transform(["A FIVE STAR BOOK"])))
    return accuracy_score(predictions, test_target)

