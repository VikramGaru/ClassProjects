import numpy as np
from scipy.optimize import minimize
from scipy.io import loadmat
from math import sqrt
import os
import csv
import time


def initializeWeights(n_in, n_out):
    """
    # initializeWeights return the random weights for Neural Network given the
    # number of node in the input layer and output layer

    # Input:
    # n_in: number of nodes of the input layer
    # n_out: number of nodes of the output layer

    # Output:
    # W: matrix of random initial weights with size (n_out x (n_in + 1))"""

    epsilon = sqrt(6) / sqrt(n_in + n_out + 1)
    W = (np.random.rand(n_out, n_in + 1) * 2 * epsilon) - epsilon
    return W


def sigmoid(z):
    """# Notice that z can be a scalar, a vector or a matrix
    # return the sigmoid of input z"""

    return 1.0 / (1.0 + np.exp(-1.0 * z))


def preprocess():
    """ Input:
     Although this function doesn't have any input, you are required to load
     the MNIST data set from file 'mnist_all.mat'.

     Output:
     train_data: matrix of training set. Each row of train_data contains
       feature vector of a image
     train_label: vector of label corresponding to each image in the training
       set
     validation_data: matrix of training set. Each row of validation_data
       contains feature vector of a image
     validation_label: vector of label corresponding to each image in the
       training set
     test_data: matrix of training set. Each row of test_data contains
       feature vector of a image
     test_label: vector of label corresponding to each image in the testing
       set

     Things to do for preprocessing step:
     - remove features that have the same value for all data points
           with corresponding labels
     - convert original data set from integer to double by using double()
           function
     - normalize the data to [0, 1]
     - divide the original data set to training, validation and testing set"""
    numberTrain=50000
    numberAttribute=784 #28*28
    train_data1=np.zeros(shape=(numberTrain,numberAttribute))
    train_label1=np.zeros(shape=(numberTrain,))
    test_data=np.zeros(shape=(10000,numberAttribute))
    test_label=np.zeros(shape=(10000,))
    #validation_data=np.zeros(shape=(10000, numberAttribute))
    #validation_label=np.zeros(shape=(10000,))
    mat = loadmat('mnist_all.mat') #loads the MAT object as a Dictionary
    """train_data1 = np.concatenate((mat['train0'], mat['train1'],
                                 mat['train2'], mat['train3'],
                                 mat['train4'], mat['train5'],
                                 mat['train6'], mat['train7'],
                                 mat['train8'], mat['train9']), 0)
    train_label1 = np.concatenate((np.ones((mat['train0'].shape[0], 1), dtype='uint8'),
                                  2 * np.ones((mat['train1'].shape[0], 1), dtype='uint8'),
                                  3 * np.ones((mat['train2'].shape[0], 1), dtype='uint8'),
                                  4 * np.ones((mat['train3'].shape[0], 1), dtype='uint8'),
                                  5 * np.ones((mat['train4'].shape[0], 1), dtype='uint8'),
                                  6 * np.ones((mat['train5'].shape[0], 1), dtype='uint8'),
                                  7 * np.ones((mat['train6'].shape[0], 1), dtype='uint8'),
                                  8 * np.ones((mat['train7'].shape[0], 1), dtype='uint8'),
                                  9 * np.ones((mat['train8'].shape[0], 1), dtype='uint8'),
                                  10 * np.ones((mat['train9'].shape[0], 1), dtype='uint8')), 0)
    test_label = np.concatenate((np.ones((mat['test0'].shape[0], 1), dtype='uint8'),
                                 2 * np.ones((mat['test1'].shape[0], 1), dtype='uint8'),
                                 3 * np.ones((mat['test2'].shape[0], 1), dtype='uint8'),
                                 4 * np.ones((mat['test3'].shape[0], 1), dtype='uint8'),
                                 5 * np.ones((mat['test4'].shape[0], 1), dtype='uint8'),
                                 6 * np.ones((mat['test5'].shape[0], 1), dtype='uint8'),
                                 7 * np.ones((mat['test6'].shape[0], 1), dtype='uint8'),
                                 8 * np.ones((mat['test7'].shape[0], 1), dtype='uint8'),
                                 9 * np.ones((mat['test8'].shape[0], 1), dtype='uint8'),
                                 10 * np.ones((mat['test9'].shape[0], 1), dtype='uint8')), 0)
    test_data = np.concatenate((mat['test0'], mat['test1'],
                                mat['test2'], mat['test3'],
                                mat['test4'], mat['test5'],
                                mat['test6'], mat['test7'],
                                mat['test8'], mat['test9']), 0)"""
    for i in range(0, 10):
        train_data1 = np.vstack((train_data1, mat['train' + str(i)]))
        train_label1 = np.hstack((train_label1, i * np.ones(mat['train' + str(i)].shape[0])))
        test_data = np.vstack((test_data, mat['test' + str(i)]))
        test_label = np.hstack((test_label, i * np.ones(mat['test' + str(i)].shape[0])))
    # remove features that have same value for all points in the training data
    # convert data to double
    train_data1=train_data1.astype(np.float64)
    test_data=test_data.astype(np.float64)
    # normalize data to [0,1]
    train_data1=train_data1/255.0
    test_data=test_data/255.0
    # Split train_data and train_label into train_data, validation_data and train_label, validation_label
    permutation=np.random.permutation(range(train_data1.shape[0]))
    train_data=train_data1[permutation[0:numberTrain],:]
    train_label=train_label1[permutation[0:numberTrain]]
    # replace the next two lines
    validation_data = train_data1[permutation[numberTrain:],:]
    validation_label = train_label1[permutation[numberTrain:]]
    #ptp max-min
    toRemove=[]
    for i in range(numberAttribute):
        if np.ptp(train_data[:, i]) == 0.0 and \
                        np.ptp(validation_data[:, i]) == 0.0:
            toRemove.append(i)
    print(toRemove)
    train_data=np.delete(train_data,toRemove,axis=1)
    test_data=np.delete(test_data,toRemove,axis=1)
    validation_data=np.delete(validation_data,toRemove,axis=1)
    print("preprocess done!")
    return train_data, train_label, validation_data, validation_label, test_data, test_label,toRemove


