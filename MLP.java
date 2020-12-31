import java.io.*;  // Import the io library
import java.util.Scanner; // Import the Scanner class to read text files
class MLP{

	private static final int size=3000; 
	//number of inputs
	private static final int d=2;
	//number of categories
	private static final int k=4;
	private static final int neuronsH1=2;
	private static final int neuronsH2=2;

	private static Point trainingSet[]=new Point[size];
	private static Point validationSet[]=new Point[size];
	

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
	public static void main(String[] args) {
		readFile("training_set.csv",true);
	}
}