
package org.eclipse.jgit.lib;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.RawParseUtils;
import org.eclipse.jgit.util.SystemReader;

public class FileRepository extends Repository {
	private final AtomicInteger useCnt = new AtomicInteger(1);

	private final File gitDir;

	private final FS fs;

	private final FileBasedConfig userConfig;

	private final RepositoryConfig config;

	private final RefDatabase refs;

	private final ObjectDirectory objectDatabase;

	private GitIndex index;


	private File workDir;

	private File indexFile;

	public FileRepository(final File d) throws IOException {
		this(d
	}

	public FileRepository(final File d
		this(d
	}

	public FileRepository(final File d
			final File[] alternateObjectDir
		this(d
	}

	public FileRepository(final File d
			final File[] alternateObjectDir
			throws IOException {

		if (workTree != null) {
			workDir = workTree;
			if (d == null)
				gitDir = new File(workTree
			else
				gitDir = d;
		} else {
			if (d != null)
				gitDir = d;
			else
				throw new IllegalArgumentException(
						JGitText.get().eitherGIT_DIRorGIT_WORK_TREEmustBePassed);
		}

		this.fs = fs;

		userConfig = SystemReader.getInstance().openUserConfig(fs);
		config = new RepositoryConfig(userConfig

		loadUserConfig();
		loadConfig();

		if (workDir == null) {
			String workTreeConfig = getConfig().getString(
					ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_WORKTREE);
			if (workTreeConfig != null) {
				workDir = fs.resolve(d
			} else if (getConfig().getString(
					ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_BARE) != null) {
				if (!getConfig().getBoolean(ConfigConstants.CONFIG_CORE_SECTION
						ConfigConstants.CONFIG_KEY_BARE
					workDir = gitDir.getParentFile();
				else
					workDir = null;
			} else if (Constants.DOT_GIT.equals(gitDir.getName())) {
				workDir = gitDir.getParentFile();
			} else {
				workDir = null;
			}
		}

		refs = new RefDirectory(this);
		if (objectDir != null)
			objectDatabase = new ObjectDirectory(fs.resolve(objectDir
					alternateObjectDir
		else
			objectDatabase = new ObjectDirectory(fs.resolve(gitDir
					alternateObjectDir

		if (indexFile != null)
			this.indexFile = indexFile;
		else
			this.indexFile = new File(gitDir

		if (objectDatabase.exists()) {
			final String repositoryFormatVersion = getConfig().getString(
					ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_REPO_FORMAT_VERSION);
			if (!"0".equals(repositoryFormatVersion)) {
				throw new IOException(MessageFormat.format(
						JGitText.get().unknownRepositoryFormat2
						repositoryFormatVersion));
			}
		}
	}

	private void loadUserConfig() throws IOException {
		try {
			userConfig.load();
		} catch (ConfigInvalidException e1) {
			IOException e2 = new IOException(MessageFormat.format(JGitText
					.get().userConfigFileInvalid
					.getAbsolutePath()
			e2.initCause(e1);
			throw e2;
		}
	}

	private void loadConfig() throws IOException {
		try {
			config.load();
		} catch (ConfigInvalidException e1) {
			IOException e2 = new IOException(JGitText.get().unknownRepositoryFormat);
			e2.initCause(e1);
			throw e2;
		}
	}

	public void create(boolean bare) throws IOException {
		final RepositoryConfig cfg = getConfig();
		if (cfg.getFile().exists()) {
			throw new IllegalStateException(MessageFormat.format(
					JGitText.get().repositoryAlreadyExists
		}
		gitDir.mkdirs();
		refs.create();
		objectDatabase.create();

		new File(gitDir

		RefUpdate head = updateRef(Constants.HEAD);
		head.disableRefLog();
		head.link(Constants.R_HEADS + Constants.MASTER);

		cfg.setInt(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_REPO_FORMAT_VERSION
		cfg.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_FILEMODE
		if (bare)
			cfg.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_BARE
		cfg.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_LOGALLREFUPDATES
		cfg.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_AUTOCRLF
		cfg.save();
	}

	public File getDirectory() {
		return gitDir;
	}

	public File getObjectsDirectory() {
		return objectDatabase.getDirectory();
	}

	public ObjectDatabase getObjectDatabase() {
		return objectDatabase;
	}

	public RefDatabase getRefDatabase() {
		return refs;
	}

	public RepositoryConfig getConfig() {
		if (userConfig.isOutdated()) {
			try {
				loadUserConfig();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		if (config.isOutdated()) {
				try {
					loadConfig();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
		}
		return config;
	}

	public FS getFS() {
		return fs;
	}

	public File toFile(final AnyObjectId objectId) {
		return objectDatabase.fileFor(objectId);
	}

	public Collection<PackedObjectLoader> openObjectInAllPacks(
			final AnyObjectId objectId
			throws IOException {
		Collection<PackedObjectLoader> result = new LinkedList<PackedObjectLoader>();
		openObjectInAllPacks(objectId
		return result;
	}

	void openObjectInAllPacks(final AnyObjectId objectId
			final Collection<PackedObjectLoader> resultLoaders
			final WindowCursor curs) throws IOException {
		objectDatabase.openObjectInAllPacks(resultLoaders
	}

	public void incrementOpen() {
		useCnt.incrementAndGet();
	}

	public void close() {
		if (useCnt.decrementAndGet() == 0) {
			objectDatabase.close();
			refs.close();
		}
	}

	public void openPack(final File pack
		objectDatabase.openPack(pack
	}

	public String toString() {
		return "Repository[" + getDirectory() + "]";
	}

	public GitIndex getIndex() throws IOException
		if (isBare())
			throw new IllegalStateException(
					JGitText.get().bareRepositoryNoWorkdirAndIndex);
		if (index == null) {
			index = new GitIndex(this);
			index.read();
		} else {
			index.rereadIfNecessary();
		}
		return index;
	}

	public File getIndexFile() throws IllegalStateException {
		if (isBare())
			throw new IllegalStateException(
					JGitText.get().bareRepositoryNoWorkdirAndIndex);
		return indexFile;
	}

	public RepositoryState getRepositoryState() {
		if (new File(getWorkDir()
			return RepositoryState.REBASING;
		if (new File(gitDir
			return RepositoryState.REBASING_INTERACTIVE;

		if (new File(getDirectory()
			return RepositoryState.REBASING_REBASING;
		if (new File(getDirectory()
			return RepositoryState.APPLY;
		if (new File(getDirectory()
			return RepositoryState.REBASING;

		if (new File(getDirectory()
			return RepositoryState.REBASING_INTERACTIVE;
		if (new File(getDirectory()
			return RepositoryState.REBASING_MERGE;

		if (new File(gitDir
			try {
				if (!DirCache.read(this).hasUnmergedPaths()) {
					return RepositoryState.MERGING_RESOLVED;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return RepositoryState.MERGING;
		}

		if (new File(gitDir
			return RepositoryState.BISECTING;

		return RepositoryState.SAFE;
	}

	public boolean isBare() {
		return workDir == null;
	}

	public File getWorkDir() throws IllegalStateException {
		if (isBare())
			throw new IllegalStateException(
					JGitText.get().bareRepositoryNoWorkdirAndIndex);
		return workDir;
	}

	public void setWorkDir(File workTree) {
		this.workDir = workTree;
	}

	public void addRepositoryChangedListener(final RepositoryListener l) {
		listeners.add(l);
	}

	public void removeRepositoryChangedListener(final RepositoryListener l) {
		listeners.remove(l);
	}

	void fireRefsChanged() {
		final RefsChangedEvent event = new RefsChangedEvent(this);
		List<RepositoryListener> all = getAnyRepositoryChangedListeners();
		synchronized (listeners) {
			all.addAll(listeners);
		}
		for (final RepositoryListener l : all) {
			l.refsChanged(event);
		}
	}

	void fireIndexChanged() {
		final IndexChangedEvent event = new IndexChangedEvent(this);
		List<RepositoryListener> all = getAnyRepositoryChangedListeners();
		synchronized (listeners) {
			all.addAll(listeners);
		}
		for (final RepositoryListener l : all) {
			l.indexChanged(event);
		}
	}

	public void scanForRepoChanges() throws IOException {
		if (!isBare())
	}

	public ReflogReader getReflogReader(String refName) throws IOException {
		Ref ref = getRef(refName);
		if (ref != null)
			return new ReflogReader(this
		return null;
	}

	public String readMergeCommitMsg() throws IOException {
		File mergeMsgFile = new File(gitDir
		try {
			return new String(IO.readFully(mergeMsgFile));
		} catch (FileNotFoundException e) {
			return null;
		}
	}

	public List<ObjectId> readMergeHeads() throws IOException {
		File mergeHeadFile = new File(gitDir
		byte[] raw;
		try {
			raw = IO.readFully(mergeHeadFile);
		} catch (FileNotFoundException notFound) {
			return new LinkedList<ObjectId>();
		}

		if (raw.length == 0)
			throw new IOException("MERGE_HEAD file empty: " + mergeHeadFile);

		LinkedList<ObjectId> heads = new LinkedList<ObjectId>();
		for (int p = 0; p < raw.length;) {
			heads.add(ObjectId.fromString(raw
			p = RawParseUtils
					.nextLF(raw
		}
		return heads;
	}
}
