package org.eclipse.jgit.submodule;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.BlobBasedConfig;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.eclipse.jgit.util.FS;

public class SubmoduleWalk implements AutoCloseable {

	public enum IgnoreSubmoduleMode {
		ALL

		DIRTY

		UNTRACKED

		NONE;
	}

	public static SubmoduleWalk forIndex(Repository repository)
			throws IOException {
		SubmoduleWalk generator = new SubmoduleWalk(repository);
		try {
			DirCache index = repository.readDirCache();
			generator.setTree(new DirCacheIterator(index));
		} catch (IOException e) {
			generator.close();
			throw e;
		}
		return generator;
	}

	public static SubmoduleWalk forPath(Repository repository
			AnyObjectId treeId
		SubmoduleWalk generator = new SubmoduleWalk(repository);
		try {
			generator.setTree(treeId);
			PathFilter filter = PathFilter.create(path);
			generator.setFilter(filter);
			generator.setRootTree(treeId);
			while (generator.next())
				if (filter.isDone(generator.walk))
					return generator;
		} catch (IOException e) {
			generator.close();
			throw e;
		}
		generator.close();
		return null;
	}

	public static SubmoduleWalk forPath(Repository repository
			AbstractTreeIterator iterator
		SubmoduleWalk generator = new SubmoduleWalk(repository);
		try {
			generator.setTree(iterator);
			PathFilter filter = PathFilter.create(path);
			generator.setFilter(filter);
			generator.setRootTree(iterator);
			while (generator.next())
				if (filter.isDone(generator.walk))
					return generator;
		} catch (IOException e) {
			generator.close();
			throw e;
		}
		generator.close();
		return null;
	}

