import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class naive {

	private static double[]   frequenciesham;
	private static double[]   frequenciesspam;

	public static void main(String[] args) throws FileNotFoundException {
		
	   String hampath="C:\\Users\\John\\Desktop\\enron1\\enron1\\ham";
	   String spampath="C:\\Users\\John\\Desktop\\enron1\\enron1\\spam";
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
	    
	    //information gain:remove elements with low appearance
	    //we will see
	
	    float hamsize=seussham.size();
	    float spamsize=seussspam.size();
	      	 
	    //read from user desired mail
	    
	   String askedpath="C:\\Users\\John\\Desktop\\enron1\\enron1\\spam\\0153.2004-01-07.GP.spam.txt";
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
  
	for(int i=0; i<ok; i++) {
		
		//calculating possibilities
	
		if(frequenciesham[i]!=0) {
	    sumham=sumham+Math.log1p((frequenciesham[i]/hamsize));
		}
		
		
		if(frequenciesspam[i]!=0) {
	    sumspam=sumspam+Math.log1p(frequenciesspam[i]/spamsize);
		}
	}
	
	if(sumham>sumspam) {
		System.out.println("Its ham");
	}else {
		System.out.println("Its spam");
	}

	
	//till here ok diagrams now:
	//Training:90% test:10%
	
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
