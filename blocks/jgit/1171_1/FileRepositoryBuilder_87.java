
package org.eclipse.jgit.storage.file;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.lib.BaseRepositoryBuilder;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileObjectDatabase.AlternateHandle;
import org.eclipse.jgit.storage.file.FileObjectDatabase.AlternateRepository;
import org.eclipse.jgit.util.SystemReader;

public class FileRepository extends Repository {
	private final FileBasedConfig userConfig;

	private final FileBasedConfig repoConfig;

	private final RefDatabase refs;

	private final ObjectDirectory objectDatabase;

	public FileRepository(final File gitDir) throws IOException {
		this(new FileRepositoryBuilder().setGitDir(gitDir).setup());
	}

	public FileRepository(final BaseRepositoryBuilder options) throws IOException {
		super(options);

		userConfig = SystemReader.getInstance().openUserConfig(getFS());
		repoConfig = new FileBasedConfig(userConfig

		loadUserConfig();
		loadRepoConfig();

		refs = new RefDirectory(this);
		objectDatabase = new ObjectDirectory(repoConfig
				options.getObjectDirectory()
				options.getAlternateObjectDirectories()
				getFS());
		getListenerList().addConfigChangedListener(objectDatabase);

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
		getDirectory().mkdirs();
		refs.create();
		objectDatabase.create();

		new File(getDirectory()

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

	public Set<ObjectId> getAdditionalHaves() {
		HashSet<ObjectId> r = new HashSet<ObjectId>();
		for (AlternateHandle d : objectDatabase. myAlternates()) {
			if (d instanceof AlternateRepository) {
				Repository repo;

				repo = ((AlternateRepository) d).repository;
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
