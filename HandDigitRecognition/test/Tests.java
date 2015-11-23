/********************************
* File: tests.java
* Description: This class provides tests for the
* ConditionalProbabilityTree class
* Author: B. Marlin and M. Lanighan. UMass Amherst CS240.
* Date: Sept. 19, 2015.
*********************************/

import java.util.*;
import static org.junit.Assert.*;
import org.junit.Test;
import java.lang.*;
import org.junit.rules.Timeout;
import org.junit.Rule;
public class Tests { 
	
	@Rule
	public Timeout globalTimeout = new Timeout(100);
    
	@Test
    public void testConditionalProbabilityXijgD1(){
      System.out.println(" Testing conditionalProbabilityXijgD(M,6,6,0,0):");
      Examples E = new Examples();
      Model M = new Model();
      double output = Computations.conditionalProbabilityXijgD(M,6,6,0,0);
      double shouldBe = 0.994490;
      System.out.printf("      Should be: %.6f. Returned: %.6f \n",shouldBe,output); 
      org.junit.Assert.assertEquals(output,shouldBe, 1e6);  
    }

    @Test
    public void testConditionalProbabilityXijgD2(){
     // System.out.println(" Testing conditionalProbabilityXijgD(M,11,11,1,9):");
      Examples E = new Examples();
      Model M = new Model();
      double output = Computations.conditionalProbabilityXijgD(M,11,11,1,9);
      double shouldBe = 0.001030;
     // System.out.printf("      Should be: %.6f. Returned: %.6f \n",shouldBe,output); 
      org.junit.Assert.assertEquals(output,shouldBe, 1e6);  
    }
    
    @Test
    public void testConditionalProbabilityXgD1(){
      System.out.println(" Testing conditionalProbabilityXgD(M,E.getExample(0),0):");
      Examples E = new Examples();
      Model M = new Model();
      double output = Computations.conditionalProbabilityXgD(M,E.getExample(0),0);
      double shouldBe = 4.827784e-45;
      System.out.printf("      Should be: %.6e. Returned: %.6e \n",shouldBe,output); 
      org.junit.Assert.assertEquals(output,shouldBe, shouldBe/1e4);  
    }

    @Test
    public void testConditionalProbabilityXgD2(){
      System.out.println(" Testing conditionalProbabilityXgD(M,E.getExample(0),9):");
      Examples E = new Examples();
      Model M = new Model();
      double output = Computations.conditionalProbabilityXgD(M,E.getExample(0),9);
      double shouldBe = 2.653623e-52;
      System.out.printf("      Should be: %.6e. Returned: %.6e \n",shouldBe,output); 
      org.junit.Assert.assertEquals(output,shouldBe, shouldBe/1e4);  
    }    
    
    @Test
    public void testJointProbabilityXD1(){
      System.out.println(" Testing jointProbabilityXD(M,E.getExample(0),0):");
      Examples E = new Examples();
      Model M = new Model();
      double output = Computations.jointProbabilityXD(M,E.getExample(0),0);
      double shouldBe = 4.827784e-46 ;
      System.out.printf("      Should be: %.6e. Returned: %.6e \n",shouldBe,output); 
      org.junit.Assert.assertEquals(output,shouldBe, shouldBe/1e4);  
    }

    @Test
    public void testJointProbabilityX2(){
      System.out.println(" Testing jointProbabilityXD(M,E.getExample(0),9):");
      Examples E = new Examples();
      Model M = new Model();
      double output = Computations.jointProbabilityXD(M,E.getExample(0),9);
      double shouldBe = 2.653623e-53;
      System.out.printf("      Should be: %.6e. Returned: %.6e \n",shouldBe,output); 
      org.junit.Assert.assertEquals(output,shouldBe, shouldBe/1e4);  
    } 
        
    @Test
    public void testMarginalProbabilityX1(){
      System.out.println(" Testing marginalProbabilityX(M,E.getExample(0)):");
      Examples E = new Examples();
      Model M = new Model();
      double output = Computations.marginalProbabilityX(M,E.getExample(0));
      double shouldBe = 4.892994e-46;
      System.out.printf("      Should be: %.6e. Returned: %.6e \n",shouldBe,output); 
      org.junit.Assert.assertEquals(output,shouldBe, shouldBe/1e4);  
    } 

