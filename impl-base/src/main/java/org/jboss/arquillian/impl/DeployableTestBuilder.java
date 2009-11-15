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
package org.jboss.arquillian.impl;

import javax.security.auth.login.Configuration;

import org.jboss.arquillian.api.Controlable;
import org.jboss.arquillian.api.Deployer;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.tmpdpl.api.container.DeploymentException;

/**
 * DeployableTestBuilder
 *
 * @author <a href="mailto:aslak@conduct.no">Aslak Knutsen</a>
 * @version $Revision: $
 */
public class DeployableTestBuilder
{
   private DeployableTestBuilder() {}
   
   // TODO: lookup/load container, setup DeployableTest
   public static DeployableTest build(Configuration config) 
   {
      Controlable controller = null;
      Deployer deployer = null;
      
      if(DeployableTest.isInContainer()) 
      {
         controller = new MockContainer();
         deployer = new MockContainer();
      }
      else 
      {
         JbossEmbeddedContainer container = new JbossEmbeddedContainer();
         controller = container;
         deployer = container;
      }

      return new DeployableTest(
            controller,
            deployer
            );
   }
   
   private static class MockContainer implements Controlable, Deployer 
   {
      @Override
      public void start() throws Exception
      {
      }

      @Override
      public void stop() throws Exception
      {
      }

      @Override
      public void deploy(Archive<?> archive) throws DeploymentException
      {
      }

      @Override
      public void undeploy(Archive<?> archive) throws DeploymentException
      {
      }
   }
}