package org.eclipse.jgit.lib;

import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_CORE_SECTION;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_BARE;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_WORKTREE;
import static org.eclipse.jgit.lib.Constants.DOT_GIT;
import static org.eclipse.jgit.lib.Constants.GIT_ALTERNATE_OBJECT_DIRECTORIES_KEY;
import static org.eclipse.jgit.lib.Constants.GIT_CEILING_DIRECTORIES_KEY;
import static org.eclipse.jgit.lib.Constants.GIT_DIR_KEY;
import static org.eclipse.jgit.lib.Constants.GIT_INDEX_KEY;
import static org.eclipse.jgit.lib.Constants.GIT_OBJECT_DIRECTORY_KEY;
import static org.eclipse.jgit.lib.Constants.GIT_WORK_TREE_KEY;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.lib.RepositoryCache.FileKey;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.SystemReader;

public class BaseRepositoryBuilder<B extends BaseRepositoryBuilder
	private FS fs;

	private File gitDir;

	private File objectDirectory;

	private List<File> alternateObjectDirectories;

	private File indexFile;

	private File workTree;

	private List<File> ceilingDirectories;

	private boolean bare;

	private Config config;

	public B setFS(FS fs) {
		this.fs = fs;
		return self();
	}

	public FS getFS() {
		return fs;
	}

	public B setGitDir(File gitDir) {
		this.gitDir = gitDir;
		this.config = null;
		return self();
	}

	public File getGitDir() {
		return gitDir;
	}

	public B setObjectDirectory(File objectDirectory) {
		this.objectDirectory = objectDirectory;
		return self();
	}

	public File getObjectDirectory() {
		return objectDirectory;
	}

	public B addAlternateObjectDirectory(File other) {
		if (other != null) {
			if (alternateObjectDirectories == null)
				alternateObjectDirectories = new LinkedList<File>();
			alternateObjectDirectories.add(other);
		}
		return self();
	}

	public B addAlternateObjectDirectories(Collection<File> inList) {
		if (inList != null) {
			for (File path : inList)
				addAlternateObjectDirectory(path);
		}
		return self();
	}

	public B addAlternateObjectDirectories(File[] inList) {
		if (inList != null) {
			for (File path : inList)
				addAlternateObjectDirectory(path);
		}
		return self();
	}

	public File[] getAlternateObjectDirectories() {
		final List<File> alts = alternateObjectDirectories;
		if (alts == null)
			return null;
		return alts.toArray(new File[alts.size()]);
	}

	public B setBare() {
		setIndexFile(null);
		setWorkTree(null);
		bare = true;
		return self();
	}

	public boolean isBare() {
		return bare;
	}

	public B setWorkTree(File workTree) {
		this.workTree = workTree;
		return self();
	}

	public File getWorkTree() {
		return workTree;
	}

	public B setIndexFile(File indexFile) {
		this.indexFile = indexFile;
		return self();
	}

	public File getIndexFile() {
		return indexFile;
	}

	public B readEnvironment() {
		return readEnvironment(SystemReader.getInstance());
	}

	public B readEnvironment(SystemReader sr) {
		if (getGitDir() == null) {
			String val = sr.getenv(GIT_DIR_KEY);
			if (val != null)
				setGitDir(new File(val));
		}

		if (getObjectDirectory() == null) {
			String val = sr.getenv(GIT_OBJECT_DIRECTORY_KEY);
			if (val != null)
				setObjectDirectory(new File(val));
		}

		if (getAlternateObjectDirectories() == null) {
			String val = sr.getenv(GIT_ALTERNATE_OBJECT_DIRECTORIES_KEY);
			if (val != null) {
				for (String path : val.split(File.pathSeparator))
					addAlternateObjectDirectory(new File(path));
			}
		}

		if (getWorkTree() == null) {
			String val = sr.getenv(GIT_WORK_TREE_KEY);
			if (val != null)
				setWorkTree(new File(val));
		}

		if (getIndexFile() == null) {
			String val = sr.getenv(GIT_INDEX_KEY);
			if (val != null)
				setIndexFile(new File(val));
		}

		if (ceilingDirectories == null) {
			String val = sr.getenv(GIT_CEILING_DIRECTORIES_KEY);
			if (val != null) {
				for (String path : val.split(File.pathSeparator))
					addCeilingDirectory(new File(path));
			}
		}

		return self();
	}

	public B addCeilingDirectory(File root) {
		if (root != null) {
			if (ceilingDirectories == null)
				ceilingDirectories = new LinkedList<File>();
			ceilingDirectories.add(root);
		}
		return self();
	}

	public B addCeilingDirectories(Collection<File> inList) {
		if (inList != null) {
			for (File path : inList)
				addCeilingDirectory(path);
		}
		return self();
	}

	public B addCeilingDirectories(File[] inList) {
		if (inList != null) {
			for (File path : inList)
				addCeilingDirectory(path);
		}
		return self();
	}

	public B findGitDir() {
		if (getGitDir() == null)
			findGitDir(new File("").getAbsoluteFile());
		return self();
	}

	public B findGitDir(File current) {
		if (getGitDir() == null) {
			FS tryFS = safeFS();
			while (current != null) {
				File dir = new File(current
				if (FileKey.isGitRepository(dir
					setGitDir(dir);
					break;
				}

				current = current.getParentFile();
				if (current != null && ceilingDirectories.contains(current))
					break;
			}
		}
		return self();
	}

	public B setup() throws IllegalArgumentException
		requireGitDirOrWorkTree();
		setupGitDir();
		setupWorkTree();
		setupInternals();
		return self();
	}

	@SuppressWarnings("unchecked")
	public R build() throws IOException {
		return (R) new FileRepository(setup());
	}

	protected void requireGitDirOrWorkTree() {
		if (getGitDir() == null && getWorkTree() == null)
			throw new IllegalArgumentException(
					JGitText.get().eitherGIT_DIRorGIT_WORK_TREEmustBePassed);
	}

	protected void setupGitDir() throws IOException {
		if (getGitDir() == null && getWorkTree() != null)
			setGitDir(new File(getWorkTree()
	}

	protected void setupWorkTree() throws IOException {
		if (getFS() == null)
			setFS(FS.DETECTED);

		if (!isBare() && getWorkTree() == null)
			setWorkTree(guessWorkTreeOrFail());

		if (!isBare()) {
			if (getGitDir() == null)
				setGitDir(getWorkTree().getParentFile());
			if (getIndexFile() == null)
				setIndexFile(new File(getGitDir()
		}
	}

	protected void setupInternals() throws IOException {
		if (getObjectDirectory() == null && getGitDir() != null)
			setObjectDirectory(safeFS().resolve(getGitDir()
	}

	protected Config getConfig() throws IOException {
		if (config == null)
			config = loadConfig();
		return config;
	}

	protected Config loadConfig() throws IOException {
		if (getGitDir() != null) {
			File path = safeFS().resolve(getGitDir()
			FileBasedConfig cfg = new FileBasedConfig(path);
			try {
				cfg.load();
			} catch (ConfigInvalidException err) {
				throw new IllegalArgumentException(MessageFormat.format(
						JGitText.get().repositoryConfigFileInvalid
								.getAbsolutePath()
			}
			return cfg;
		} else {
			return new Config();
		}
	}

	private File guessWorkTreeOrFail() throws IOException {
		final Config cfg = getConfig();

		String path = cfg.getString(CONFIG_CORE_SECTION
				CONFIG_KEY_WORKTREE);
		if (path != null)
			return safeFS().resolve(getGitDir()

		if (cfg.getString(CONFIG_CORE_SECTION
			if (cfg.getBoolean(CONFIG_CORE_SECTION
				setBare();
				return null;
			}
			return getGitDir().getParentFile();
		}

		if (getGitDir().getName().equals(DOT_GIT)) {
			return getGitDir().getParentFile();
		}

		setBare();
		return null;
	}

	protected FS safeFS() {
		return getFS() != null ? getFS() : FS.DETECTED;
	}

	@SuppressWarnings("unchecked")
	protected final B self() {
		return (B) this;
	}
}
