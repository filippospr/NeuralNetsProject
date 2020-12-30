import java.util.Random;
import java.io.FileWriter; 
import java.io.IOException; 

class dataset2{
	private static int size=900;
	private static float x1[]=new float[size];
	private static float x2[]=new float[size];
	// private static int group []=new int[size];

	static Random r = new Random(); 


	public static void createRandomPointsSquare(float lowerCordinates[] ,float upperCoordinates [] ,int arraypos){	
		for(int i=arraypos;i<arraypos+150;i++){
			x1[i]=lowerCordinates[0] + r.nextFloat() * (lowerCordinates[1] -lowerCordinates[0]);
			x2[i]=upperCoordinates[0] + r.nextFloat() * (upperCoordinates[1] -upperCoordinates[0]);
			// group[i]=group;
		}	
	}
	public static void createAllPoints(){
		float lowerCordinates[][]={{0.75f,1.25f},{0f,0.5f},{0f,0.5f},{1.5f,2f},{1.5f,2f},{0f,2f}};
		float upperCoordinates[][]={{0.75f,1.25f},{0f,0.5f},{1.5f,2f},{0f,0.5f},{1.5f,2f},{0f,2f}};
		for (int i=0;i<6 ;i++ ){
			createRandomPointsSquare(lowerCordinates[i],upperCoordinates[i],150*i);			
		}
	}

	public static void writeToFile(){
		try {
      		FileWriter myWriter = new FileWriter("results2.csv");
			for(int i=0;i<size;i++){
				myWriter.write(x1[i]+","+x2[i]+"\n");
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