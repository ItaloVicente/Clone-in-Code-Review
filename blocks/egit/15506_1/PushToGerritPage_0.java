package org.eclipse.egit.ui.internal.gerrit;

import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.ConfigConstants;

public class GerritUtil {

	public static final String REFS_FOR = "refs/for/"; //$NON-NLS-1$

	public static boolean getCreateChangeId(Config config) {
		return config.getBoolean(ConfigConstants.CONFIG_GERRIT_SECTION,
				ConfigConstants.CONFIG_KEY_CREATECHANGEID, false);
	}

	public static void setCreateChangeId(Config config) {
		config.setBoolean(ConfigConstants.CONFIG_GERRIT_SECTION, null,
				ConfigConstants.CONFIG_KEY_CREATECHANGEID, true);
	}
}
