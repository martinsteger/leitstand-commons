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
package io.leitstand.commons.jsonb;

import static io.leitstand.commons.jsonb.JsonbDefaults.jsonb;
import static io.leitstand.commons.model.StringUtil.isEmptyString;

import java.io.StringReader;

public final class JsonProcessor {
	
	public static <M> M unmarshal(Class<M> eventType, String json) {
		if(isEmptyString(json)) {
			return null;
		}
		try(StringReader reader = new StringReader(json)){
			return jsonb().fromJson(reader, eventType);
		}
	}

	public static String marshal(Object o) {
		if(o == null) {
			return null;
		}
		return jsonb().toJson(o);
	}
	
	private JsonProcessor() {
		// No instances allowed.
	}
	
	
}
