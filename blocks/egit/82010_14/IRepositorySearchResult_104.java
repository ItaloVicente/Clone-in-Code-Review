package org.eclipse.egit.ui.internal.provisional.wizards;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.egit.core.securestorage.UserPasswordCredentials;

public class GitRepositoryInfo {

	private final String cloneUri;
	private UserPasswordCredentials credentials;
	private boolean shouldSaveCredentialsInSecureStore;
	private String repositoryName;
	private final List<String> fetchRefSpecs = new ArrayList<>();

	public static class PushInfo {

		private String pushRefSpec;
		private String pushUri;

		public PushInfo(String pushRefSpec, String pushUri) {
			this.pushRefSpec = pushRefSpec;
			this.pushUri = pushUri;
		}

		public String getPushRefSpec() {
			return pushRefSpec;
		}

		public String getPushUri() {
			return pushUri;
		}
	}

	private List<PushInfo> pushInfos = new ArrayList<>();

	public static class RepositoryConfigProperty {

		private String section;
		private String subsection;
		private String name;
		private String value;

		public RepositoryConfigProperty(String section, String subsection, String name, String value) {
			this.section = section;
			this.subsection = subsection;
			this.name = name;
			this.value = value;
		}

		public String getSection() {
			return section;
		}

		public String getSubsection() {
			return subsection;
		}

		public String getName() {
			return name;
		}

		public String getValue() {
			return value;
		}
	}

	private final List<RepositoryConfigProperty> repositoryConfigProperties = new ArrayList<>();


	public GitRepositoryInfo(String cloneUri) {
		this.cloneUri = cloneUri;
	}

	public String getCloneUri() {
		return cloneUri;
	}

	public void setCredentials(String user, String password) {
		credentials = new UserPasswordCredentials(user, password);
	}

	public UserPasswordCredentials getCredentials() {
		return credentials;
	}

	public void setShouldSaveCredentialsInSecureStore(
			boolean shouldSaveCredentialsInSecureStore) {
		this.shouldSaveCredentialsInSecureStore = shouldSaveCredentialsInSecureStore;
	}

	public boolean shouldSaveCredentialsInSecureStore() {
		return shouldSaveCredentialsInSecureStore;
	}

	public void setRepositoryName(String repositoryName) {
		this.repositoryName = repositoryName;
	}

	public String getRepositoryName() {
		return repositoryName;
	}

	public void addFetchRefSpec(String fetchRefSpec) {
		this.fetchRefSpecs.add(fetchRefSpec);
	}

	public List<String> getFetchRefSpecs() {
		return fetchRefSpecs;
	}

	public void addPushInfo(String pushRefSpec, String pushUri) {
		this.pushInfos.add(new PushInfo(pushRefSpec, pushUri));
	}

	public List<PushInfo> getPushInfos() {
		return pushInfos;
	}

	public void addRepositoryConfigProperty(String section, String subsection, String name, String value) {
		repositoryConfigProperties.add(new RepositoryConfigProperty(section, subsection, name, value));
	}

	public List<RepositoryConfigProperty> getRepositoryConfigProperties() {
		return repositoryConfigProperties;
	}

}
