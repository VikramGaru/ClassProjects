import numpy as np
from scipy.optimize import minimize
from scipy.io import loadmat
from math import sqrt
import pickle


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
    return  1.0/(1.0+np.exp(-1.0*z))  # your code here


def preprocess():
    numberTrain = 50000
    numberAttribute = 784
    with open('AI_quick_draw.pickle', 'rb') as open_ai_quick:
        train_data1 = pickle.load(open_ai_quick)
        train_label1 = pickle.load(open_ai_quick)
        test_data = pickle.load(open_ai_quick)
        test_label = pickle.load(open_ai_quick)
    train_data1 = train_data1.astype(np.float64) / 255.0
    test_data = test_data.astype(np.float64) / 255.0
    permutation = np.random.permutation(range(train_data1.shape[0]))
    validation_data = train_data1[permutation[numberTrain:], :]
    validation_label = train_label1[permutation[numberTrain:]]
    train_data = train_data1[permutation[0:numberTrain], :]
    train_label = train_label1[permutation[0:numberTrain]]
    toRemove = []
    for i in range(numberAttribute):
        if np.ptp(train_data[:, i]) == 0.0 and \
                        np.ptp(validation_data[:, i]) == 0.0:
            toRemove.append(i)
    train_data = np.delete(train_data, toRemove, axis=1)
    test_data = np.delete(test_data, toRemove, axis=1)
    validation_data = np.delete(validation_data, toRemove, axis=1)
    print("Preprocessing Done!")
    return train_data, train_label, validation_data, validation_label, test_data, test_label


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

train_data, train_label, validation_data, validation_label, test_data, test_label = preprocess()

#  Train Neural Network

# set the number of nodes in input unit (not including bias unit)
n_input = train_data.shape[1]

# set the number of nodes in hidden unit (not including bias unit)
n_hidden = 50

# set the number of nodes in output unit
n_class = 10

# initialize the weights into some random matrices
initial_w1 = initializeWeights(n_input, n_hidden)
initial_w2 = initializeWeights(n_hidden, n_class)

# unroll 2 weight matrices into single column vector
initialWeights = np.concatenate((initial_w1.flatten(), initial_w2.flatten()), 0)

# set the regularization hyper-parameter
lambdaval = 0

args = (n_input, n_hidden, n_class, train_data, train_label, lambdaval)

# Train Neural Network using fmin_cg or minimize from scipy,optimize module. Check documentation for a working example

opts = {'maxiter': 50}  # Preferred value.

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

print('\n Training set Accuracy:' + str(100 * np.mean((predicted_label == train_label).astype(float))) + '%')

predicted_label = nnPredict(w1, w2, validation_data)

# find the accuracy on Validation Dataset

print('\n Validation set Accuracy:' + str(100 * np.mean((predicted_label == validation_label).astype(float))) + '%')

predicted_label = nnPredict(w1, w2, test_data)

# find the accuracy on Validation Dataset

print('\n Test set Accuracy:' + str(100 * np.mean((predicted_label == test_label).astype(float))) + '%')
