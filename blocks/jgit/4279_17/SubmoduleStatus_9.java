package org.eclipse.jgit.submodule;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.TreeFilter;

public class SubmoduleGenerator {

	private final Repository repository;

	private final TreeWalk walk;

	private final StoredConfig repoConfig;

	private FileBasedConfig modulesConfig;

	private String path;

	private ObjectId id;

	public SubmoduleGenerator(Repository repository) throws IOException {
		this(repository
	}

	public SubmoduleGenerator(Repository repository
			throws IOException {
		this.repository = repository;
		this.repoConfig = repository.getConfig();

		walk = new TreeWalk(repository);
		walk.setFilter(filter);
		walk.setRecursive(true);
		walk.addTree(new DirCacheIterator(repository.readDirCache()));
	}

	private void loadConfig() throws IOException
		if (modulesConfig == null) {
			File modules = new File(repository.getWorkTree()
					Constants.DOT_GIT_MODULES);
			FileBasedConfig config = new FileBasedConfig(modules
					repository.getFS());
			config.load();
			modulesConfig = config;
		}
	}

	public File getDirectory() {
		if (path == null)
			return null;
		String normalized;
		if (File.separatorChar != '\\')
			normalized = path;
		else
			normalized = path.replace('/'
		return new File(repository.getWorkTree()
	}

	public File getGitDirectory() {
		if (path == null)
			return null;
		return new File(getDirectory()
	}

	public boolean next() throws IOException {
		while (walk.next()) {
			if (FileMode.GITLINK != walk.getFileMode(0))
				continue;

			path = walk.getPathString();
			id = walk.getObjectId(0);
			return true;
		}
		path = null;
		id = null;
		return false;
	}

	public String getPath() {
		return path;
	}

	public ObjectId getObjectId() {
		return id;
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
