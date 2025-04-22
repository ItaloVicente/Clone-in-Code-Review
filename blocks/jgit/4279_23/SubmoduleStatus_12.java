package org.eclipse.jgit.submodule;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.AndTreeFilter;
import org.eclipse.jgit.treewalk.filter.TreeFilter;

public class SubmoduleGenerator {

	public static File getSubmoduleDirectory(final Repository parent
			final String path) {
		String normalized;
		if (File.separatorChar != '\\')
			normalized = path;
		else
			normalized = path.replace('/'
		return new File(parent.getWorkTree()
	}

	private final Repository repository;

	private final TreeWalk walk;

	private StoredConfig repoConfig;

	private FileBasedConfig modulesConfig;

	private String path;

	public SubmoduleGenerator(Repository repository) throws IOException {
		this(repository
	}

	public SubmoduleGenerator(final Repository repository
			final TreeFilter filter) throws IOException {
		this.repository = repository;

		walk = new TreeWalk(repository);
		if (filter != null)
			walk.setFilter(AndTreeFilter.create(new SubmoduleTreeFilter()
					filter));
		else
			walk.setFilter(new SubmoduleTreeFilter());
		reset();
	}

	private void loadConfig() throws IOException
		if (modulesConfig == null) {
			File modulesFile = new File(repository.getWorkTree()
					Constants.DOT_GIT_MODULES);
			FileBasedConfig config = new FileBasedConfig(modulesFile
					repository.getFS());
			config.load();
			modulesConfig = config;
		}
	}

	public SubmoduleGenerator reset() throws IOException {
		repoConfig = repository.getConfig();
		modulesConfig = null;
		walk.reset();
		walk.addTree(new DirCacheIterator(repository.readDirCache()));
		return this;
	}

	public File getDirectory() {
		if (path == null)
			return null;
		return getSubmoduleDirectory(repository
	}

	public File getGitDirectory() {
		if (path == null)
			return null;
		return new File(getDirectory()
	}

	public boolean next() throws IOException {
		boolean next = walk.next();
		path = next ? walk.getPathString() : null;
		return next;
	}

	public String getPath() {
		return path;
	}

	public ObjectId getObjectId() {
		return walk.getObjectId(0);
	}

	public String getModulesPath() throws IOException
		if (path == null)
			return null;

		loadConfig();
		return modulesConfig.getString(
				ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_PATH);
	}

	public String getConfigUrl() throws IOException
		if (path == null)
			return null;

		return repoConfig.getString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				path
	}

	public String getModulesUrl() throws IOException
		if (path == null)
			return null;

		loadConfig();
		return modulesConfig.getString(
				ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_URL);
	}

	public String getConfigUpdate() throws IOException
		if (path == null)
			return null;

		return repoConfig.getString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				path
	}

	public String getModulesUpdate() throws IOException
		if (path == null)
			return null;

		loadConfig();
		return modulesConfig.getString(
				ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_UPDATE);
	}

	public boolean hasGitDirectory() {
		File directory = getGitDirectory();
		return directory != null && directory.isDirectory();
	}

	public Repository getRepository() throws IOException {
		File directory = getGitDirectory();
		if (directory == null || !directory.isDirectory())
			return null;
		try {
			return Git.open(directory).getRepository();
		} catch (RepositoryNotFoundException e) {
			return null;
		}
	}
}
