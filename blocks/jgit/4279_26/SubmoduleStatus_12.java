package org.eclipse.jgit.submodule;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.eclipse.jgit.treewalk.filter.TreeFilter;

public class SubmoduleGenerator {

	public static SubmoduleGenerator forIndex(Repository repository)
			throws IOException {
		SubmoduleGenerator generator = new SubmoduleGenerator(repository);
		generator.setTree(new DirCacheIterator(repository.readDirCache()));
		return generator;
	}

	public static SubmoduleGenerator forIndexPath(Repository repository
			String path) throws IOException {
		SubmoduleGenerator generator = new SubmoduleGenerator(repository);
		generator.setTree(new DirCacheIterator(repository.readDirCache()));
		generator.setFilter(PathFilter.create(path));
		return generator.next() ? generator : null;
	}

	public static SubmoduleGenerator forTree(Repository repository
			AnyObjectId treeId) throws IOException {
		SubmoduleGenerator generator = new SubmoduleGenerator(repository);
		generator.setTree(treeId);
		return generator;
	}

	public static SubmoduleGenerator forTreePath(Repository repository
			AnyObjectId treeId
		SubmoduleGenerator generator = new SubmoduleGenerator(repository);
		generator.setTree(treeId);
		generator.setFilter(PathFilter.create(path));
		return generator.next() ? generator : null;
	}

	public static File getSubmoduleDirectory(final Repository parent
			final String path) {
		return new File(parent.getWorkTree()
	}

	public static Repository getSubmoduleRepository(final Repository parent
			final String path) throws IOException {
		File directory = getSubmoduleGitDirectory(parent
		if (directory == null || !directory.isDirectory())
			return null;
		try {
			return Git.open(directory).getRepository();
		} catch (RepositoryNotFoundException e) {
			return null;
		}
	}

	public static File getSubmoduleGitDirectory(final Repository parent
			final String path) {
		return new File(getSubmoduleDirectory(parent
	}

	private final Repository repository;

	private final TreeWalk walk;

	private StoredConfig repoConfig;

	private FileBasedConfig modulesConfig;

	private String path;

	public SubmoduleGenerator(final Repository repository) throws IOException {
		this.repository = repository;
		repoConfig = repository.getConfig();
		walk = new TreeWalk(repository);
		walk.setRecursive(true);
	}

	private void loadModulesConfig() throws IOException
		if (modulesConfig == null) {
			File modulesFile = new File(repository.getWorkTree()
					Constants.DOT_GIT_MODULES);
			FileBasedConfig config = new FileBasedConfig(modulesFile
					repository.getFS());
			config.load();
			modulesConfig = config;
		}
	}

	public SubmoduleGenerator setFilter(TreeFilter filter) {
		walk.setFilter(filter);
		return this;
	}

	public SubmoduleGenerator setTree(final AbstractTreeIterator iterator)
			throws CorruptObjectException {
		walk.addTree(iterator);
		return this;
	}

	public SubmoduleGenerator setTree(final AnyObjectId treeId)
			throws IOException {
		walk.addTree(treeId);
		return this;
	}

	public SubmoduleGenerator reset() {
		repoConfig = repository.getConfig();
		modulesConfig = null;
		walk.reset();
		return this;
	}

	public File getDirectory() {
		return getSubmoduleDirectory(repository
	}

	public File getGitDirectory() {
		return getSubmoduleGitDirectory(repository
	}

	public boolean next() throws IOException {
		while (walk.next()) {
			if (FileMode.GITLINK != walk.getFileMode(0))
				continue;
			path = walk.getPathString();
			return true;
		}
		path = null;
		return false;
	}

	public String getPath() {
		return path;
	}

	public ObjectId getObjectId() {
		return walk.getObjectId(0);
	}

	public String getModulesPath() throws IOException
		loadModulesConfig();
		return modulesConfig.getString(
				ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_PATH);
	}

	public String getConfigUrl() throws IOException
		return repoConfig.getString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				path
	}

	public String getModulesUrl() throws IOException
		loadModulesConfig();
		return modulesConfig.getString(
				ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_URL);
	}

	public String getConfigUpdate() throws IOException
		return repoConfig.getString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				path
	}

	public String getModulesUpdate() throws IOException
		loadModulesConfig();
		return modulesConfig.getString(
				ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_UPDATE);
	}

	public boolean hasGitDirectory() {
		File directory = getGitDirectory();
		return directory != null && directory.isDirectory();
	}

	public Repository getRepository() throws IOException {
		return getSubmoduleRepository(repository
	}

	public ObjectId getHead() throws IOException {
		Repository subRepo = getRepository();
		return subRepo != null ? subRepo.resolve(Constants.HEAD) : null;
	}

	public String getHeadBranch() throws IOException {
		Repository subRepo = getRepository();
		if (subRepo == null)
			return null;
		Ref head = subRepo.getRef(Constants.HEAD);
		if (head != null && head.isSymbolic())
			return Repository.shortenRefName(head.getLeaf().getName());
		else
			return null;
	}
}
