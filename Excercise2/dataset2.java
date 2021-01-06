import java.util.Random;
import java.io.FileWriter; 
import java.io.IOException; 

class dataset2{
	private static int size=900;
	private static float x1[]=new float[size];
	private static float x2[]=new float[size];
	private static int category[] = new int[size];
	// private static int group []=new int[size];
	private static float squareCenters[][] = new float[5][2];

	static Random r = new Random(); 



	public static float euclideanDistance(float x1, float y1, float x2, float y2){
		return (float)Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));
	}

	public static int findNearestCenter(float x1, float x2){
		float minDist = Float.MAX_VALUE;
		int category = -1;
		for(int i = 0; i < 5; i++){
			float currentDist = euclideanDistance(x1,x2,squareCenters[i][0],squareCenters[i][1]);
			if(currentDist<minDist){
				minDist = currentDist;
				category = i;
			}
		}
		return category;
	}

	// place random points within a box bounds
	public static void createRandomPointsSquare(float lowerCoordinates[] ,float upperCoordinates [] ,int arraypos, int label){	
		for(int i=arraypos;i<arraypos+150;i++){
			x1[i]=lowerCoordinates[0] + r.nextFloat() * (lowerCoordinates[1] -lowerCoordinates[0]);
			x2[i]=upperCoordinates[0] + r.nextFloat() * (upperCoordinates[1] -upperCoordinates[0]);
			if(label != 5){
				category[i] = label;
			// group[i]=group;
			}else{
				category[i] = findNearestCenter(x1[i],x2[i]);
			}
		}	
	}
	public static void createAllPoints(){
		float lowerCoordinates[][]={{0.75f,1.25f},{0f,0.5f},{0f,0.5f},{1.5f,2f},{1.5f,2f},{0f,2f}};
		float upperCoordinates[][]={{0.75f,1.25f},{0f,0.5f},{1.5f,2f},{0f,0.5f},{1.5f,2f},{0f,2f}};
		
		for(int i = 0; i < 5; i++){
			squareCenters[i][0] =  (lowerCoordinates[i][0] + lowerCoordinates[i][1])/2;
			squareCenters[i][1] =  (upperCoordinates[i][0] + upperCoordinates[i][1])/2;
		}


		for (int i=0;i<6 ;i++ ){
			createRandomPointsSquare(lowerCoordinates[i],upperCoordinates[i],150*i,i);			
		}
	}

	public static void writeToFile(){
		try {
      		FileWriter myWriter = new FileWriter("results2.csv");
			for(int i=0;i<size;i++){
				myWriter.write(x1[i]+","+x2[i]+","+category[i]+"\n");
			}
			myWriter.close();
    		
    	} 
    	catch (IOException e) {
      		System.out.println("An error occurred.");
      		e.printStackTrace();
    	}		
	}
	public static void main(String[] args) {
		createAllPoints();
		writeToFile();
	}
}