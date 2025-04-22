
package org.eclipse.jgit.lib;

import java.net.URISyntaxException;

import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteConfig;

public class BranchConfig {

	public enum BranchRebaseMode implements Config.ConfigEnum {

		REBASE("true")
		PRESERVE("preserve")
		INTERACTIVE("interactive")

		private final String configValue;

		private BranchRebaseMode(String configValue) {
			this.configValue = configValue;
		}

		@Override
		public String toConfigValue() {
			return configValue;
		}

		@Override
		public boolean matchConfigValue(String s) {
			return configValue.equals(s);
		}
	}


	private final Config config;
	private final String branchName;

	public BranchConfig(Config config
		this.config = config;
		this.branchName = branchName;
	}

	public String getTrackingBranch() {
		String remote = getRemoteOrDefault();
		String mergeRef = getMerge();
		if (remote == null || mergeRef == null)
			return null;

		if (isRemoteLocal())
			return mergeRef;

		return findRemoteTrackingBranch(remote
	}

	public String getRemoteTrackingBranch() {
		String remote = getRemoteOrDefault();
		String mergeRef = getMerge();
		if (remote == null || mergeRef == null)
			return null;

		return findRemoteTrackingBranch(remote
	}

	public boolean isRemoteLocal() {
		return LOCAL_REPOSITORY.equals(getRemote());
	}

	public String getRemote() {
		return config.getString(ConfigConstants.CONFIG_BRANCH_SECTION
				branchName
	}

	public String getMerge() {
		return config.getString(ConfigConstants.CONFIG_BRANCH_SECTION
				branchName
	}

	public boolean isRebase() {
		return getRebaseMode() != BranchRebaseMode.NONE;
	}

	public BranchRebaseMode getRebaseMode() {
		return config.getEnum(BranchRebaseMode.values()
				ConfigConstants.CONFIG_BRANCH_SECTION
				ConfigConstants.CONFIG_KEY_REBASE
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

	private String getRemoteOrDefault() {
		String remote = getRemote();
		if (remote == null)
			return Constants.DEFAULT_REMOTE_NAME;
		else
			return remote;
	}
}
