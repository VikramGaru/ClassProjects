import numpy as np
from scipy.optimize import minimize
from scipy.io import loadmat
from numpy.linalg import det, inv
from math import sqrt, pi
import scipy.io
import matplotlib.pyplot as plt
import pickle
import sys

def ldaLearn(X,y):
    # Inputs
    # X - a N x d matrix with each row corresponding to a training example
    # y - a N x 1 column vector indicating the labels for each training example
    #
    # Outputs
    # means - A k x d matrix containing learnt means for each of the k classes
    # covmat - A single d x d learnt covariance matrix 
    
    # IMPLEMENT THIS METHOD
    d=X.shape[1]
    num=int(np.max(y))
    means=np.empty((d,num))
    for i in range(1,num+1):
        tD=X[np.where(y==i)[0],:]
        means[:,i - 1]=np.mean(tD,axis=0).transpose()
    covmat = (np.cov(X.T))
    return means,covmat

def qdaLearn(X,y):
    # Inputs
    # X - a N x d matrix with each row corresponding to a training example
    # y - a N x 1 column vector indicating the labels for each training example
    #
    # Outputs
    # means - A k x d matrix containing learnt means for each of the k classes
    # covmats - A list of k d x d learnt covariance matrices for each of the k classes
    
    # IMPLEMENT THIS METHOD
    d=X.shape[1]
    num=int(np.max(y))
    means=np.empty((d, num))
    covmats=[]
    for i in range(1, num + 1):
        tD=X[np.where(y == i)[0], :]
        means[:, i - 1]=np.mean(tD, axis=0).transpose()
        covmats.append(np.cov(np.transpose(tD)))
    return means,covmats

def ldaTest(means,covmat,Xtest,ytest):
    # Inputs
    # means, covmat - parameters of the LDA model
    # Xtest - a N x d matrix with each row corresponding to a test example
    # ytest - a N x 1 column vector indicating the labels for each test example
    # Outputs
    # acc - A scalar accuracy value
    # ypred - N x 1 column vector indicating the predicted labels

    # IMPLEMENT THIS METHOD
    prediction = np.empty((Xtest.shape[0], means.shape[1]))
    for i in range(0, means.shape[1]):
        dot = np.dot(np.linalg.inv(covmat), (Xtest - means[:, i]).T)  # 2x2 , 2x100
        prediction[:, i] = (np.exp(-(1 / 2) * (np.sum((Xtest - means[:, i]) * dot.T, 1)))) * (1 / np.power(np.linalg.det(covmat), 2))
    ypred = np.argmax(prediction, 1)  # get best label
    ypred = ypred + 1
    ytest = ytest.reshape(ytest.size)
    matchingResults = 0
    for i in range(0, ytest.size):
        if ytest[i] == ypred[i]:
            matchingResults = matchingResults + 1
    acc = 100 * (matchingResults / ytest.size)
    return acc,ypred

def qdaTest(means,covmats,Xtest,ytest):
    # Inputs
    # means, covmats - parameters of the QDA model
    # Xtest - a N x d matrix with each row corresponding to a test example
    # ytest - a N x 1 column vector indicating the labels for each test example
    # Outputs
    # acc - A scalar accuracy value
    # ypred - N x 1 column vector indicating the predicted labels

    # IMPLEMENT THIS METHOD
    d=np.zeros((Xtest.shape[0],means.shape[1]))
    for i in range(means.shape[1]):
        diff=Xtest-means[:,i]
        dot=np.dot(diff,inv(covmats[i]))
        s=np.sum(diff*dot,1)
        d[:,i]=(np.exp(-1*s/2))/(sqrt(pi*2)*np.power(det(covmats[i]),0.5))
    ypred = np.argmax(d,1)
    ypred = ypred + 1
    acc = 100 * np.mean(ypred == ytest.T)
    return acc,ypred

