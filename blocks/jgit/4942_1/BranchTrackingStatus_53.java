
package org.eclipse.jgit.lib;

import java.net.URISyntaxException;

import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteConfig;

public class BranchConfig {

	private final Config config;
	private final String branchName;

	public BranchConfig(final Config config
		this.config = config;
		this.branchName = branchName;
	}

	public String getTrackingBranch() {
		String remote = getRemote();
		String mergeRef = getMergeBranch();
		if (remote == null || mergeRef == null)
			return null;

		if (remote.equals("."))
			return mergeRef;

		return findRemoteTrackingBranch(remote
	}

	public String getRemoteTrackingBranch() {
		String remote = getRemote();
		String mergeRef = getMergeBranch();
		if (remote == null || mergeRef == null)
			return null;

		return findRemoteTrackingBranch(remote
	}

	private String findRemoteTrackingBranch(String remote
		RemoteConfig remoteConfig;
		try {
			remoteConfig = new RemoteConfig(config
		} catch (URISyntaxException e) {
			return null;
		}
		for (RefSpec refSpec : remoteConfig.getFetchRefSpecs()) {
			if (refSpec.matchSource(mergeRef)) {
				RefSpec expanded = refSpec.expandFromSource(mergeRef);
				return expanded.getDestination();
			}
		}
		return null;
	}

	private String getRemote() {
		String remoteName = config.getString(
				ConfigConstants.CONFIG_BRANCH_SECTION
				ConfigConstants.CONFIG_KEY_REMOTE);
		if (remoteName == null)
			return Constants.DEFAULT_REMOTE_NAME;
		else
			return remoteName;
	}

	private String getMergeBranch() {
		String mergeRef = config.getString(
				ConfigConstants.CONFIG_BRANCH_SECTION
				ConfigConstants.CONFIG_KEY_MERGE);
		return mergeRef;
	}
}
