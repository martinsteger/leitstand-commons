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
package io.leitstand.commons.db;

import java.sql.SQLException;


/**
 * The <code>DatabaseException</code> application exception is thrown by the 
 * {@link DatabaseService} to report SQL errors and to rollback the current transaction.
 *
 */
public class DatabaseException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	/**
	 * Create a <code>DatabaseException</code>.
	 * @param cause - the SQL exception reported by the database.
	 */
	public DatabaseException(SQLException cause) {
		super(cause);
	}
	
}