def learnOLERegression(X,y):
    # Inputs:                                                         
    # X = N x d 
    # y = N x 1                                                               
    # Output: 
    # w = d x 1 
    
    # IMPLEMENT THIS METHOD
    productx = np.dot(X.T, X)
    producty = np.dot(X.T, y)
    w = np.dot(np.linalg.inv(productx), producty)
    return w

def learnRidgeRegression(X,y,lambd):
    # Inputs:
    # X = N x d                                                               
    # y = N x 1 
    # lambd = ridge parameter (scalar)
    # Output:                                                                  
    # w = d x 1                                                                

    # IMPLEMENT THIS METHOD
    n = X.shape[1]
    l = lambd * np.identity(n)
    x = X.T
    prodx = np.dot(x, X)
    prody = np.dot(x, y)
    inv = np.linalg.inv(prodx + l)
    w = np.dot(inv, prody)
    return w

def testOLERegression(w,Xtest,ytest):
    # Inputs:
    # w = d x 1
    # Xtest = N x d
    # ytest = X x 1
    # Output:
    # mse
    
    # IMPLEMENT THIS METHOD
    mse=np.sum((ytest-np.dot(Xtest,w))**2)/len(Xtest)
    return mse

def regressionObjVal(w, X, y, lambd):

    # compute squared error (scalar) and gradient of squared error with respect
    # to w (vector) for the given data X and y and the regularization parameter
    # lambda                                                                  

    # IMPLEMENT THIS METHOD
    w=np.asmatrix(w).transpose()
    error=0.5*(np.dot((y-np.dot(X,w)).transpose(),y-np.dot(X,w))+lambd*np.dot(w.transpose(),w))
    error_grad=-(np.dot(X.transpose(),(y-np.dot(X,w))))+lambd*w
    error_grad=np.squeeze(np.array(error_grad))
    return error, error_grad

def mapNonLinear(x,p):
    # Inputs:                                                                  
    # x - a single column vector (N x 1)                                       
    # p - integer (>= 0)                                                       
    # Outputs:                                                                 
    # Xp - (N x (p+1)) 
    
    # IMPLEMENT THIS METHOD
    s=x.shape[0]
    Xp=np.zeros((s,p+1))
    for i in range (p+1):
        Xp[:,i]=np.power(x,i)
    return Xp

# Main script

# Problem 1
# load the sample data                                                                 
if sys.version_info.major == 2:
    X,y,Xtest,ytest = pickle.load(open('sample.pickle','rb'))
else:
    X,y,Xtest,ytest = pickle.load(open('sample.pickle','rb'),encoding = 'latin1')

# LDA
means,covmat = ldaLearn(X,y)
ldaacc,ldares = ldaTest(means,covmat,Xtest,ytest)
print('LDA Accuracy = '+str(ldaacc))
# QDA
means,covmats = qdaLearn(X,y)
qdaacc,qdares = qdaTest(means,covmats,Xtest,ytest)
print('QDA Accuracy = '+str(qdaacc))

# plotting boundaries
x1 = np.linspace(-5,20,100)
x2 = np.linspace(-5,20,100)
xx1,xx2 = np.meshgrid(x1,x2)
xx = np.zeros((x1.shape[0]*x2.shape[0],2))
xx[:,0] = xx1.ravel()
xx[:,1] = xx2.ravel()

fig = plt.figure(figsize=[12,6])
plt.subplot(1, 2, 1)

zacc,zldares = ldaTest(means,covmat,xx,np.zeros((xx.shape[0],1)))
plt.contourf(x1,x2,zldares.reshape((x1.shape[0],x2.shape[0])),alpha=0.3)
plt.scatter(Xtest[:,0:1],Xtest[:,1:],c=ytest)
plt.title('LDA')

plt.subplot(1, 2, 2)

zacc,zqdares = qdaTest(means,covmats,xx,np.zeros((xx.shape[0],1)))
plt.contourf(x1,x2,zqdares.reshape((x1.shape[0],x2.shape[0])),alpha=0.3)
plt.scatter(Xtest[:,0:1],Xtest[:,1:],c=ytest)
plt.title('QDA')