    @Test
    public void testMarginalProbabilityX2(){
      System.out.println(" Testing marginalProbabilityX(M,E.getExample(9)):");
      Examples E = new Examples();
      Model M = new Model();
      double output =Computations.marginalProbabilityX(M,E.getExample(9));
      double shouldBe = 3.305696e-56;
      System.out.printf("      Should be: %.6e. Returned: %.6e \n",shouldBe,output); 
      org.junit.Assert.assertEquals(output,shouldBe, shouldBe/1e4);  
    }     
        
    @Test
    public void testConditionalProbabilityDgX1(){
      System.out.println(" Testing conditionalProbabilityDgX(M,0, E.getExample(0)):");
      Examples E = new Examples();
      Model M = new Model();
      double output = Computations.conditionalProbabilityDgX(M,0,E.getExample(0));
      double shouldBe = 9.866730e-01;
      System.out.printf("      Should be: %.6e. Returned: %.6e \n",shouldBe,output); 
      org.junit.Assert.assertEquals(output,shouldBe, shouldBe/1e4);  
    }     

    @Test
    public void testConditionalProbabilityDgX2(){
      System.out.println(" Testing conditionalProbabilityDgX(M,9, E.getExample(0)):");
      Examples E = new Examples();
      Model M = new Model();
      double output = Computations.conditionalProbabilityDgX(M,9,E.getExample(0));
      double shouldBe = 5.423312e-08;
      System.out.printf("      Should be: %.6e. Returned: %.6e \n",shouldBe,output); 
      org.junit.Assert.assertEquals(output,shouldBe, shouldBe/1e4);  
    }     

    @Test
    public void testClassify0(){
      System.out.println(" Testing classify(M,E.getExample(0)):");
      Examples E = new Examples();
      Model M = new Model();
      int output = Computations.classify(M,E.getExample(0));
      int shouldBe = 0;
      System.out.printf("      Should be: %d. Returned: %d \n",shouldBe,output); 
      org.junit.Assert.assertEquals(output,shouldBe);  
    }     
    
    @Test
    public void testClassify1(){
      System.out.println(" Testing classify(M,E.getExample(1)):");
      Examples E = new Examples();
      Model M = new Model();
      int output = Computations.classify(M,E.getExample(1));
      int shouldBe = 8;
      System.out.printf("      Should be: %d. Returned: %d \n",shouldBe,output); 
      org.junit.Assert.assertEquals(output,shouldBe);  
    }     
    
    @Test
    public void testClassify2(){
      System.out.println(" Testing classify(M,E.getExample(2)):");
      Examples E = new Examples();
      Model M = new Model();
      int output = Computations.classify(M,E.getExample(2));
      int shouldBe = 2;
      System.out.printf("      Should be: %d. Returned: %d \n",shouldBe,output); 
      org.junit.Assert.assertEquals(output,shouldBe);  
    }         

    @Test
    public void testClassify3(){
      System.out.println(" Testing classify(M,E.getExample(3)):");
      Examples E = new Examples();
      Model M = new Model();
      int output = Computations.classify(M,E.getExample(3));
      int shouldBe = 3;
      System.out.printf("      Should be: %d. Returned: %d \n",shouldBe,output); 
      org.junit.Assert.assertEquals(output,shouldBe);  
    } 
    
    @Test
    public void testClassify4(){
      System.out.println(" Testing classify(M,E.getExample(4)):");
      Examples E = new Examples();
      Model M = new Model();
      int output = Computations.classify(M,E.getExample(4));
      int shouldBe = 4;
      System.out.printf("      Should be: %d. Returned: %d \n",shouldBe,output); 
      org.junit.Assert.assertEquals(output,shouldBe);  
    } 
    
    @Test
    public void testClassify5(){
      System.out.println(" Testing classify(M,E.getExample(5)):");
      Examples E = new Examples();
      Model M = new Model();
      int output = Computations.classify(M,E.getExample(5));
      int shouldBe = 3;
      System.out.printf("      Should be: %d. Returned: %d \n",shouldBe,output); 
      org.junit.Assert.assertEquals(output,shouldBe);  
    } 
        
