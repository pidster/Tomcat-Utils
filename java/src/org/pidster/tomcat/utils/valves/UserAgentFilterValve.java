/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.pidster.tomcat.utils.valves;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.RequestFilterValve;

/**
 * A simple Valve which processes the User-agent string in a request
 * against a list of configured regular expressions, in two categories: 
 * deny and allow.
 * 
 * This Valve extends the RequestFilterValve which provides the majority
 * the functional requirement.
 * 
 * @author pid
 *
 */
public class UserAgentFilterValve extends RequestFilterValve {

	/*
	 * (non-Javadoc)
	 * @see org.apache.catalina.valves.RequestFilterValve#invoke(org.apache.catalina.connector.Request, org.apache.catalina.connector.Response)
	 */
	@Override
	public void invoke(Request request, Response response) throws IOException,
			ServletException {

		String property = request.getHeader("User-agent");
		this.process(property, request, response);
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.catalina.valves.RequestFilterValve#setAllow(java.lang.String)
	 */
	@Override
	public void setAllow(String allow) {
		super.setAllow(allow);
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.catalina.valves.RequestFilterValve#setDeny(java.lang.String)
	 */
	@Override
	public void setDeny(String deny) {
		super.setDeny(deny);
	}

}