plt.show()
# Problem 2
if sys.version_info.major == 2:
    X,y,Xtest,ytest = pickle.load(open('diabetes.pickle','rb'))
else:
    X,y,Xtest,ytest = pickle.load(open('diabetes.pickle','rb'),encoding = 'latin1')

# add intercept
X_i = np.concatenate((np.ones((X.shape[0],1)), X), axis=1)
Xtest_i = np.concatenate((np.ones((Xtest.shape[0],1)), Xtest), axis=1)

#Without Bias
w = learnOLERegression(X,y)
mle = testOLERegression(w,Xtest,ytest)
mleTrain = testOLERegression(w,X,y)

#With Bias
w_i = learnOLERegression(X_i,y)
mle_i = testOLERegression(w_i,Xtest_i,ytest)
mle_iTrain=testOLERegression(w_i, X_i, y)

print('MSE test without intercept '+str(mle))
print('MSE test with intercept '+str(mle_i))
print('MSE train without intercept '+str(mleTrain))
print('MSE train with intercept '+str(mle_iTrain))

# Problem 3
k = 101
lambdas = np.linspace(0, 1, num=k)
i = 0
mses3_train = np.zeros((k,1))
mses3 = np.zeros((k,1))
for lambd in lambdas:
    w_l = learnRidgeRegression(X_i,y,lambd)
    mses3_train[i] = testOLERegression(w_l,X_i,y)
    mses3[i] = testOLERegression(w_l,Xtest_i,ytest)
    i = i + 1
fig = plt.figure(figsize=[12,6])
plt.subplot(1, 2, 1)
plt.plot(lambdas,mses3_train)
plt.title('MSE for Train Data')
plt.subplot(1, 2, 2)
plt.plot(lambdas,mses3)
plt.title('MSE for Test Data')
min_index = np.argmin(mses3)
Rigde_weights = learnRidgeRegression(X_i, y, lambdas[min_index])
print("Best Lambda: ", lambdas[min_index])
plt.show()


# Problem 4
'''k = 101
lambdas = np.linspace(0, 1, num=k)
i = 0
mses4_train = np.zeros((k,1))
mses4 = np.zeros((k,1))
opts = {'maxiter' : 20}    # Preferred value.                                                
w_init = np.ones((X_i.shape[1],1))
for lambd in lambdas:
    args = (X_i, y, lambd)
    w_l = minimize(regressionObjVal, w_init, jac=True, args=args,method='CG', options=opts)
    w_l = np.transpose(np.array(w_l.x))
    w_l = np.reshape(w_l,[len(w_l),1])
    mses4_train[i] = testOLERegression(w_l,X_i,y)
    mses4[i] = testOLERegression(w_l,Xtest_i,ytest)
    i = i + 1
fig = plt.figure(figsize=[12,6])
plt.subplot(1, 2, 1)
plt.plot(lambdas,mses4_train)
plt.plot(lambdas,mses3_train)
plt.title('MSE for Train Data')
plt.legend(['Using scipy.minimize','Direct minimization'])

plt.subplot(1, 2, 2)
plt.plot(lambdas,mses4)
plt.plot(lambdas,mses3)
plt.title('MSE for Test Data')
plt.legend(['Using scipy.minimize','Direct minimization'])

print("Minimum MSE with out Gradient Descent: " + str(min(mses3)))
print("Minimum MSE with Gradient Descent: " + str(min(mses4)))

print("Minimum Lambda obtained with out Gradient Descent: "+ str(lambdas[np.argmin(mses3)]))
print("Minimum Lambda obtained with Gradient Descent: " + str(lambdas[np.argmin(mses4)]))
plt.show()'''


