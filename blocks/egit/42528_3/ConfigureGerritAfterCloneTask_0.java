package org.eclipse.egit.core.internal.gerrit;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.URIish;

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

	public static RemoteConfig findRemoteConfig(Config config, String remoteName)
			throws URISyntaxException {
		List<RemoteConfig> allRemoteConfigs;
		RemoteConfig remoteConfig = null;
		allRemoteConfigs = RemoteConfig.getAllRemoteConfigs(config);
		for (RemoteConfig rc : allRemoteConfigs) {
			if (rc.getName().equals(remoteName))
				remoteConfig = rc;
		}
		return remoteConfig;
	}

	public static void configurePushURI(RemoteConfig remoteConfig,
			URIish pushURI)
			throws URISyntaxException {
		List<URIish> pushURIs = new ArrayList<URIish>(
				remoteConfig.getPushURIs());
		for (URIish urIish : pushURIs) {
			remoteConfig.removePushURI(urIish);
		}
		remoteConfig.addPushURI(pushURI);
	}

	public static void configurePushRefSpec(RemoteConfig remoteConfig,
			String gerritBranch) {
		List<RefSpec> pushRefSpecs = new ArrayList<RefSpec>(
				remoteConfig.getPushRefSpecs());
		for (RefSpec refSpec : pushRefSpecs) {
			remoteConfig.removePushRefSpec(refSpec);
		}
		remoteConfig.addPushRefSpec(new RefSpec(
				"HEAD:" + GerritUtil.REFS_FOR + gerritBranch)); //$NON-NLS-1$
	}

	public static void configureFetchNotes(RemoteConfig remoteConfig) {
		String notesRef = Constants.R_NOTES + "*"; //$NON-NLS-1$
		List<RefSpec> fetchRefSpecs = remoteConfig.getFetchRefSpecs();
		for (RefSpec refSpec : fetchRefSpecs) {
			if (refSpec.matchSource(notesRef))
				return;
		}
		remoteConfig.addFetchRefSpec(new RefSpec(notesRef + ":" + notesRef)); //$NON-NLS-1$
	}


}
