package org.eclipse.jgit.api;

import java.io.IOException;
import java.net.URISyntaxException;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.transport.RemoteConfig;

public class RemoteRemoveCommand extends GitCommand<RemoteConfig> {

	private String remoteName;

	protected RemoteRemoveCommand(Repository repo) {
		super(repo);
	}

	@Deprecated
	public void setName(String name) {
		this.remoteName = name;
	}

	public RemoteRemoveCommand setRemoteName(String remoteName) {
		this.remoteName = remoteName;
		return this;
	}

	@Override
	public RemoteConfig call() throws GitAPIException {
		checkCallable();

		try {
			StoredConfig config = repo.getConfig();
			RemoteConfig remote = new RemoteConfig(config
			config.unsetSection(ConfigConstants.CONFIG_KEY_REMOTE
			config.save();
			return remote;
		} catch (IOException | URISyntaxException e) {
			throw new JGitInternalException(e.getMessage()
		}

	}

}