# Problem 5
'''pmax = 7
lambda_opt = lambdas[np.argmin(mses4)] #0  REPLACE THIS WITH lambda_opt estimated from Problem 3
mses5_train = np.zeros((pmax,2))
mses5 = np.zeros((pmax,2))
for p in range(pmax):
    Xd = mapNonLinear(X[:,2],p)
    Xdtest = mapNonLinear(Xtest[:,2],p)
    w_d1 = learnRidgeRegression(Xd,y,0)
    mses5_train[p,0] = testOLERegression(w_d1,Xd,y)
    mses5[p,0] = testOLERegression(w_d1,Xdtest,ytest)
    w_d2 = learnRidgeRegression(Xd,y,lambda_opt)
    mses5_train[p,1] = testOLERegression(w_d2,Xd,y)
    mses5[p,1] = testOLERegression(w_d2,Xdtest,ytest)

fig = plt.figure(figsize=[12,6])
plt.subplot(1, 2, 1)
plt.plot(range(pmax),mses5_train)
plt.title('MSE for Train Data')
plt.legend(('No Regularization','Regularization'))
plt.subplot(1, 2, 2)
plt.plot(range(pmax),mses5)
plt.title('MSE for Test Data')
plt.legend(('No Regularization','Regularization'))
plt.show()'''

# PROBLEM - 4
k = 101
lambdas = np.linspace(0, 1, num=k)
i = 0
mses4_train = np.zeros((k,1))
mses4 = np.zeros((k,1))
opts = {'maxiter' : 20}    # Preferred value.                                                
w_init = np.ones((X_i.shape[1],1))
for lambd in lambdas:
    args = (X_i, y, lambd)
    w_l = minimize(regressionObjVal, w_init, jac=True, args=args,method='CG', options=opts)
    w_l = np.transpose(np.array(w_l.x))
    w_l = np.reshape(w_l,[len(w_l),1])
    mses4_train[i] = testOLERegression(w_l,X_i,y)
    mses4[i] = testOLERegression(w_l,Xtest_i,ytest)
    i = i + 1

print("Minimum MSE with out Gradient Descent: " + str(min(mses3)))
print("Minimum MSE with Gradient Descent: " + str(min(mses4))+ "\n")

print("Minimum Lambda obtained with out Gradient Descent: "+ str(lambdas[np.argmin(mses3)]))
print("Minimum Lambda obtained with Gradient Descent: " + str(lambdas[np.argmin(mses4)]))

fig = plt.figure(figsize=[12,6])
plt.subplot(1, 2, 1)
plt.plot(lambdas,mses4_train)
plt.plot(lambdas,mses3_train)
plt.title('MSE for Train Data')
plt.legend(['Using scipy.minimize','Direct minimization'])

plt.subplot(1, 2, 2)
plt.plot(lambdas,mses4)
plt.plot(lambdas,mses3)
plt.title('MSE for Test Data')
plt.legend(['Using scipy.minimize','Direct minimization'])
plt.show()



# PROBLEM - 5
pmax = 7
lambda_opt = lambdas[np.where(mses3==min(mses3))[0][0]]
mses5_train = np.zeros((pmax,2))
mses5 = np.zeros((pmax,2))
for p in range(pmax):
    Xd = mapNonLinear(X[:,2],p)
    Xdtest = mapNonLinear(Xtest[:,2],p)
    w_d1 = learnRidgeRegression(Xd,y,0)
    mses5_train[p,0] = testOLERegression(w_d1,Xd,y)
    mses5[p,0] = testOLERegression(w_d1,Xdtest,ytest)
    w_d2 = learnRidgeRegression(Xd,y,lambda_opt)
    mses5_train[p,1] = testOLERegression(w_d2,Xd,y)
    mses5[p,1] = testOLERegression(w_d2,Xdtest,ytest)

fig = plt.figure(figsize=[12,6])
plt.subplot(1, 2, 1)
plt.plot(range(pmax),mses5_train)
plt.title('MSE for Train Data')
plt.legend(('No Regularization','Regularization'))
plt.subplot(1, 2, 2)
plt.plot(range(pmax),mses5)
plt.title('MSE for Test Data')
plt.legend(('No Regularization','Regularization'))
plt.show()