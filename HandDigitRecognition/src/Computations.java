
import java.util.Arrays;
import java.lang.*;

class Computations{

  //Computes P(X_ij=x|D=d)
  public static double conditionalProbabilityXijgD(Model M, int i, int j,int x, int d){
	  // System.out.println("ans :cond/marg");
	  double cond;
   //Double marg;
	  cond =M.getPXijeq1gD(i, j, d);
    //marg= M.getPD(d);
   // System.out.println("ans :cond/marg");
	//  System.out.println(cond);
	  //System.out.println(1-cond);
    if(x==1)
    	
	  return cond;
    
    return 1.0-cond;
    
  }

  //Computes P(X=x|D=d)  
  public static double conditionalProbabilityXgD(Model M, int[][] x, int d){
	double tot= 1.0;
	  for(int i=0; i<x.length; i++){
		for(int j=0;j<x[i].length; j++){
		//System.out.println(M.getPXijeq1gD(i, j, d));
			tot*=conditionalProbabilityXijgD(M,i,j,x[i][j],d);
		//System.out.println(tot);
	}
		
	}
    return tot;
  }

  //Computes P(X=x,D=d)    
  public static double jointProbabilityXD(Model M, int[][] x, int d){
	  double tot =1.0;
//	  for(int i=0; i<x.length; itot++){
//			for(int j=0;j<x[i].length; j++){
	 //tot*= M.getPD(d)*conditionalProbabilityXgD(M,x,d);
			
	
	 
	  return M.getPD(d)*conditionalProbabilityXgD(M,x,d);
  }

  //Computes P(X=x)      
  public static double marginalProbabilityX(Model M, int[][] x){
   //System.out.println("im here");
	  double tot =0.0;

	  for(int k=0;k<10 ; k++){
		  tot+= jointProbabilityXD(M,x,k);
		  //tot+= jointProbabd =0ilityXD(M,x,k);
	  }
	//  System.out.println("tot=="+tot);
	  
	  return tot;
  }

  //Computes P(D=d|X=x)        
  public static double conditionalProbabilityDgX(Model M, int d, int[][] x){
	 double tot=0.0;
	 tot =jointProbabilityXD(M,x,d)/marginalProbabilityX(M,x);
	 //System.out.println(tot);
	  
    return tot;
  }

  //Computes the most likely digit type for image x          
  public static int classify(Model M, int[][] x){
	 // Double high =jointProbabilityXD(M,x,0)/marginalProbabilityX(M,x);
	  double high=0.0;
	  int digit =0;
	  int i=0;
	  while(i<10){
		  double tot =conditionalProbabilityDgX( M,  i,  x);
	  	
	  if(high<tot){
		  high=tot;
	  		digit =i; 
	  	}
		 i++;
	  	//System.out.println(0.988809344056389 < 0.011080326918292818);
	  
		// System.out.println(tot);
	  }
	  return digit;
  }
  
  //Computes the most likely digit type for image x 
  //as fast as possible
  public static int fastClassify(Model M, int[][] x){
    //dont call other classes
	  //look out for terms that you can take out
//	  int d =0;
//	   double joint;
//	   double t= 1.0;
//		double high;
//		//Double tot1;
//		double higher;
//		double tot =1.0;
//		double tot1= 1.0;
//	  int digit =0;
//	  for(int i=0; i<x.length; i++){
//		for(int j=0;j<x[i].length; j++){
//
//		//probability at 0;
//			tot*=conditionalProbabilityXijgD(M,i,j,x[i][j],0); 
//		  
//		   high = M.getPD(0)*tot;
//			while(d<10){
//				tot1*=conditionalProbabilityXijgD(M,i,j,x[i][j],d); 
//				higher = M.getPD(d)*tot1;
//				
//				if(high <higher){
//					high = higher;
//					digit =d;
//				}
//				d++;
//			}
//		   
//	  }
//	  
//	  }
	  double high=0.0;
	  int digit =0;
	  int i=0;
	  while(i<10){
		  double tot =jointProbabilityXD(M,x,i);
	  	
	  if(high<tot){
		  high=tot;
	  		digit =i; 
	  	}
		 i++;
	  	//System.out.println(0.988809344056389 < 0.011080326918292818);
	  
		// System.out.println(tot);
	  }
	  return digit;
	 
        
}
}