import java.io.*;  // Import the io library
import java.util.*;
import java.lang.Math;

public class LVQ{
	private static int numPoints=900;
	private static Point points[]=new Point[numPoints];
	private static float weights[][];
	private static float learningRate=0.1f;
	static Random r = new Random();

	public static void readFile(String filename){
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
			String currentPoint[] = line.split(",");
			Point point=new Point(Float.parseFloat(currentPoint[0]),Float.parseFloat(currentPoint[1]),Integer.parseInt(currentPoint[2]));
			
			points[pointIndex++]=point;
			
		}
		reader.close();

	}

	public static float euclideanDistance(float x1, float y1, float x2, float y2){
		// System.out.println(x1);
		// System.out.println(y1);
		// System.out.println(x2);
		// System.out.println(y2);
		return (float)Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));
	}

	// initialize centroids
	public static void initiateWeights(int k){
		float x;
		float y;
		int randomIndex;
		Point p=null;
		weights=new float[k][2];

		for(int i = 0; i < k; i++){
			randomIndex = r.nextInt(numPoints-1);
			//get random point
			p = points[randomIndex];
			weights[i][0]=p.x;
			weights[i][1]=p.y;
		}
	}

	public static void printWeights(int k){

		for(int i = 0; i < k; i++){
			System.out.println(weights[i][0]+ " "+weights[i][1]);
		}
		
	}

	public static float lvq(int k)
	{
		initiateWeights(k);
		float minDist;
		float currentDist;
		int winner=0;
		float errorDifference = 1;
		float currentError;
		float previousError=Float.MAX_VALUE;
		learningRate = 0.1f;
		float totalError = 0f;
		
		while(errorDifference>=0.001)
		{
			totalError=0f;
			for(int i=0;i<numPoints;i++)
			{
				minDist=Float.MAX_VALUE;
				// winner = -1;
				for(int j=0;j<k;j++)
				{
					currentDist=euclideanDistance(points[i].x,points[i].y,weights[j][0],weights[j][1]);
					// if(currentDist==minDist){
					// 	System.out.println(points[i].x+","+points[i].y+","+weights[j][0]+","+weights[j][1]);
					// }
					if(currentDist<minDist)
					{
						minDist=currentDist;
						winner=j;
						points[i].winner=winner;
					}
				}

				weights[winner][0] = weights[winner][0] + learningRate*(points[i].x-weights[winner][0]);
				weights[winner][1] = weights[winner][1] + learningRate*(points[i].y-weights[winner][1]);						

				totalError+= euclideanDistance(points[i].x,points[i].y,weights[winner][0],weights[winner][1]);;
			}

		
			learningRate *= 0.95;
			errorDifference = Math.abs(previousError - totalError);			
			// System.out.println(totalError);
			previousError = totalError;
		}	
		return totalError;
	}

	public static float [][] deepCopyWeights(int k){
		float centroidsPosition [][]=new float[k][2];
		
		for(int i=0;i<k;i++){
			centroidsPosition[i][0]=weights[i][0];
			centroidsPosition[i][1]=weights[i][1];
		}
		return centroidsPosition;
	}

	public static int[] deepCopyWinners(){
		int  winners[] = new int[numPoints];
		for(int i=0;i<numPoints;i++){
			winners[i]=points[i].winner;
		}
		return winners;
	}

	public static void writeCsvData(int minPointsWinners[], float minCentroids[][], int k){
		try {
      		FileWriter writer = new FileWriter("LVQData/plotLVQ"+k+".csv");
      		writer.write(k+"\n");

			for(int i=0;i<k;i++){
			
				writer.write(minCentroids[i][0]+","+minCentroids[i][1]+","+i+"\n");
			}

			for(int i = 0; i < numPoints; i++)
			{
				writer.write(minPointsWinners[i]+"\n");
			}
			writer.close();
    	} 
    	catch (IOException e) {
      		System.out.println("An error occurred.");
      		e.printStackTrace();
    	}		
	}

	public static void writeErrorsForClusters(float minErrors[]){
		try {
      		FileWriter writer = new FileWriter("LVQData/plotLVQErrors.csv");

			for(int i=0;i<7;i++){
				writer.write(minErrors[i]+" ");
			}
			writer.close();
    	} 
    	catch (IOException e) {
      		System.out.println("An error occurred.");
      		e.printStackTrace();
    	}	
	}



	public static void runLVQ(){
		int nClusters = 7;
		int clusters[] = {2,3,4,5,6,7,10};
		
		// arrays for writing data
		int minPointsWinners[] = null;
		float minErrors[]=new float[nClusters];
		float centroidsPosition[][] = null;


		float minError ;
		float currentError;

		// for each number of cluster
		for(int i = 0; i < nClusters; i++)
		{	
			int k = clusters[i];
			minError = Float.MAX_VALUE;
			// iterate 5 times the experiment and keep the best outcome
			for(int j = 0; j < 5; j++)
			{
				
				currentError = lvq(k);
				//System.out.println(currentError);
				if(currentError<minError)
				{
					minError = currentError;
					minErrors[i]=minError;
					centroidsPosition = deepCopyWeights(k);
					minPointsWinners = deepCopyWinners();
				}
			}
			System.out.println("k: "+k+", totalError: "+minError);
			writeCsvData(minPointsWinners,centroidsPosition, k);
    	} 
		
    	writeErrorsForClusters(minErrors);
	}


	static class Point{
		float x;
		float y;
		int category;
		int winner;

		Point(float x,float y,int category){
			this.x=x;
			this.y=y;
			this.category=category;
		}

	}

	public static void main(String[] args) {
		readFile("results2.csv");
		runLVQ();	
	}
}