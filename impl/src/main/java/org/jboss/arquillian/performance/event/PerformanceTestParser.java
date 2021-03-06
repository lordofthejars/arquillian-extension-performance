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
package org.jboss.arquillian.performance.event;

import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.performance.annotation.PerformanceTest;
import org.jboss.arquillian.performance.meta.PerformanceClassResult;
import org.jboss.arquillian.performance.meta.PerformanceSuiteResult;
import org.jboss.arquillian.test.spi.TestClass;
import org.jboss.arquillian.test.spi.annotation.SuiteScoped;
import org.jboss.arquillian.test.spi.event.suite.BeforeClass;

/**
 * 
 * A PerformanceRuleParser.
 * 
 * @author <a href="mailto:stale.pedersen@jboss.org">Stale W. Pedersen</a>
 * @version $Revision: 1.1 $
 */
public class PerformanceTestParser 
{
   @Inject @SuiteScoped
   private InstanceProducer<PerformanceSuiteResult> suiteResultInst;

   public void callback(@Observes BeforeClass event) throws Exception
   {
      parsePerformanceRules(event.getTestClass());
   }
   
   public void parsePerformanceRules(TestClass testClass)
   {
      PerformanceTest performanceTest = (PerformanceTest) testClass.getAnnotation(PerformanceTest.class);
      if(performanceTest != null)
      {
         PerformanceClassResult classPerformance = 
            new PerformanceClassResult(performanceTest, testClass.getName());
         
         PerformanceSuiteResult suitePerformance = suiteResultInst.get();
         if(suitePerformance == null)
         {
            suitePerformance = new PerformanceSuiteResult(classPerformance.getTestClassName());
            suiteResultInst.set(suitePerformance);
         }
         
         suitePerformance.addClassResult(testClass.getName(), classPerformance);
      }  
   }
}