def nnObjFunction(params, *args):
    """% nnObjFunction computes the value of objective function (negative log
    %   likelihood error function with regularization) given the parameters
    %   of Neural Networks, thetraining data, their corresponding training
    %   labels and lambda - regularization hyper-parameter.

    % Input:
    % params: vector of weights of 2 matrices w1 (weights of connections from
    %     input layer to hidden layer) and w2 (weights of connections from
    %     hidden layer to output layer) where all of the weights are contained
    %     in a single vector.
    % n_input: number of node in input layer (not include the bias node)
    % n_hidden: number of node in hidden layer (not include the bias node)
    % n_class: number of node in output layer (number of classes in
    %     classification problem
    % training_data: matrix of training data. Each row of this matrix
    %     represents the feature vector of a particular image
    % training_label: the vector of truth label of training images. Each entry
    %     in the vector represents the truth label of its corresponding image.
    % lambda: regularization hyper-parameter. This value is used for fixing the
    %     overfitting problem.

    % Output:
    % obj_val: a scalar value representing value of error function
    % obj_grad: a SINGLE vector of gradient value of error function
    % NOTE: how to compute obj_grad
    % Use backpropagation algorithm to compute the gradient of error function
    % for each weights in weight matrices.

    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    % reshape 'params' vector into 2 matrices of weight w1 and w2
    % w1: matrix of weights of connections from input layer to hidden layers.
    %     w1(i, j) represents the weight of connection from unit j in input
    %     layer to unit i in hidden layer.
    % w2: matrix of weights of connections from hidden layer to output layers.
    %     w2(i, j) represents the weight of connection from unit j in hidden
    %     layer to unit i in output layer."""

    n_input, n_hidden, n_class, training_data, training_label, lambdaval = args

    w1 = params[0:n_hidden * (n_input + 1)].reshape((n_hidden, (n_input + 1)))
    w2 = params[(n_hidden * (n_input + 1)):].reshape((n_class, (n_hidden + 1)))
    obj_val = training_data.shape[0]
    grad_w1 = 0.0
    grad_w2 = 0.0
    error = 0.0
    # Your code here
    #
    #
    #
    #
    #
    for i in range(obj_val):
        current = training_data[i]
        expectedOutput = np.zeros(n_class)
        expectedOutput[int(training_label[i]) - 1] = 1.0
        hiddenOut, out = feedForward(w1, w2, current)
        error = error + np.sum(expectedOutput * np.log(out) + (1.0 - expectedOutput) * np.log(1.0 - out))
        delOut = out - expectedOutput
        sumDelw = np.dot(delOut, w2)
        sumDelw = sumDelw[:-1]
        delHidden = (1.0 - hiddenOut) * hiddenOut * sumDelw
        grad_w2 = grad_w2 + delOut.reshape((n_class, 1)) * np.hstack((hiddenOut, np.ones(1)))
        grad_w1 = grad_w1 + delHidden.reshape((n_hidden, 1)) * np.hstack((current, np.ones(1)))
        i = i + 1
    error = error / (-1 * obj_val)
    error = error + ((lambdaval * np.sum(np.sum(w1 * w1)) + np.sum(np.sum(w2 * w2))) / (2.0 * obj_val))
    grad_w1 = (grad_w1 + (lambdaval * w1)) / obj_val
    grad_w2 = (grad_w2 + (lambdaval * w2)) / obj_val
    # Make sure you reshape the gradient matrices to a 1D array. for instance if your gradient matrices are grad_w1 and grad_w2
    # you would use code similar to the one below to create a flat array
    obj_grad = np.concatenate((grad_w1.flatten(), grad_w2.flatten()), 0)
    # obj_grad = np.array([])
    obj_val = error
    return (obj_val, obj_grad)


