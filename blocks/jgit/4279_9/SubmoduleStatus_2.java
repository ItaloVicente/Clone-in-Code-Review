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
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.treewalk.TreeWalk;

public class SubmoduleGenerator {

	private final Repository repository;

	private final TreeWalk walk;

	private FileBasedConfig config;

	private String path;

	private ObjectId id;

	public SubmoduleGenerator(Repository repository) throws IOException {
		this.repository = repository;

		walk = new TreeWalk(repository);
		walk.setRecursive(true);
		walk.addTree(new DirCacheIterator(repository.readDirCache()));
	}

	private void loadConfig() throws IOException
		if (config == null) {
			File modules = new File(repository.getWorkTree()
					Constants.DOT_GIT_MODULES);
			FileBasedConfig modulesConfig = new FileBasedConfig(modules
					repository.getFS());
			modulesConfig.load();
			config = modulesConfig;
		}
	}

	private File getSubmoduleGitDir() {
		String normalized;
		if (File.separatorChar != '\\')
			normalized = path;
		else
			normalized = path.replace('/'
		String repoPath = normalized + File.separatorChar + Constants.DOT_GIT;
		return new File(repository.getWorkTree()
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

	public String getConfigPath() throws IOException
		if (path == null)
			return null;

		loadConfig();
		return config.getString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_PATH);
	}

	public String getConfigUrl() throws IOException
		if (path == null)
			return null;

		loadConfig();
		return config.getString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_URL);
	}

	public boolean hasGitDirectory() {
		return path != null ? getSubmoduleGitDir().isDirectory() : false;
	}

	public Repository getRepository() throws IOException {
		if (path == null)
			return null;

		File directory = getSubmoduleGitDir();
		if (!directory.isDirectory())
			return null;
		try {
			return Git.open(directory).getRepository();
		} catch (RepositoryNotFoundException e) {
			return null;
		}
	}
}
