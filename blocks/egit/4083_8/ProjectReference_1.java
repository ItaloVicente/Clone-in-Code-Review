package org.eclipse.egit.core;

import java.net.URISyntaxException;
import java.util.regex.Pattern;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.osgi.util.NLS;

public final class ProjectReference {

	private static final String DEFAULT_BRANCH = Constants.MASTER;

	private String version;

	private String projectDir;

	private URIish repository;

	private String branch = DEFAULT_BRANCH;

	private String origin = Constants.DEFAULT_REMOTE_NAME;

	static final String SEPARATOR = ","; //$NON-NLS-1$

	@SuppressWarnings("boxing")
	public ProjectReference(final String reference) throws URISyntaxException, IllegalArgumentException {
		final String[] tokens = reference.split(Pattern.quote(ProjectReference.SEPARATOR));
		if (tokens.length != 4)
			throw new IllegalArgumentException(NLS.bind(
					CoreText.ProjectReference_InvalidTokensCount, new Object[] {
							4, tokens.length, tokens }));

		this.version = tokens[0];
		this.repository = new URIish(tokens[1]);
		if (!"".equals(tokens[2])) //$NON-NLS-1$
			this.branch = tokens[2];
		this.projectDir = tokens[3];
	}

	public URIish getRepository() {
		return repository;
	}

	public String getBranch() {
		return branch;
	}

	public String getOrigin() {
		return origin;
	}

	public String getProjectDir() {
		return projectDir;
	}

	String getVersion() {
		return version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((branch == null) ? 0 : branch.hashCode());
		result = prime * result
				+ ((projectDir == null) ? 0 : projectDir.hashCode());
		result = prime * result
				+ ((repository == null) ? 0 : repository.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ProjectReference))
			return false;
		ProjectReference other = (ProjectReference) obj;
		if (branch == null) {
			if (other.branch != null)
				return false;
		} else if (!branch.equals(other.branch))
			return false;
		if (projectDir == null) {
			if (other.projectDir != null)
				return false;
		} else if (!projectDir.equals(other.projectDir))
			return false;
		if (repository == null) {
			if (other.repository != null)
				return false;
		} else if (!repository.equals(other.repository))
			return false;
		return true;
	}
}
