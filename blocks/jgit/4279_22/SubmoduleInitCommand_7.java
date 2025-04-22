package org.eclipse.jgit.api;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.submodule.SubmoduleGenerator;
import org.eclipse.jgit.transport.CredentialsProvider;

public class SubmoduleAddCommand extends GitCommand<Repository> {

	private String path;

	private String uri;

	private ProgressMonitor monitor = NullProgressMonitor.INSTANCE;

	private CredentialsProvider credentialsProvider;

	public SubmoduleAddCommand(Repository repo) {
		super(repo);
	}

	public SubmoduleAddCommand setPath(String path) {
		this.path = path;
		return this;
	}

	public SubmoduleAddCommand setURI(String uri) {
		this.uri = uri;
		return this;
	}

	public SubmoduleAddCommand setProgressMonitor(ProgressMonitor monitor) {
		this.monitor = monitor;
		return this;
	}

	public SubmoduleAddCommand setCredentialsProvider(
			CredentialsProvider credentialsProvider) {
		this.credentialsProvider = credentialsProvider;
		return this;
	}

	public Repository call() throws JGitInternalException {
		checkCallable();
		if (path == null || path.length() == 0)
			throw new IllegalArgumentException(JGitText.get().pathNotConfigured);
		if (uri == null || uri.length() == 0)
			throw new IllegalArgumentException(JGitText.get().uriNotConfigured);

		File moduleDirectory = SubmoduleGenerator.getSubmoduleDirectory(repo
				path);
		CloneCommand clone = Git.cloneRepository();
		clone.setDirectory(moduleDirectory);
		clone.setURI(uri);
		clone.setProgressMonitor(monitor);
		clone.setCredentialsProvider(credentialsProvider);

		Repository subRepo = clone.call().getRepository();

		StoredConfig config = repo.getConfig();
		config.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_URL
		try {
			config.save();
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		}

		FileBasedConfig modulesConfig = new FileBasedConfig(new File(
				repo.getWorkTree()
		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_PATH
		modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_URL
		try {
			modulesConfig.save();
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		}

		AddCommand add = new AddCommand(repo);
		add.addFilepattern(Constants.DOT_GIT_MODULES);
		add.addFilepattern(path);
		try {
			add.call();
		} catch (NoFilepatternException e) {
			throw new JGitInternalException(e.getMessage()
		}

		return subRepo;
	}
}
