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

	@SuppressWarnings("boxing")
	public ProjectReference(final String reference) throws URISyntaxException, IllegalArgumentException {
		final String[] tokens = reference.split(Pattern.quote(GitProjectSetCapability.SEPARATOR));
		if (tokens.length != 4)
			throw new IllegalArgumentException(NLS.bind(
					CoreText.GitProjectSetCapability_InvalidTokensCount, new Object[] {
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

	public String getVersion() {
		return version;
	}

	public String getProjectDir() {
		return projectDir;
	}
}
