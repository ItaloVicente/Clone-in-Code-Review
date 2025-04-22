/*******************************************************************************
 * Copyright (c) 2013 Robin Stocker <robin@nibor.org> and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *******************************************************************************/
package org.eclipse.egit.ui.internal.gerrit;

import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.ConfigConstants;

/**
 * Gerrit utilities
 */
public class GerritUtil {

	/**
	 * The Gerrit push prefix {@value} .
	 */

	/**
	 * @param config
	 * @return true if there if "create change ID" is configured
	 */
	public static boolean getCreateChangeId(Config config) {
		return config.getBoolean(ConfigConstants.CONFIG_GERRIT_SECTION,
				ConfigConstants.CONFIG_KEY_CREATECHANGEID, false);
	}

	/**
	 * Set the "create change ID" configuration
	 *
	 * @param config
	 */
	public static void setCreateChangeId(Config config) {
		config.setBoolean(ConfigConstants.CONFIG_GERRIT_SECTION, null,
				ConfigConstants.CONFIG_KEY_CREATECHANGEID, true);
	}
}
