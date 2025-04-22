package org.eclipse.egit.gitflow;

import static org.eclipse.egit.gitflow.GitFlowDefaults.DEVELOP;
import static org.eclipse.egit.gitflow.GitFlowDefaults.FEATURE_PREFIX;
import static org.eclipse.egit.gitflow.GitFlowDefaults.HOTFIX_PREFIX;
import static org.eclipse.egit.gitflow.GitFlowDefaults.RELEASE_PREFIX;
import static org.eclipse.egit.gitflow.GitFlowDefaults.VERSION_TAG;
import static org.eclipse.jgit.lib.Constants.DEFAULT_REMOTE_NAME;
import static org.eclipse.jgit.lib.Constants.R_HEADS;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.transport.RemoteConfig;

public class GitFlowConfig {
	public static final String MASTER_KEY = "master"; //$NON-NLS-1$

	public static final String DEVELOP_KEY = "develop"; //$NON-NLS-1$

	public static final String HOTFIX_KEY = "hotfix"; //$NON-NLS-1$

	public static final String RELEASE_KEY = "release"; //$NON-NLS-1$

	public static final String FEATURE_KEY = "feature"; //$NON-NLS-1$

	public static final String VERSION_TAG_KEY = "versiontag"; //$NON-NLS-1$

	public static final String USER_SECTION = "user"; //$NON-NLS-1$

	public static final String BRANCH_SECTION = "branch"; //$NON-NLS-1$

	public static final String PREFIX_SECTION = "prefix"; //$NON-NLS-1$

	public static final String GITFLOW_SECTION = "gitflow"; //$NON-NLS-1$

	public static final String REMOTE_KEY = "remote"; //$NON-NLS-1$

	public static final String MERGE_KEY = "merge"; //$NON-NLS-1$

	private Repository repository;

	public GitFlowConfig(Repository repository) {
		Assert.isNotNull(repository);
		this.repository = repository;
	}

	public boolean isInitialized() throws IOException {
		StoredConfig config = repository.getConfig();
		Set<String> sections = config.getSections();
		return sections.contains(GITFLOW_SECTION);
	}

	public String getUser() {
		StoredConfig config = repository.getConfig();
		String userName = config.getString(USER_SECTION, null, "name"); //$NON-NLS-1$
		String email = config.getString(USER_SECTION, null, "email"); //$NON-NLS-1$
		return String.format("%s <%s>", userName, email); //$NON-NLS-1$
	}

	public String getFeaturePrefix() {
		return getPrefix(FEATURE_KEY, FEATURE_PREFIX);
	}

	public String getReleasePrefix() {
		return getPrefix(RELEASE_KEY, RELEASE_PREFIX);
	}

	public String getHotfixPrefix() {
		return getPrefix(HOTFIX_KEY, HOTFIX_PREFIX);
	}

	public String getVersionTagPrefix() {
		return getPrefix(VERSION_TAG_KEY, VERSION_TAG);
	}

	public String getDevelop() {
		return getBranch(DEVELOP_KEY, DEVELOP);
	}

	public String getDevelopFull() {
		return R_HEADS + getDevelop();
	}

	public String getMaster() {
		return getBranch(MASTER_KEY, GitFlowDefaults.MASTER);
	}

	public String getPrefix(String prefixName, String defaultPrefix) {
		StoredConfig config = repository.getConfig();
		String result = config.getString(GITFLOW_SECTION, PREFIX_SECTION,
				prefixName);
		return (result == null) ? defaultPrefix : result;
	}

	public String getBranch(String branch, String defaultBranch) {
		StoredConfig config = repository.getConfig();
		String result = config.getString(GITFLOW_SECTION, BRANCH_SECTION,
				branch);
		return (result == null) ? defaultBranch : result;
	}

	public void setPrefix(String prefixName, String value) {
		StoredConfig config = repository.getConfig();
		config.setString(GITFLOW_SECTION, PREFIX_SECTION, prefixName, value);
	}

	public void setBranch(String branchName, String value) {
		StoredConfig config = repository.getConfig();
		config.setString(GITFLOW_SECTION, BRANCH_SECTION, branchName, value);
	}

	public String getFullFeatureBranchName(String featureName) {
		return R_HEADS + getFeatureBranchName(featureName);
	}

	public String getFeatureBranchName(String featureName) {
		return getFeaturePrefix() + featureName;
	}

	public String getHotfixBranchName(String hotfixName) {
		return getHotfixPrefix() + hotfixName;
	}

	public String getFullHotfixBranchName(String hotfixName) {
		return R_HEADS + getHotfixBranchName(hotfixName);
	}

	public String getFullReleaseBranchName(String releaseName) {
		return R_HEADS + getReleaseBranchName(releaseName);
	}

	public String getReleaseBranchName(String releaseName) {
		return getReleasePrefix() + releaseName;
	}

	public RemoteConfig getDefaultRemoteConfig() {
		StoredConfig rc = repository.getConfig();
		RemoteConfig result;
		try {
			result = new RemoteConfig(rc, DEFAULT_REMOTE_NAME);
		} catch (URISyntaxException e) {
			throw new IllegalStateException(e);
		}
		return result;
	}

	public boolean hasDefaultRemote() {
		RemoteConfig config = getDefaultRemoteConfig();
		return !config.getURIs().isEmpty();
	}

	public void setRemote(String featureName, String value) throws IOException {
		setBranchValue(featureName, value, REMOTE_KEY);
	}

	public void setUpstreamBranchName(String featureName, String value) throws IOException {
		setBranchValue(featureName, value, MERGE_KEY);
	}

	public String getUpstreamBranchName(String featureName) {
		StoredConfig config = repository.getConfig();
		return config.getString(BRANCH_SECTION,
				getFeatureBranchName(featureName), MERGE_KEY);
	}

	private void setBranchValue(String featureName, String value,
			String mergeKey) throws IOException {
		StoredConfig config = repository.getConfig();
		config.setString(BRANCH_SECTION, featureName, mergeKey, value);
		config.save();
	}

	public String getRemoteName(String featureName) {
		StoredConfig config = repository.getConfig();
		return config.getString(BRANCH_SECTION,
				getFeatureBranchName(featureName), REMOTE_KEY);
	}
}
