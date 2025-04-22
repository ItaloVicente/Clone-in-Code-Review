
package org.eclipse.jgit.lib;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.SystemReader;

public class FileRepository extends Repository {
	private final FileBasedConfig userConfig;

	private final FileBasedConfig repoConfig;

	private final RefDatabase refs;

	private final ObjectDirectory objectDatabase;

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
		repoConfig = new FileBasedConfig(userConfig

		loadUserConfig();
		loadRepoConfig();

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

	private void loadRepoConfig() throws IOException {
		try {
			repoConfig.load();
		} catch (ConfigInvalidException e1) {
			IOException e2 = new IOException(JGitText.get().unknownRepositoryFormat);
			e2.initCause(e1);
			throw e2;
		}
	}

	public void create(boolean bare) throws IOException {
		final FileBasedConfig cfg = getConfig();
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

	public File getObjectsDirectory() {
		return objectDatabase.getDirectory();
	}

	public ObjectDirectory getObjectDatabase() {
		return objectDatabase;
	}

	public RefDatabase getRefDatabase() {
		return refs;
	}

	public FileBasedConfig getConfig() {
		if (userConfig.isOutdated()) {
			try {
				loadUserConfig();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		if (repoConfig.isOutdated()) {
				try {
					loadRepoConfig();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
		}
		return repoConfig;
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

	public Set<ObjectId> getAdditionalHaves() {
		HashSet<ObjectId> r = new HashSet<ObjectId>();
		for (ObjectDatabase d : getObjectDatabase().getAlternates()) {
			if (d instanceof AlternateRepositoryDatabase) {
				Repository repo;

				repo = ((AlternateRepositoryDatabase) d).getRepository();
				for (Ref ref : repo.getAllRefs().values())
					r.add(ref.getObjectId());
				r.addAll(repo.getAdditionalHaves());
			}
		}
		return r;
	}

	public void openPack(final File pack
		objectDatabase.openPack(pack
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
}
