import java.util.Random;
import java.io.FileWriter; 
import java.io.IOException; 

class dataset1{
	private static int size=6000;
	private static float x1[]=new float[size];
	private static float x2[]=new float[size];
	private static int categories[]=new int[size];
	
	static Random r = new Random();

	public static void createRandomPoints(){
		for(int i=0;i<size;i++){
			x1[i]=r.nextFloat() * (2)-1;
			x2[i]=r.nextFloat() * (2)-1;
			categories[i]=categorize(x1[i],x2[i]);
		}	
	}

	public static int categorize(float x1,float x2){
		float propability=r.nextFloat();
		if ((Math.pow(x1,2)+Math.pow(x2,2))<0.25 && propability<=0.9){
			return 1;
		}
		else if((x1>=-1) && (x1<=-0.4)&& (x2>=-1)&& (x2<=-0.4) && propability<=0.9){
			return 2;
		}
		else if((x1>=0.4) && (x1<=1) && (x2>=0.4) && (x2<=1) && propability<=0.9){
			return 2;
		}
		else if((x1>=-1) && (x1<=-0.4) &&(x2>=0.4) && (x2<=1) && propability<=0.9){
			return 3;
		}
		else if((x1>=0.4) && (x1<=1) &&(x2>=-1) && (x2<=-0.4) && propability<=0.9){
			return 3;
		}
		return 4;
	}
	public static void writeToFile(){
		try {
      		FileWriter trainingWriter = new FileWriter("training_set.csv");
      		FileWriter testWriter = new FileWriter("test_set.csv");
			for(int i=0;i<size;i++){
				float choice=r.nextFloat();
				if(choice<=0.5){
					trainingWriter.write(x1[i]+","+x2[i]+","+categories[i]+"\n");
				}
				else{
					testWriter.write(x1[i]+","+x2[i]+","+categories[i]+"\n");	
				}
			}
			trainingWriter.close();
			testWriter.close();
    	} 
    	catch (IOException e) {
      		System.out.println("An error occurred.");
      		e.printStackTrace();
    	}		
	}
	public static void main(String[] args) {
		createRandomPoints();
		writeToFile();
	}
}



/*
y1,1=forward-pass 
class neuron{
	float weights[2];
	float input[2];
	float output;

	calculateOutput();

}

neuron layer1[2];
neuron layer2[2];
layer2[0].input[0]=layer1[0].output;
calculate output from layer 1 neurons

(*/