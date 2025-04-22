package org.eclipse.jgit.api;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.submodule.SubmoduleValidator;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.submodule.SubmoduleWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.eclipse.jgit.treewalk.filter.TreeFilter;

public class SubmoduleAddCommand extends
		TransportCommand<SubmoduleAddCommand

	private String name;

	private String path;

	private String uri;

	private ProgressMonitor monitor;

	public SubmoduleAddCommand(Repository repo) {
		super(repo);
	}

	public SubmoduleAddCommand setName(String name) {
		this.name = name;
		return this;
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

	protected boolean submoduleExists() throws IOException {
		TreeFilter filter = PathFilter.create(path);
		try (SubmoduleWalk w = SubmoduleWalk.forIndex(repo)) {
			return w.setFilter(filter).next();
		}
	}

	@Override
	public Repository call() throws GitAPIException {
		checkCallable();
		if (path == null || path.length() == 0)
			throw new IllegalArgumentException(JGitText.get().pathNotConfigured);
		if (uri == null || uri.length() == 0)
			throw new IllegalArgumentException(JGitText.get().uriNotConfigured);
		if (name == null || name.length() == 0) {
			name = path;
		}

		try {
			SubmoduleValidator.assertValidSubmoduleName(name);
			SubmoduleValidator.assertValidSubmodulePath(path);
			SubmoduleValidator.assertValidSubmoduleUri(uri);
		} catch (SubmoduleValidator.SubmoduleValidationException e) {
			throw new IllegalArgumentException(e.getMessage());
		}

		try {
			if (submoduleExists())
				throw new JGitInternalException(MessageFormat.format(
						JGitText.get().submoduleExists
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		}

		final String resolvedUri;
		try {
			resolvedUri = SubmoduleWalk.getSubmoduleRemoteUrl(repo
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		}
		File moduleDirectory = SubmoduleWalk.getSubmoduleDirectory(repo
		CloneCommand clone = Git.cloneRepository();
		configure(clone);
		clone.setDirectory(moduleDirectory);
		clone.setGitDir(new File(new File(repo.getDirectory()
				Constants.MODULES)
		clone.setURI(resolvedUri);
		if (monitor != null)
			clone.setProgressMonitor(monitor);
		Repository subRepo = null;
		try (Git git = clone.call()) {
			subRepo = git.getRepository();
			subRepo.incrementOpen();
		}

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
		try {
			modulesConfig.load();
			modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
					name
			modulesConfig.setString(ConfigConstants.CONFIG_SUBMODULE_SECTION
					name
			modulesConfig.save();
		} catch (IOException | ConfigInvalidException e) {
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
