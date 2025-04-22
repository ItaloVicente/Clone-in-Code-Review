package org.eclipse.jgit.api;

import java.io.IOException;
import java.net.URISyntaxException;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.URIish;

public class RemoteAddCommand extends GitCommand<RemoteConfig> {

	private String name;

	private URIish uri;

	protected RemoteAddCommand(Repository repo) {
		super(repo);
	}

	public RemoteAddCommand setName(String name) {
		this.name = name;
		return this;
	}

	public RemoteAddCommand setUri(URIish uri) {
		this.uri = uri;
		return this;
	}

	@Override
	public RemoteConfig call() throws GitAPIException {
		checkCallable();

		try {
			StoredConfig config = repo.getConfig();
			RemoteConfig remote = new RemoteConfig(config

			RefSpec refSpec = new RefSpec();
			refSpec = refSpec.setForceUpdate(true);
			refSpec = refSpec.setSourceDestination(Constants.R_HEADS + "*"
			remote.addFetchRefSpec(refSpec);

			remote.addURI(uri);

			remote.update(config);
			config.save();
			return remote;
		} catch (IOException | URISyntaxException e) {
			throw new JGitInternalException(e.getMessage()
		}

	}

}
