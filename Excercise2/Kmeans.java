import java.io.*;  // Import the io library
import java.util.*;
import java.lang.Math;

public class Kmeans{

	private static int numPoints = 900;
	private static int numCentroids = 5;

	private static Point points[] = new Point[numPoints];
	private static Centroid centroids[];

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
			Point point=new Point(Float.parseFloat(currentPoint[0]),Float.parseFloat(currentPoint[1]));
			
			points[pointIndex++]=point;
			
		}
		reader.close();

	}

	public static float euclideanDistance(float x1, float y1, float x2, float y2){
		return (float)Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));
	}


	public static float kmeans(int k){
		initiateCentroids(k);
		float minDist;
		float currentDist;
		int centroidID = -1;
		// for each point calculate minum distance of centroid
		float totalError = 0;
		float previousError = 0;
		float errorDifference = 1;

		while(errorDifference>=0.0001)
		{
			// find closest centroid
			for(int i = 0; i < numPoints; i++)
			{
				minDist = Float.MAX_VALUE;
				for(int j = 0; j < k; j++)
				{
					currentDist = euclideanDistance(points[i].x,points[i].y, centroids[j].x,centroids[j].y);
					if(currentDist< minDist)
					{
						minDist = currentDist;
						centroidID = j;
					}

				}
				// assign closest centroid
				points[i].cluster = centroidID;
				points[i].errorDist = minDist;
				centroids[centroidID].totalX += points[i].x;
				centroids[centroidID].totalY += points[i].y;
				centroids[centroidID].count++;
			}

			//update centroids position
			for(int i = 0; i < k; i++)
			{
				centroids[i].x = centroids[i].totalX/centroids[i].count;
				centroids[i].y = centroids[i].totalY/centroids[i].count;
			}

			//calculate total error
			totalError = 0;
			for(int i = 0; i < numPoints; i++)
			{
				totalError += points[i].errorDist;
			}

			errorDifference = Math.abs(previousError - totalError);			
			// System.out.println(totalError);
			previousError = totalError;
		}
		
		return totalError;
	}

	// initialize centroids
	public static void initiateCentroids(int k){
		Centroid center;
		float x;
		float y;
		int randomIndex;
		Point p;
		centroids = new Centroid[k];

		for(int i = 0; i < k; i++){
			randomIndex = r.nextInt(numPoints-1);
			//get random point
			p = points[randomIndex];
			center = new Centroid(p.x, p.y, i);
			centroids[i] = center;
		}


	}


	public static void writeErrorsForClusters(float minErrors[]){
		try {
      		FileWriter writer = new FileWriter("kmeansData/plotKmeansErrors.csv");

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

	public static void writeCsvData(int minPoints[], Centroid minCentroids[], int k){
		try {
      		FileWriter writer = new FileWriter("kmeansData/plotKmeans"+k+".csv");
      		writer.write(k+"\n");

			for(int i=0;i<k;i++){
				// float choice=r.nextFloat();
			
				writer.write(minCentroids[i].x+","+minCentroids[i].y+","+minCentroids[i].id+"\n");
			}

			for(int i = 0; i < numPoints; i++)
			{
				writer.write(minPoints[i]+"\n");
			}
			writer.close();
    	} 
    	catch (IOException e) {
      		System.out.println("An error occurred.");
      		e.printStackTrace();
    	}		
	}

	/*CSV
	3 // num centroids
	x1 y1 id1
	x2 y2 id2
	x3 y3 id3
	0
	2
	3
	1
	0
	....
	*/
	public static Centroid[] deepCopyCentroids(int k){
		Centroid minCentroids[] = new Centroid[k];
		for(int i = 0; i < k ; i++)
		{
			minCentroids[i] = centroids[i];
		}
		return minCentroids;

	}
	public static int[] deepCopyCategorys(){
		int minPoints[] = new int[numPoints];
		for(int i = 0; i < numPoints ; i++)
		{
			minPoints[i] = points[i].cluster;
		}
		return minPoints;

	}


	public static void runKMeansStats(){
		int clusters[] = {2,3,4,5,6,7,10};
		float minError ;
		float currentError;
		Centroid minCentroid[] = null;
		int minPoints[] = null;
		float minErrors[]=new float[7];
		
		// for each number of cluster
		for(int i = 0; i < 7; i++)
		{	
			int k = clusters[i];
			minError = Float.MAX_VALUE;
			// iterate 5 times the experiment and keep the best outcome
			for(int j = 0; j < 5; j++)
			{
				
				currentError = kmeans(k);
				if(currentError<minError)
				{
					minError = currentError;
					minErrors[i]=minError;
					minCentroid = deepCopyCentroids(k);
					minPoints = deepCopyCategorys();
				}
			}
			writeCsvData(minPoints,minCentroid, k);
			System.out.println("k: "+k+", totalError: "+minError);
    	} 
		
    	writeErrorsForClusters(minErrors);

	}



	static class Point{
		float x;
		float y;
		int cluster;
		float errorDist;
		Point(float x, float y){
			this.x = x;
			this.y = y;
		}
	}

	//proponhths
	static class Centroid{
		float x;
		float y;
		float totalX;
		float totalY;
		int id;
		int count;

		Centroid(float x, float y, int id){
			this.x = x;
			this.y = y;
			this.id = id;
			totalX = 0;
			totalY = 0;
			count = 0;
		}
	}


	public static void main(String[] args) {
		readFile("results2.csv");
		//initiateCentroids();
		float clusters[] = {2,3,4,5,6,7,10};
		runKMeansStats();
		
	}
}