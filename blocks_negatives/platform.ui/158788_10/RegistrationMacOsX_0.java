/*******************************************************************************
 * Copyright (c) 2018 SAP SE and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     SAP SE - initial version
 *******************************************************************************/
package org.eclipse.urischeme.internal.registration;

import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * A parser which understands the output from Lsregister, the Mac OS mimetype
 * registration.
 *
 */
public class LsregisterParser {

	private String lsregisterDump;

	/**
	 * @param lsregisterDump the content from <code>lsregister -dump</code>
	 */
	public LsregisterParser(String lsregisterDump) {
		this.lsregisterDump = lsregisterDump;
	}

	/**
	 * Searches the lsregister dump content for the handler of a given scheme and
	 * returns the path to that handler (app).
	 *
	 * @param scheme
	 * @return path to the app handling the scheme
	 */
	public String getAppFor(String scheme) {
		String[] split = lsregisterDump.split(ENTRY_SEPERATOR);

		Pattern pattern = Pattern.compile(pathRegex, Pattern.MULTILINE);

				map(m -> m.group(1)).findFirst();


	}
}
