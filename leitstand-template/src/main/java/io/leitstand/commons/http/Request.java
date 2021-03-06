/*
 * Copyright 2020 RtBrick Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package io.leitstand.commons.http;

import static io.leitstand.commons.http.Request.HttpMethod.DELETE;
import static io.leitstand.commons.http.Request.HttpMethod.GET;
import static io.leitstand.commons.http.Request.HttpMethod.POST;
import static io.leitstand.commons.http.Request.HttpMethod.PUT;
import static io.leitstand.commons.jsonb.IsoDateAdapter.isoDateFormat;
import static io.leitstand.commons.jsonb.JsonProcessor.unmarshal;
import static io.leitstand.commons.model.BuilderUtil.assertNotInvalidated;
import static java.util.Collections.emptyMap;
import static java.util.Collections.unmodifiableMap;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.json.JsonObject;

import io.leitstand.commons.json.MapMarshaller;

public class Request {
	
	public static Builder newRequest() {
		return new Builder();
	}
	
	public static class Builder {
		
		private Request request = new Request();
		
		public Builder withMethod(HttpMethod method) {
			assertNotInvalidated(getClass(), request);
			request.method = method;
			return this;
		}
		
		public Builder withPath(String path) {
			assertNotInvalidated(getClass(), request);
			request.path = path;
			return this;
		}
		
		public Builder withHeader(String header, String value) {
			return addHeader(header,value);
		}
		
		public Builder withHeader(String header, Date value) {
			return addHeader(header,isoDateFormat(value));
		}
		
		public Builder withHeader(String header, int value) {
			return addHeader(header,value);
		}
		
		private Builder addHeader(String header, Object value) {
			if(request.headers == null) {
				request.headers = new LinkedHashMap<>();
			}
			request.headers.put(header, value);
			return this;
		}
		
		public Builder withHeaders(Map<String,Object> headers) {
			assertNotInvalidated(getClass(), request);
			request.headers = new LinkedHashMap<>(headers);
			return this;
		}
		
		public Builder withBody(String body) {
	        assertNotInvalidated(getClass(), request);
            request.body = body;
            return this;
		}
		
		public Builder withBody(Object body) {
			assertNotInvalidated(getClass(),request);
			request.body = body;
			return this;
		}
		
		public Builder withBody(JsonObject body) {
            assertNotInvalidated(getClass(), request);
            request.body = body;
            return this;
        }
		
		public Builder withBody(Map<String,Object> body) {
		    return withBody(MapMarshaller.marshal(body).toJson());
		}
		
		public Request build() {
			try {
				assertNotInvalidated(getClass(), request);
				return request;
			} finally {
				this.request = null;
			}
		}
	}
	

	public enum HttpMethod {
		GET,
		POST,
		PUT,
		DELETE
	}
	
	private String path;
	private HttpMethod method;
	private Map<String,Object> headers;
	private Object body;
	
	public String getPath() {
		return path;
	}
	
	public HttpMethod getMethod() {
		return method;
	}
	
	public boolean isGetRequest() {
		return method == GET;
	}
	
	public boolean isPostRequest() {
		return method == POST;
	}
	
	public boolean isDeleteRequest() {
		return method == DELETE;
	}
	
	public boolean isPutRequest() {
		return method == PUT;
	}
	
	public Map<String, Object> getHeaders() {
		if(headers == null) {
			return emptyMap();
		}
		return unmodifiableMap(headers);
	}
	
	public <T> T getHeader(String name) {
	    if(headers == null) {
	        return null;
	    }
		return (T) headers.get(name);
	}
	
	public <T> T getBody(Class<T> entity){
	    if(body == null) {
	        return null;
	    }
	    if(entity.isInstance(body)){
	        return (T) body;
	    }
		return unmarshal(entity, getBody().toString());
	}
	
	public <T> T getBody() {
		return (T) body;
	}
	
}
