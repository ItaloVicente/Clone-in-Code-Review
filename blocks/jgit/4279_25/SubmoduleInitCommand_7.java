package org.eclipse.jgit.api;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

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
import org.eclipse.jgit.treewalk.filter.PathFilter;

public class SubmoduleAddCommand extends GitCommand<Repository> {

	private String path;

	private String uri;

	private ProgressMonitor monitor;

	private CredentialsProvider credentialsProvider;

	public SubmoduleAddCommand(final Repository repo) {
		super(repo);
	}

	public SubmoduleAddCommand setPath(final String path) {
		this.path = path;
		return this;
	}

	public SubmoduleAddCommand setURI(final String uri) {
		this.uri = uri;
		return this;
	}

	public SubmoduleAddCommand setProgressMonitor(final ProgressMonitor monitor) {
		this.monitor = monitor;
		return this;
	}

	public SubmoduleAddCommand setCredentialsProvider(
			final CredentialsProvider credentialsProvider) {
		this.credentialsProvider = credentialsProvider;
		return this;
	}

	protected boolean submoduleExists() throws IOException {
		SubmoduleGenerator generator = new SubmoduleGenerator(repo
				PathFilter.create(path));
		return generator.next();
	}

	public Repository call() throws JGitInternalException {
		checkCallable();
		if (path == null || path.length() == 0)
			throw new IllegalArgumentException(JGitText.get().pathNotConfigured);
		if (uri == null || uri.length() == 0)
			throw new IllegalArgumentException(JGitText.get().uriNotConfigured);

		try {
			if (submoduleExists())
				throw new JGitInternalException(MessageFormat.format(
						JGitText.get().submoduleExists
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		}

		File moduleDirectory = SubmoduleGenerator.getSubmoduleDirectory(repo
				path);
		CloneCommand clone = Git.cloneRepository();
		clone.setDirectory(moduleDirectory);
		clone.setURI(uri);
		if (monitor != null)
			clone.setProgressMonitor(monitor);
		if (credentialsProvider != null)
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