    @Test
    public void testClassify6(){
      System.out.println(" Testing classify(M,E.getExample(6)):");
      Examples E = new Examples();
      Model M = new Model();
      int output = Computations.classify(M,E.getExample(6));
      int shouldBe = 6;
      System.out.printf("      Should be: %d. Returned: %d \n",shouldBe,output); 
      org.junit.Assert.assertEquals(output,shouldBe);  
    } 
    
    @Test
    public void testClassify7(){
      System.out.println(" Testing classify(M,E.getExample(7)):");
      Examples E = new Examples();
      Model M = new Model();
      int output = Computations.classify(M,E.getExample(7));
      int shouldBe = 7;
      System.out.printf("      Should be: %d. Returned: %d \n",shouldBe,output); 
      org.junit.Assert.assertEquals(output,shouldBe);  
    } 
    
    @Test
    public void testClassify8(){
      System.out.println(" Testing classify(M,E.getExample(8)):");
      Examples E = new Examples();
      Model M = new Model();
      int output = Computations.classify(M,E.getExample(8));
      int shouldBe = 8;
      System.out.printf("      Should be: %d. Returned: %d \n",shouldBe,output); 
      org.junit.Assert.assertEquals(output,shouldBe);  
    } 
    
    @Test(timeout =100)
    public void testClassify9(){
      System.out.println(" Testing classify(M,E.getExample(9)):");
      Examples E = new Examples();
      Model M = new Model();
      int output = Computations.classify(M,E.getExample(9));
      int shouldBe = 9;
      System.out.printf("      Should be: %d. Returned: %d \n",shouldBe,output); 
      org.junit.Assert.assertEquals(output,shouldBe);  
    }     

    @Test//(timeout =100)
    public void testFastClassify0(){
      System.out.println(" Testing fastClassify(M,E.getExample(0)):");
      Examples E = new Examples();
      Model M = new Model();
      int output = Computations.fastClassify(M,E.getExample(0));
      int shouldBe = 0;
      System.out.printf("      Should be: %d. Returned: %d \n",shouldBe,output); 
      org.junit.Assert.assertEquals(output,shouldBe);  
    }
    @Test
    public void testSpeed(){
    
      System.out.println(" Testing fastClassify Speed:");

      
      Examples E = new Examples();
      Model M = new Model();   
      long startTime, endTime; 
      int output;
      int reps = 100000;
      long diff1, diff2;
      
      
      startTime = System.currentTimeMillis();
      for(int i=0;i<reps;i++){
        output = Computations.classify(M,E.getExample(0));
        output = Computations.classify(M,E.getExample(1));
        output = Computations.classify(M,E.getExample(2));
        output = Computations.classify(M,E.getExample(3));
        output = Computations.classify(M,E.getExample(4));
        output = Computations.classify(M,E.getExample(5));
        output = Computations.classify(M,E.getExample(6));
        output = Computations.classify(M,E.getExample(7));
        output = Computations.classify(M,E.getExample(8));
        output = Computations.classify(M,E.getExample(9));      
      }
      endTime = System.currentTimeMillis();
      diff1   = endTime - startTime;
      System.out.printf("      Classify(): %d ms per %d calls\n", diff1, reps);    
      
      startTime = System.currentTimeMillis();
      for(int i=0;i<reps;i++){
        output = Computations.fastClassify(M,E.getExample(0));
        output = Computations.fastClassify(M,E.getExample(1));
        output = Computations.fastClassify(M,E.getExample(2));
        output = Computations.fastClassify(M,E.getExample(3));
        output = Computations.fastClassify(M,E.getExample(4));
        output = Computations.fastClassify(M,E.getExample(5));
        output = Computations.fastClassify(M,E.getExample(6));
        output = Computations.fastClassify(M,E.getExample(7));
        output = Computations.fastClassify(M,E.getExample(8));
        output = Computations.fastClassify(M,E.getExample(9));      
      }
      endTime = System.currentTimeMillis();
      diff2   = endTime - startTime;
      System.out.printf("      fastClassify(): %d ms per %d calls\n", diff2, reps); 
      
      org.junit.Assert.assertTrue(diff2<diff1);
      
  }
    
    
  public static void main(String [ ] args){
  
    org.junit.runner.JUnitCore.main("Tests");
    
  }  

}
