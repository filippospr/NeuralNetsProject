# NeuralNetsProject
Project of Neural Nets

In this project there were implemented three machine learning models:

## Supervised Learning ##
- A neural network from scratch (Multi-Layer Perceptron).

## Unspervised Learning ##
- The Kmeans Algorithm
- The LVQ algorithm


The training datasets are sets of points in the 2D plain in the [-1,1]x[-1,1]
In the mutlilayer perceptron we train the neural network for a set of given points and then with the rest we calculate the accuracy of the prediction that it made. We used the cross validation technique.

The neural network used the forward propagation algorithm and each neuron used an activation function. Either the logistic function or the tanh function. Then we exploit the backpropagation algorithm, by differentiation the error difference and propagating the error backwards.

In the unsupervised learning the algorithms themselves detect the groups for various Ks(clusters).