def nnPredict(w1, w2, data):
    """% nnPredict predicts the label of data given the parameter w1, w2 of Neural
    % Network.

    % Input:
    % w1: matrix of weights of connections from input layer to hidden layers.
    %     w1(i, j) represents the weight of connection from unit i in input
    %     layer to unit j in hidden layer.
    % w2: matrix of weights of connections from hidden layer to output layers.
    %     w2(i, j) represents the weight of connection from unit i in input
    %     layer to unit j in hidden layer.
    % data: matrix of data. Each row of this matrix represents the feature
    %       vector of a particular image

    % Output:
    % label: a column vector of predicted labels"""

    labels = []
    # Your code here
    for i in data:
        outputHidden, output = feedForward(w1, w2, i)
        predictedDigit = np.argmax(output)
        labels.append(predictedDigit)
    labels = np.asarray(labels)
    return labels


def feedForward(a, b, i):
    outputHidden = sigmoid(np.sum(a * (np.hstack((i, np.ones(1.0)))), axis=1))
    output = sigmoid(np.sum(b * (np.hstack((outputHidden, np.ones(1.0)))), axis=1))
    return (outputHidden, output)


"""**************Neural Network Script Starts here********************************"""

train_data, train_label, validation_data, validation_label, test_data, test_label,toRemove = preprocess()

#  Train Neural Network

# set the number of nodes in input unit (not including bias unit)
n_input = train_data.shape[1]

# set the number of nodes in hidden unit (not including bias unit)
n_hidden = 50

# set the number of nodes in output unit
n_class = 10

if os.path.exists('output.csv'):
    os.remove('output.csv')

with open('output.csv', 'a', newline='\n', encoding='utf-8') as csvFile:
    fieldNames = ['Matrix_iterations','Hidden', 'Lambda', 'Start time', 'End time', 'Execution time', 'train_accuracy', 'validation_accuracy', 'test_accuracy']
    writer = csv.DictWriter(csvFile, fieldnames=fieldNames)
    writer.writeheader()
    n=50
    opts = {'maxiter': 50}  # Preferred value.
    for node in np.arange(0,20,4):
      n_hidden=node
      for lamb in np.arange(0,60,5):
        now_time = time.time()
        start_time = time.strftime("%X")
        lambdaval = lamb
        # initialize the weights into some random matrices
        initial_w1 = initializeWeights(n_input, n_hidden)
        initial_w2 = initializeWeights(n_hidden, n_class)
        # unroll 2 weight matrices into single column vector
        initialWeights = np.concatenate((initial_w1.flatten(), initial_w2.flatten()), 0)
        # set the regularization hyper-parameter
        args = (n_input, n_hidden, n_class, train_data, train_label, lambdaval)
        # Train Neural Network using fmin_cg or minimize from scipy,optimize module. Check documentation for a working example
        nn_params = minimize(nnObjFunction, initialWeights, jac=True, args=args, method='CG', options=opts)
        # In Case you want to use fmin_cg, you may have to split the nnObjectFunction to two functions nnObjFunctionVal
        # and nnObjGradient. Check documentation for this function before you proceed.
        # nn_params, cost = fmin_cg(nnObjFunctionVal, initialWeights, nnObjGradient,args = args, maxiter = 50)
        # Reshape nnParams from 1D vector into w1 and w2 matrices
        w1 = nn_params.x[0:n_hidden * (n_input + 1)].reshape((n_hidden, (n_input + 1)))
        w2 = nn_params.x[(n_hidden * (n_input + 1)):].reshape((n_class, (n_hidden + 1)))
        # Test the computed parameters
        predicted_label = nnPredict(w1, w2, train_data)
        # find the accuracy on Training Dataset
        trainAccuracy=str(100 * np.mean((predicted_label == train_label).astype(float))) + '%'
        print('\n Training set Accuracy:' + str(100 * np.mean((predicted_label == train_label).astype(float))) + '%')
        predicted_label = nnPredict(w1, w2, validation_data)
        # find the accuracy on Validation Dataset
        validAccuracy=str(100 * np.mean((predicted_label == validation_label).astype(float))) + '%'
        print('\n Validation set Accuracy:' + str(100 * np.mean((predicted_label == validation_label).astype(float))) + '%')
        predicted_label = nnPredict(w1, w2, test_data)
        # find the accuracy on Validation Dataset
        testAccuracy=str(100 * np.mean((predicted_label == test_label).astype(float))) + '%'
        print('\n Test set Accuracy:' + str(100 * np.mean((predicted_label == test_label).astype(float))) + '%')
        end_time = time.strftime("%X")
        execution_time = str(round(time.time() - now_time, 2))
        writer.writerow({'Matrix_iterations':n,'Hidden': n_hidden, 'Lambda': lambdaval, 'Start time': start_time, 'End time': end_time , 'Execution time': execution_time , 'train_accuracy': trainAccuracy, 'validation_accuracy': validAccuracy, 'test_accuracy': testAccuracy})
        print('Matrix_iterations' + str(n) +'number_of_nodes = ' + str(node) + ' and lambda = ' + str(lamb) )
        obj = [toRemove, n_hidden, lambdaval, w1, w2]
        pickle.dump(obj, open('params.pickle', 'wb'))