import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.FileNotFoundException;

public class test {

	private static double[]   frequenciesham;
	private static double[]   frequenciesspam;
	private static double[][] m=new double[][] {{0,0},{0,0}};
	//private static double[]   train;
	
	
 
	public static void main(String[] args) throws FileNotFoundException {
		//double[][] m=new double[][] {{0,0},{0,0}};
		double train[] = new double[5];
		double testing[]=new double[5];                //for confusion matrixes
		double precisionham[]=new double[5];
		double precisionspam[]=new double[5];
		double recallham[]=new double[5];
		double recallspam[]=new double[5];
		double f1ham[]=new double[5];
		double f1spam[]=new double[5];
		double [][] matrix1=new double[2][2];           //          Predicted
		double [][] matrix2=new double[2][2];           //          ham     spam
		double [][] matrix3=new double[2][2];          // ham:     m[0][0]  m[0][1]                            } Actual
		double [][] matrix4=new double[2][2];          //spam:     m[1][0]  m[1][1]                            }
		double [][] matrix5=new double[2][2];
		
	   String trainpath="C:\\Users\\John\\Desktop\\enron1\\enron1\\test\\all";
	   
		//training curve
	for(int k=1; k<6; k++) {
		
	   String hampath="C:\\Users\\John\\Desktop\\enron1\\enron1\\"+"mails"+k+"\\ham";
	   String spampath="C:\\Users\\John\\Desktop\\enron1\\enron1\\"+ "mails"+k+"\\spam";
	   String allpath="C:\\Users\\John\\Desktop\\enron1\\enron1\\"+"mails"+k+"\\all";
	   
	   //String testpath="C:\\Users\\John\\Desktop\\enron1\\enron1\\test\\all";
	   //creating ham hashmap
	   ArrayList<String> seussham = seussre(hampath);			   
	   Map<String, Integer> seussCountH = new HashMap<String,Integer>();
	    for(String t: seussham) {
	       Integer i = seussCountH.get(t);
	       if (i ==  null) {
	           i = 0;
	       }
	       seussCountH.put(t, i + 1);
	    }
	    //creating spam hashmap
	    ArrayList<String> seussspam = seussre(spampath);
	    Map<String, Integer> seussCountS = new HashMap<String,Integer>();
	    for(String t: seussspam) {
	       Integer i = seussCountS.get(t);  
	       if (i ==  null) {
	           i = 0;
	       }
	       seussCountS.put(t, i + 1);
	    }	        
	    //till here perfect with spam and ham numbers!
	    
	    
	
	    float hamsize=seussham.size();
	    float spamsize=seussspam.size();
		 
	  
	    
	    File dir = new File(allpath);
	    double files=0;
	    int corrects=0;
	    for (File file : dir.listFiles()) {
	    	String askedpath=file.getAbsolutePath();
	    	StringTokenizer str=new StringTokenizer(askedpath,".");
	    
	    	for(int j=0; j<3; j++) {
	    	str.nextToken();
	    	}
	      
	    
	  
	   ArrayList<String> asked=seussone(askedpath);
	 
        frequenciesham = new double[asked.size()];
        frequenciesspam = new double[asked.size()];
        int ok=0;
        String s1;
        cleararr(asked);
       for(int i=0; i<asked.size(); i++)
        {
        	s1=asked.get(i);
        	
        	if(seussCountH.containsKey(s1)) {
            frequenciesham[ok]=seussCountH.get(s1);
            }else {
            	 frequenciesham[ok]=0.0;
            }
        	if(seussCountS.containsKey(s1)) {
            frequenciesspam[ok]=seussCountS.get(s1);
        	}else {
        		 frequenciesspam[ok]=0.0;
        	}
            ok++;
            
        }
	    
        
    double sumham=0;
	double sumspam=0;
    double test=1;
   // m[0][0]=0;
    //m[0][1]=0;
    //m[1][0]=0;
    //m[1][1]=0;
	for(int i=0; i<ok; i++) {
		
		//calculating possibilities
	
		if(frequenciesham[i]!=0) {
	    sumham=sumham+Math.log1p((frequenciesham[i]/hamsize));
		}
		if(frequenciesham[i]!=0) {
		test=test*(frequenciesham[i]/hamsize);
		  //System.out.println(frequenciesham[1]/hamsize);
		}
		
		if(frequenciesspam[i]!=0) {
	    sumspam=sumspam+Math.log1p(frequenciesspam[i]/spamsize);
		}
	}
	
	String s2=str.nextToken();
	if((sumham>sumspam)&&(s2.equals("ham")) ){
		//System.out.println("Its ham");
		corrects++;
	}
	
	if((sumham<sumspam)&&(s2.equals("spam")) ){
		//System.out.println("Its spam");
		corrects++;
	}
	
	//for confusion matrixes
	if (s2.equals("ham")){
		if(sumham>sumspam) {
			m[0][0]=m[0][0]+1;
		}else {
			m[0][1]=m[0][1]+1;
		}
				
	}else {
		if(sumham>sumspam) {
			m[1][0]=m[1][0]+1;
		}else {
			m[1][1]=m[1][1]+1;
		}
	}

  	files++;
	    }//end  file loop
	    train[k-1]=1-(corrects/files);	    
	    //training end
	    //confusion matrixes
	    if(k==1) {
	    	 matrix1[0][0]=m[0][0];
	    	 matrix1[0][1]=m[0][1];
	    	 matrix1[1][0]=m[1][0];
	    	 matrix1[1][1]=m[1][1];
	    }
	    if(k==2) {
	    	 matrix2[0][0]=m[0][0];
	    	 matrix2[0][1]=m[0][1];
	    	 matrix2[1][0]=m[1][0];
	    	 matrix2[1][1]=m[1][1];
	    }
	    if(k==3) {
	    	 matrix3[0][0]=m[0][0];
	    	 matrix3[0][1]=m[0][1];
	    	 matrix3[1][0]=m[1][0];
	    	 matrix3[1][1]=m[1][1];
	    }
	    if(k==4) {
	    	 matrix4[0][0]=m[0][0];
	    	 matrix4[0][1]=m[0][1];
	    	 matrix4[1][0]=m[1][0];
	    	 matrix4[1][1]=m[1][1];
	    }
	    if(k==5) {
	    	 matrix5[0][0]=m[0][0];
	    	 matrix5[0][1]=m[0][1];
	    	 matrix5[1][0]=m[1][0];
	    	 matrix5[1][1]=m[1][1];
	    }
	    //System.out.println("corrects:"+corrects +"out of files:"+files);
	    
	   
	    
	  //testing  
	    File dirr = new File(trainpath);
	    double filess=0;
	    int correctss=0;
	    for (File file : dirr.listFiles()) {
	    	String askedpath=file.getAbsolutePath();
	    	StringTokenizer str=new StringTokenizer(askedpath,".");
	    
	    	for(int j=0; j<3; j++) {
	    	str.nextToken();
	    	}
	    	
	    	 ArrayList<String> asked=seussone(askedpath);
	    	 
	         frequenciesham = new double[asked.size()];
	         frequenciesspam = new double[asked.size()];
	         int ok=0;
	         String s1;
	         cleararr(asked);
	        for(int i=0; i<asked.size(); i++)
	         {
	         	s1=asked.get(i);
	         	
	         	if(seussCountH.containsKey(s1)) {
	             frequenciesham[ok]=seussCountH.get(s1);
	             }else {
	             	 frequenciesham[ok]=0.0;
	             }
	         	if(seussCountS.containsKey(s1)) {
	             frequenciesspam[ok]=seussCountS.get(s1);
	         	}else {
	         		 frequenciesspam[ok]=0.0;
	         	}
	             ok++;
	             
	         }
	        
	        double sumham=0;
	    	double sumspam=0;
	        double test=1;
	    	for(int i=0; i<ok; i++) {
	    		
	    		//calculating possibilities
	    	
	    		if(frequenciesham[i]!=0) {
	    	    sumham=sumham+Math.log1p((frequenciesham[i]/hamsize));
	    		}
	    		if(frequenciesham[i]!=0) {
	    		test=test*(frequenciesham[i]/hamsize);
	    		  //System.out.println(frequenciesham[1]/hamsize);
	    		}
	    		
	    		if(frequenciesspam[i]!=0) {
	    	    sumspam=sumspam+Math.log1p(frequenciesspam[i]/spamsize);
	    		}
	    	}
	    	
	    	String s2=str.nextToken();
	    	if((sumham>sumspam)&&(s2.equals("ham")) ){
	    		//System.out.println("Its ham");
	    		correctss++;
	    	}
	    	
	    	if((sumham<sumspam)&&(s2.equals("spam")) ){
	    		//System.out.println("Its spam");
	    		correctss++;
	    	}
	    	filess++;
	   	    	
	    }	//end file loop
	    
	    testing[k-1]=1-(correctss/filess);
	    m[0][0]=0;
	    m[0][1]=0;
	    m[1][0]=0;
	    m[1][1]=0;
	}//end for loop
	
	for(int i=0; i<train.length; i++) {
		System.out.println(train[i]);
	}
	System.out.println("============== Training error");
	//end of training curve
	for(int i=0; i<testing.length; i++) {
		System.out.println(testing[i]);
	}
	System.out.println("============== Testing error");
	System.out.println(matrix1[0][0]+" "+matrix1[0][1]+" "+matrix1[1][0]+ " "+matrix1[1][1]);
	System.out.println(matrix2[0][0]+" "+matrix2[0][1]+" "+matrix2[1][0]+ " "+matrix2[1][1]);
	System.out.println(matrix3[0][0]+" "+matrix3[0][1]+" "+matrix3[1][0]+ " "+matrix3[1][1]);
	System.out.println(matrix4[0][0]+" "+matrix4[0][1]+" "+matrix4[1][0]+ " "+matrix4[1][1]);
	System.out.println(matrix5[0][0]+" "+matrix5[0][1]+" "+matrix5[1][0]+ " "+matrix5[1][1]);
	System.out.println("============confusion matrixes");
	//testing end
	
	//precision recall f1 
   ///////////////////
	//created confusion matrixes
	//creating 
	
		precisionham[0]=(matrix1[0][0]/(matrix1[0][0]+matrix1[1][0]));
		precisionham[1]=(matrix2[0][0]/(matrix2[0][0]+matrix2[1][0]));
		precisionham[2]=(matrix3[0][0]/(matrix3[0][0]+matrix3[1][0]));
		precisionham[3]=(matrix4[0][0]/(matrix4[0][0]+matrix4[1][0]));
		precisionham[4]=(matrix5[0][0]/(matrix5[0][0]+matrix5[1][0]));
		precisionspam[0]=(matrix1[1][1]/(matrix1[0][1]+matrix1[1][1]));
		precisionspam[1]=(matrix2[1][1]/(matrix2[0][1]+matrix2[1][1]));
		precisionspam[2]=(matrix3[1][1]/(matrix3[0][1]+matrix3[1][1]));
		precisionspam[3]=(matrix4[1][1]/(matrix4[0][1]+matrix4[1][1]));
		precisionspam[4]=(matrix5[1][1]/(matrix5[0][1]+matrix5[1][1]));
		recallham[0]=(matrix1[0][0]/(matrix1[0][0]+matrix1[0][1]));
		recallham[1]=(matrix2[0][0]/(matrix2[0][0]+matrix2[0][1]));
		recallham[2]=(matrix3[0][0]/(matrix3[0][0]+matrix3[0][1]));
		recallham[3]=(matrix4[0][0]/(matrix4[0][0]+matrix4[0][1]));
		recallham[4]=(matrix5[0][0]/(matrix5[0][0]+matrix5[0][1]));
		recallspam[0]=(matrix1[1][1]/(matrix1[1][0]+matrix1[1][1]));
		recallspam[1]=(matrix2[1][1]/(matrix2[1][0]+matrix2[1][1]));
		recallspam[2]=(matrix3[1][1]/(matrix3[1][0]+matrix3[1][1]));
		recallspam[3]=(matrix4[1][1]/(matrix4[1][0]+matrix4[1][1]));
		recallspam[4]=(matrix5[1][1]/(matrix5[1][0]+matrix5[1][1]));
	 
		for(int i=0; i<5; i++) {
			System.out.println(precisionham[i]);
		}
		System.out.println("=======precisionham");
		for(int i=0; i<5; i++) {
			System.out.println(precisionspam[i]);
		}
		System.out.println("=======precisionspam");
		for(int i=0; i<5; i++) {
			System.out.println(recallham[i]);
		}
		System.out.println("=======recallham");
		for(int i=0; i<5; i++) {
			System.out.println(recallspam[i]);
		}
		System.out.println("=======recallspam");
		for(int i=0; i<5; i++) {
			f1ham[i]=2*((precisionham[i]*recallham[i])/(precisionham[i]+recallham[i]));
			f1spam[i]=2*((precisionspam[i]*recallspam[i])/(precisionspam[i]+recallspam[i]));
		}
		
		for(int i=0; i<5; i++) {
			System.out.println(f1ham[i]);
		}
		System.out.println("=======f1ham");
		for(int i=0; i<5; i++) {
			System.out.println(f1spam[i]);
		}
		System.out.println("=======f1spam");
	
	
	
	
	
	
	
}
//end of main
	public static ArrayList<String> seussone(String path) throws FileNotFoundException{
		
		File file = new File(path);
		Scanner sc = new Scanner(file); 
		ArrayList<String> seuss = new ArrayList<String>();
		sc.delimiter(); 
	
		 while(sc.hasNext()) {
			seuss.add(sc.next());
		    }
		 sc.close();
		 return seuss;
	}

    public static  ArrayList<String> seussre(String path) throws FileNotFoundException {
    	File dir = new File(path);
		 ArrayList<String> seuss = new ArrayList<String>();
		for (File file : dir.listFiles()) {
		    Scanner sc = new Scanner(file);
		   
			sc.delimiter();
			 while(sc.hasNext()) {
					seuss.add(sc.next());
				    }
		  sc.close();
       }
         return seuss;
     }
    
    
    public  static void cleararr(ArrayList<String> arr) {
    
    	 String[] avoid= {".","/","!","@","#","$","%","^","&","*","(",")","_","-","+","=","{","[","}","]","|",":",";","<",">","?","1","2","3","4","5","6","7","8","9","0",",", " '"};
    	 ArrayList<String> list = new ArrayList<>();
    	 
    	for(int i=0; i<arr.size(); i++) {
    		for(int j=0; j<avoid.length; j++) {
    		if(arr.get(i).contains(avoid[j])) {
    			list.add(arr.get(i));
    			continue;
    		}
    	}
    		
    	}
    	for(String inte:list) {
    		arr.remove(inte);
    	}  	
   }
       
   }

