/*
 * JBoss, Home of Professional Open Source
 * Copyright 2009, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.arquillian.performance.meta;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * A PerformanceMethodResult.
 * 
 * @author <a href="mailto:stale.pedersen@jboss.org">Stale W. Pedersen</a>
 * @version $Revision: 1.1 $
 */
public class PerformanceMethodResult implements Serializable
{

   /** The serialVersionUID */
   private static final long serialVersionUID = 1249191155205920067L;
   
   private double maxTime;
   private double actualTime;
   private String testMethod;
   
   public PerformanceMethodResult(double maxTime, double actualTime, Method testMethod)
   {
      setMaxTime(maxTime);
      setActualTime(actualTime);
      setTestMethod(testMethod.getName());
   }
   public double getMaxTime()
   {
      return maxTime;
   }
   private void setMaxTime(double maxTime)
   {
      this.maxTime = maxTime;
   }
   public double getActualTime()
   {
      return actualTime;
   }
   private void setActualTime(double actualTime)
   {
      this.actualTime = actualTime;
   }
   public String getTestMethod()
   {
      return testMethod;
   }
   private void setTestMethod(String testMethod)
   {
      this.testMethod = testMethod;
   }
   public void compareResults(PerformanceMethodResult methodResult, double resultsThreshold)
   {
      System.out.println("methods compared: "+testMethod+", and: "+methodResult.getTestMethod());
      System.out.println("Comparing: "+actualTime+" to: "+methodResult.getActualTime());
      System.out.println("Threshold is: "+resultsThreshold);
      if((resultsThreshold +1) * actualTime < methodResult.getActualTime())
      {
         System.out.println("WE FAILED");
         throw new RuntimeException("Method1 actual time: "+actualTime+
               ", Method2: "+methodResult.getActualTime()+
               ", threshold: "+resultsThreshold);
      }
      
   }
}