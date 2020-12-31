import java.io.*;  // Import the io library
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.Random;
import java.lang.Math;

class MLP{

	private static final int size=3000; 
	//number of inputs
	private static final int d=2;
	//number of categories
	private static final int k=4;
	private static final int neuronsH1=2;
	private static final int neuronsH2=2;
	private static final int layerSize[]={d,neuronsH1,neuronsH2,k};
	private static Point trainingSet[]=new Point[size];
	private static Point validationSet[]=new Point[size];
	private static Neuron network[][];
	//{d,H1,H2,k}
	private static int numberOfLayers=4;

	static Random r = new Random();

	public static void readFile(String filename,boolean isTraining){
		Scanner reader = null;
		try{
			reader = new Scanner(new FileInputStream(filename));
		} 
		catch(FileNotFoundException e){
			System.out.println("File not be found");
			System.exit(0);
		}

		int pointIndex=0;
		while(reader.hasNextLine()){
			String line = reader.nextLine();
			String points[]=line.split(",");
			Point point=new Point(Float.parseFloat(points[0]),Float.parseFloat(points[1]),Integer.parseInt(points[2]));
			if(isTraining){
				trainingSet[pointIndex++]=point;
			}
			else{
				validationSet[pointIndex++]=point;
			}
		}
		System.out.println(pointIndex);
		reader.close();

	}
	//to do sinartisi energopoihshs
	public static void initiateNetwork(){
		network=new Neuron[numberOfLayers][];
		for(int i=0;i<numberOfLayers;i++){
			network[i]=new Neuron[layerSize[i]];
			for(int j=0;j<layerSize[i];j++){
				network[i][j]=new Neuron(layerSize[i]);
			}
		}
		//we dont need weights for inputs
		network[0][0].weights = null;
		network[0][1].weights = null;

	}

	//prints output for each neuron
	public static void printNeuronOutputs(){
		for(int i=0;i<numberOfLayers;i++){
			for(int j=0;j<layerSize[i];j++){
				System.out.print(network[i][j].output+" ");
			}
			System.out.println();
		}		
	}
	public static void printNeuronWeights(){
		for(int i=1;i<numberOfLayers;i++){
			for(int j=0;j<layerSize[i];j++){
				System.out.print("(layer:"+i+" neuron:"+j+") has input weights:");
				for(int k=0;k<layerSize[i];k++){
					System.out.print(network[i][j].weights[k]+ " ");
				}
				System.out.println();
			}
		}		
	}

	public static float[] forwardPass(float input[]){
		network[0][0].output = input[0];
		network[0][1].output = input[1];
		float networkOutput[] = new float[k];
		float product;
		for (int i = 1 ; i < numberOfLayers;i++){
			for (int j = 0 ;j<layerSize[i];j++){
			    product = dotProduct(network[i-1], network[i][j].weights, layerSize[i-1]) + network[i][j].bias;
			    network[i][j].output = activateFunc(product, i);
				if (i == k-1){
					networkOutput[j] = network[i][j].output;
				}
			}
		}
		return networkOutput;
	}

	public static float activateFunc(float input, int type){
		// 1 is for tanh
		if(type==1){
			return (float)Math.tanh(input);
		}
		// 2 or 3 for logistic 
		else if (type==2 || type==3){
			return (float)(1/(1+Math.exp(-input)));
		}
		System.out.println("activate function not found");
		return 0;
	}

	public static float dotProduct(Neuron layer[],float weights[],int layerSize){
	 	float productSum = 0;
    	for (int i = 0 ; i < layerSize;i++){
    		productSum += layer[i].output*weights[i];
    	}
		return productSum;
	}


	static class Point{
		float x1;
		float x2;
		int category;

		Point(float x1,float x2,int category){
			this.x1=x1;
			this.x2=x2;
			this.category=category;
		}

	}
	static class Neuron{
		float weights[];
		float output;
		float bias;

		Neuron(int weightsLength){
			weights=new float[weightsLength];
			for(int i=0;i<weightsLength;i++){
				weights[i]=r.nextFloat() * (2)-1;
			}
			bias=r.nextFloat() * (2)-1;
		}

	}

	//trainNetwork()
	//forward
	//backpro


	public static void main(String[] args) {
		readFile("training_set.csv",true);
		initiateNetwork();
		// printNeuronOutputs();
		// printNeuronWeights();

		float input[] = {trainingSet[0].x1,trainingSet[0].x2};
		float[] arr = forwardPass(input);
		System.out.println("forwad pass done");
		for (int i=0;i<4;i++){
			System.out.println(arr[i]);
		}
	}
}


/*
y1,1=forward-pass 
class neuron{
	float weights[2];
	//float input[2];
	float output;

	calculateOutput();

}

neuron layer1[2];
neuron layer2[2];
layer2[0].input[0]=layer1[0].output;
calculate output from layer 1 neurons

Neuron net[]][];
net[1]

float[] forwardPass(float input[]){
	net[0][0].output = input[0]
	net[0][1].output = input[1]
	float outputVec[] = new float[4]
	for i = 1 ; i < 4
		for j = 0 to layerSize
		    prod = dotProduct(net[i-1], net[i][j].weights) + net[i][j].bias;
		    net[i][j].output = actFunc(prod, index)
			if i == 3
				outputeVec[j] = net[i][j].output

}

void activateFunc(float inp, int type)


dotProd(neuron layer[], float weighjts[])
    prod = 0
    for i = 0 ; i < layerSize
    	prod += layer[i].output*weights[i]
	return prod

(*/