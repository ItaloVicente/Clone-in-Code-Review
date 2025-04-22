package org.eclipse.jgit.api;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.URIish;

public class RemoteSetUrlCommand extends GitCommand<RemoteConfig> {

	private String name;

	private URIish uri;

	private boolean push;

	protected RemoteSetUrlCommand(Repository repo) {
		super(repo);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUri(URIish uri) {
		this.uri = uri;
	}

	public void setPush(boolean push) {
		this.push = push;
	}

	@Override
	public RemoteConfig call() throws GitAPIException {
		checkCallable();

		try {
			StoredConfig config = repo.getConfig();
			RemoteConfig remote = new RemoteConfig(config
			if (push) {
				List<URIish> uris = remote.getPushURIs();
				if (uris.size() > 1) {
					throw new JGitInternalException(
				} else if (uris.size() == 1) {
					remote.removePushURI(uris.get(0));
				}
				remote.addPushURI(uri);
			} else {
				List<URIish> uris = remote.getURIs();
				if (uris.size() > 1) {
					throw new JGitInternalException(
				} else if (uris.size() == 1) {
					remote.removeURI(uris.get(0));
				}
				remote.addURI(uri);
			}

			remote.update(config);
			config.save();
			return remote;
		} catch (IOException | URISyntaxException e) {
			throw new JGitInternalException(e.getMessage()
		}
	}

}