	public static File getSubmoduleDirectory(final Repository parent
			final String path) {
		return new File(parent.getWorkTree()
	}

	public static Repository getSubmoduleRepository(final Repository parent
			final String path) throws IOException {
		return getSubmoduleRepository(parent.getWorkTree()
				parent.getFS());
	}

	public static Repository getSubmoduleRepository(final File parent
			final String path) throws IOException {
		return getSubmoduleRepository(parent
	}

	public static Repository getSubmoduleRepository(final File parent
			final String path
		File subWorkTree = new File(parent
		if (!subWorkTree.isDirectory())
			return null;
		File workTree = new File(parent
		try {
					.build();
		} catch (RepositoryNotFoundException e) {
			return null;
		}
	}

	public static String getSubmoduleRemoteUrl(final Repository parent
			final String url) throws IOException {
			return url;

		String remoteName = null;
		Ref ref = parent.exactRef(Constants.HEAD);
		if (ref != null) {
			if (ref.isSymbolic())
				ref = ref.getLeaf();
			remoteName = parent.getConfig().getString(
					ConfigConstants.CONFIG_BRANCH_SECTION
					Repository.shortenRefName(ref.getName())
					ConfigConstants.CONFIG_KEY_REMOTE);
		}

		if (remoteName == null)
			remoteName = Constants.DEFAULT_REMOTE_NAME;

		String remoteUrl = parent.getConfig().getString(
				ConfigConstants.CONFIG_REMOTE_SECTION
				ConfigConstants.CONFIG_KEY_URL);

		if (remoteUrl == null) {
			remoteUrl = parent.getWorkTree().getAbsolutePath();
			if ('\\' == File.separatorChar)
				remoteUrl = remoteUrl.replace('\\'
		}

		if (remoteUrl.charAt(remoteUrl.length() - 1) == '/')
			remoteUrl = remoteUrl.substring(0

		char separator = '/';
		String submoduleUrl = url;
		while (submoduleUrl.length() > 0) {
				submoduleUrl = submoduleUrl.substring(2);
				int lastSeparator = remoteUrl.lastIndexOf('/');
				if (lastSeparator < 1) {
					lastSeparator = remoteUrl.lastIndexOf(':');
					separator = ':';
				}
				if (lastSeparator < 1)
					throw new IOException(MessageFormat.format(
							JGitText.get().submoduleParentRemoteUrlInvalid
							remoteUrl));
				remoteUrl = remoteUrl.substring(0
				submoduleUrl = submoduleUrl.substring(3);
			} else
				break;
		}
		return remoteUrl + separator + submoduleUrl;
	}

	private final Repository repository;

	private final TreeWalk walk;

	private StoredConfig repoConfig;

	private AbstractTreeIterator rootTree;

	private Config modulesConfig;

	private String path;

	private Map<String

	public SubmoduleWalk(Repository repository) throws IOException {
		this.repository = repository;
		repoConfig = repository.getConfig();
		walk = new TreeWalk(repository);
		walk.setRecursive(true);
	}

	public SubmoduleWalk setModulesConfig(Config config) {
		modulesConfig = config;
		loadPathNames();
		return this;
	}

	public SubmoduleWalk setRootTree(AbstractTreeIterator tree) {
		rootTree = tree;
		modulesConfig = null;
		pathToName = null;
		return this;
	}

	public SubmoduleWalk setRootTree(AnyObjectId id) throws IOException {
		final CanonicalTreeParser p = new CanonicalTreeParser();
		p.reset(walk.getObjectReader()
		rootTree = p;
		modulesConfig = null;
		pathToName = null;
		return this;
	}

	public SubmoduleWalk loadModulesConfig() throws IOException
		if (rootTree == null) {
			File modulesFile = new File(repository.getWorkTree()
					Constants.DOT_GIT_MODULES);
			FileBasedConfig config = new FileBasedConfig(modulesFile
					repository.getFS());
			config.load();
			modulesConfig = config;
			loadPathNames();
		} else {
			try (TreeWalk configWalk = new TreeWalk(repository)) {
				configWalk.addTree(rootTree);

				int idx;
				for (idx = 0; !rootTree.first(); idx++) {
					rootTree.back(1);
				}

				try {
					configWalk.setRecursive(false);
					PathFilter filter = PathFilter.create(Constants.DOT_GIT_MODULES);
					configWalk.setFilter(filter);
					while (configWalk.next()) {
						if (filter.isDone(configWalk)) {
							modulesConfig = new BlobBasedConfig(null
									configWalk.getObjectId(0));
							loadPathNames();
							return this;
						}
					}
					modulesConfig = new Config();
					pathToName = null;
				} finally {
					if (idx > 0)
						rootTree.next(idx);
				}
			}
		}
		return this;
	}

	private void loadPathNames() {
		pathToName = null;
		if (modulesConfig != null) {
			HashMap<String
			for (String name : modulesConfig
					.getSubsections(ConfigConstants.CONFIG_SUBMODULE_SECTION)) {
				pathNames.put(modulesConfig.getString(
						ConfigConstants.CONFIG_SUBMODULE_SECTION
						ConfigConstants.CONFIG_KEY_PATH)
			}
			pathToName = pathNames;
		}
	}

	public static boolean containsGitModulesFile(Repository repository)
			throws IOException {
		if (repository.isBare()) {
			return false;
		}
		File modulesFile = new File(repository.getWorkTree()
				Constants.DOT_GIT_MODULES);
		return (modulesFile.exists());
	}

	private void lazyLoadModulesConfig() throws IOException
		if (modulesConfig == null) {
			loadModulesConfig();
		}
	}

	private String getModuleName(String modulePath) {
		String name = pathToName != null ? pathToName.get(modulePath) : null;
		return name != null ? name : modulePath;
	}

	public SubmoduleWalk setFilter(TreeFilter filter) {
		walk.setFilter(filter);
		return this;
	}

	public SubmoduleWalk setTree(AbstractTreeIterator iterator)
			throws CorruptObjectException {
		walk.addTree(iterator);
		return this;
	}

	public SubmoduleWalk setTree(AnyObjectId treeId) throws IOException {
		walk.addTree(treeId);
		return this;
	}

	public SubmoduleWalk reset() {
		repoConfig = repository.getConfig();
		modulesConfig = null;
		pathToName = null;
		walk.reset();
		return this;
	}

	public File getDirectory() {
		return getSubmoduleDirectory(repository
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

	public String getModuleName() {
		return getModuleName(path);
	}

	public ObjectId getObjectId() {
		return walk.getObjectId(0);
	}

	public String getModulesPath() throws IOException
		lazyLoadModulesConfig();
		return modulesConfig.getString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				getModuleName()
	}

	public String getConfigUrl() throws IOException
		return repoConfig.getString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				getModuleName()
	}

	public String getModulesUrl() throws IOException
		lazyLoadModulesConfig();
		return modulesConfig.getString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				getModuleName()
	}

	public String getConfigUpdate() throws IOException
		return repoConfig.getString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				getModuleName()
	}

	public String getModulesUpdate() throws IOException
		lazyLoadModulesConfig();
		return modulesConfig.getString(ConfigConstants.CONFIG_SUBMODULE_SECTION
				getModuleName()
	}

	public IgnoreSubmoduleMode getModulesIgnore() throws IOException
			ConfigInvalidException {
		lazyLoadModulesConfig();
		return modulesConfig.getEnum(IgnoreSubmoduleMode.values()
				ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_IGNORE
	}

	public Repository getRepository() throws IOException {
		return getSubmoduleRepository(repository
	}

	public ObjectId getHead() throws IOException {
		try (Repository subRepo = getRepository()) {
			if (subRepo == null) {
				return null;
			}
			return subRepo.resolve(Constants.HEAD);
		}
	}

	public String getHeadRef() throws IOException {
		try (Repository subRepo = getRepository()) {
			if (subRepo == null) {
				return null;
			}
			Ref head = subRepo.exactRef(Constants.HEAD);
			return head != null ? head.getLeaf().getName() : null;
		}
	}

	public String getRemoteUrl() throws IOException
		String url = getModulesUrl();
		return url != null ? getSubmoduleRemoteUrl(repository
	}

	@Override
	public void close() {
		walk.close();
	}
}
