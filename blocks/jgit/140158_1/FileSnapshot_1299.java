
package org.eclipse.jgit.internal.storage.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.attributes.AttributesNode;
import org.eclipse.jgit.attributes.AttributesNodeProvider;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.events.ConfigChangedEvent;
import org.eclipse.jgit.events.ConfigChangedListener;
import org.eclipse.jgit.events.IndexChangedEvent;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.file.ObjectDirectory.AlternateHandle;
import org.eclipse.jgit.internal.storage.file.ObjectDirectory.AlternateRepository;
import org.eclipse.jgit.internal.storage.reftree.RefTreeDatabase;
import org.eclipse.jgit.lib.BaseRepositoryBuilder;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.CoreConfig.HideDotFiles;
import org.eclipse.jgit.lib.CoreConfig.SymLinks;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.ReflogReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.storage.pack.PackConfig;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.RawParseUtils;
import org.eclipse.jgit.util.StringUtils;
import org.eclipse.jgit.util.SystemReader;

public class FileRepository extends Repository {

	private final FileBasedConfig systemConfig;
	private final FileBasedConfig userConfig;
	private final FileBasedConfig repoConfig;
	private final RefDatabase refs;
	private final ObjectDirectory objectDatabase;

	private final Object snapshotLock = new Object();

	private FileSnapshot snapshot;

	public FileRepository(File gitDir) throws IOException {
		this(new FileRepositoryBuilder().setGitDir(gitDir).setup());
	}

	public FileRepository(String gitDir) throws IOException {
		this(new File(gitDir));
	}

	public FileRepository(BaseRepositoryBuilder options) throws IOException {
		super(options);

		if (StringUtils.isEmptyOrNull(SystemReader.getInstance().getenv(
				Constants.GIT_CONFIG_NOSYSTEM_KEY)))
			systemConfig = SystemReader.getInstance().openSystemConfig(null
					getFS());
		else
			systemConfig = new FileBasedConfig(null
				@Override
				public void load() {
				}

				@Override
				public boolean isOutdated() {
					return false;
				}
			};
		userConfig = SystemReader.getInstance().openUserConfig(systemConfig
				getFS());
		repoConfig = new FileBasedConfig(userConfig
				getDirectory()
				getFS());

		loadSystemConfig();
		loadUserConfig();
		loadRepoConfig();

		repoConfig.addChangeListener(new ConfigChangedListener() {
			@Override
			public void onConfigChanged(ConfigChangedEvent event) {
				fireEvent(event);
			}
		});

		final long repositoryFormatVersion = getConfig().getLong(
				ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_REPO_FORMAT_VERSION

		String reftype = repoConfig.getString(
				"extensions"
		if (repositoryFormatVersion >= 1 && reftype != null) {
			if (StringUtils.equalsIgnoreCase(reftype
				refs = new RefTreeDatabase(this
			} else {
				throw new IOException(JGitText.get().unknownRepositoryFormat);
			}
		} else {
			refs = new RefDirectory(this);
		}

		objectDatabase = new ObjectDirectory(repoConfig
				options.getObjectDirectory()
				options.getAlternateObjectDirectories()
				getFS()
				new File(getDirectory()

		if (objectDatabase.exists()) {
			if (repositoryFormatVersion > 1)
				throw new IOException(MessageFormat.format(
						JGitText.get().unknownRepositoryFormat2
						Long.valueOf(repositoryFormatVersion)));
		}

		if (!isBare()) {
			snapshot = FileSnapshot.save(getIndexFile());
		}
	}

	private void loadSystemConfig() throws IOException {
		try {
			systemConfig.load();
		} catch (ConfigInvalidException e) {
			throw new IOException(MessageFormat.format(JGitText
					.get().systemConfigFileInvalid
							.getAbsolutePath()
					e)
		}
	}

	private void loadUserConfig() throws IOException {
		try {
			userConfig.load();
		} catch (ConfigInvalidException e) {
			throw new IOException(MessageFormat.format(JGitText
					.get().userConfigFileInvalid
							.getAbsolutePath()
					e)
		}
	}

	private void loadRepoConfig() throws IOException {
		try {
			repoConfig.load();
		} catch (ConfigInvalidException e) {
			throw new IOException(JGitText.get().unknownRepositoryFormat
		}
	}

	@Override
	public void create(boolean bare) throws IOException {
		final FileBasedConfig cfg = getConfig();
		if (cfg.getFile().exists()) {
			throw new IllegalStateException(MessageFormat.format(
					JGitText.get().repositoryAlreadyExists
		}
		FileUtils.mkdirs(getDirectory()
		HideDotFiles hideDotFiles = getConfig().getEnum(
				ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_HIDEDOTFILES
				HideDotFiles.DOTGITONLY);
		if (hideDotFiles != HideDotFiles.FALSE && !isBare()
			getFS().setHidden(getDirectory()
		refs.create();
		objectDatabase.create();

		FileUtils.mkdir(new File(getDirectory()
		FileUtils.mkdir(new File(getDirectory()

		RefUpdate head = updateRef(Constants.HEAD);
		head.disableRefLog();
		head.link(Constants.R_HEADS + Constants.MASTER);

		final boolean fileMode;
		if (getFS().supportsExecute()) {
			File tmp = File.createTempFile("try"

			getFS().setExecute(tmp
			final boolean on = getFS().canExecute(tmp);

			getFS().setExecute(tmp
			final boolean off = getFS().canExecute(tmp);
			FileUtils.delete(tmp);

			fileMode = on && !off;
		} else {
			fileMode = false;
		}

		SymLinks symLinks = SymLinks.FALSE;
		if (getFS().supportsSymlinks()) {
			File tmp = new File(getDirectory()
			try {
				getFS().createSymLink(tmp
				symLinks = null;
				FileUtils.delete(tmp);
			} catch (IOException e) {
			}
		}
		if (symLinks != null)
			cfg.setString(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_SYMLINKS
							.toLowerCase(Locale.ROOT));
		cfg.setInt(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_REPO_FORMAT_VERSION
		cfg.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_FILEMODE
		if (bare)
			cfg.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_BARE
		cfg.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_LOGALLREFUPDATES
		if (SystemReader.getInstance().isMacOS())
			cfg.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_PRECOMPOSEUNICODE
		if (!bare) {
			File workTree = getWorkTree();
			if (!getDirectory().getParentFile().equals(workTree)) {
				cfg.setString(ConfigConstants.CONFIG_CORE_SECTION
						ConfigConstants.CONFIG_KEY_WORKTREE
								.getAbsolutePath());
				LockFile dotGitLockFile = new LockFile(new File(workTree
						Constants.DOT_GIT));
				try {
					if (dotGitLockFile.lock()) {
						dotGitLockFile.write(Constants.encode(Constants.GITDIR
								+ getDirectory().getAbsolutePath()));
						dotGitLockFile.commit();
					}
				} finally {
					dotGitLockFile.unlock();
				}
			}
		}
		cfg.save();
	}

	public File getObjectsDirectory() {
		return objectDatabase.getDirectory();
	}

	@Override
	public ObjectDirectory getObjectDatabase() {
		return objectDatabase;
	}

	@Override
	public RefDatabase getRefDatabase() {
		return refs;
	}

	@Override
	public FileBasedConfig getConfig() {
		if (systemConfig.isOutdated()) {
			try {
				loadSystemConfig();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
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

	@Override
	@Nullable
	public String getGitwebDescription() throws IOException {
		String d;
		try {
			d = RawParseUtils.decode(IO.readFully(descriptionFile()));
		} catch (FileNotFoundException err) {
			return null;
		}
		if (d != null) {
			d = d.trim();
			if (d.isEmpty() || UNNAMED.equals(d)) {
				return null;
			}
		}
		return d;
	}

	@Override
	public void setGitwebDescription(@Nullable String description)
			throws IOException {
		String old = getGitwebDescription();
		if (Objects.equals(old
			return;
		}

		File path = descriptionFile();
		LockFile lock = new LockFile(path);
		if (!lock.lock()) {
			throw new IOException(MessageFormat.format(JGitText.get().lockError
					path.getAbsolutePath()));
		}
		try {
			String d = description;
			if (d != null) {
				d = d.trim();
				if (!d.isEmpty()) {
					d += '\n';
				}
			} else {
			}
			lock.write(Constants.encode(d));
			lock.commit();
		} finally {
			lock.unlock();
		}
	}

	private File descriptionFile() {
		return new File(getDirectory()
	}

	@Override
	public Set<ObjectId> getAdditionalHaves() {
		return getAdditionalHaves(null);
	}

	private Set<ObjectId> getAdditionalHaves(Set<AlternateHandle.Id> skips) {
		HashSet<ObjectId> r = new HashSet<>();
		skips = objectDatabase.addMe(skips);
		for (AlternateHandle d : objectDatabase.myAlternates()) {
			if (d instanceof AlternateRepository && !skips.contains(d.getId())) {
				FileRepository repo;

				repo = ((AlternateRepository) d).repository;
				for (Ref ref : repo.getAllRefs().values()) {
					if (ref.getObjectId() != null)
						r.add(ref.getObjectId());
					if (ref.getPeeledObjectId() != null)
						r.add(ref.getPeeledObjectId());
				}
				r.addAll(repo.getAdditionalHaves(skips));
			}
		}
		return r;
	}

	public void openPack(File pack) throws IOException {
		objectDatabase.openPack(pack);
	}

	@Override
	public void scanForRepoChanges() throws IOException {
		detectIndexChanges();
	}

	private void detectIndexChanges() {
		if (isBare()) {
			return;
		}

		File indexFile = getIndexFile();
		synchronized (snapshotLock) {
			if (snapshot == null) {
				snapshot = FileSnapshot.save(indexFile);
				return;
			}
			if (!snapshot.isModified(indexFile)) {
				return;
			}
		}
		notifyIndexChanged(false);
	}

	@Override
	public void notifyIndexChanged(boolean internal) {
		synchronized (snapshotLock) {
			snapshot = FileSnapshot.save(getIndexFile());
		}
		fireEvent(new IndexChangedEvent(internal));
	}

	@Override
	public ReflogReader getReflogReader(String refName) throws IOException {
		Ref ref = findRef(refName);
		if (ref != null)
			return new ReflogReaderImpl(this
		return null;
	}

	@Override
	public AttributesNodeProvider createAttributesNodeProvider() {
		return new AttributesNodeProviderImpl(this);
	}

	static class AttributesNodeProviderImpl implements
			AttributesNodeProvider {

		private AttributesNode infoAttributesNode;

		private AttributesNode globalAttributesNode;

		protected AttributesNodeProviderImpl(Repository repo) {
			infoAttributesNode = new InfoAttributesNode(repo);
			globalAttributesNode = new GlobalAttributesNode(repo);
		}

		@Override
		public AttributesNode getInfoAttributesNode() throws IOException {
			if (infoAttributesNode instanceof InfoAttributesNode)
				infoAttributesNode = ((InfoAttributesNode) infoAttributesNode)
						.load();
			return infoAttributesNode;
		}

		@Override
		public AttributesNode getGlobalAttributesNode() throws IOException {
			if (globalAttributesNode instanceof GlobalAttributesNode)
				globalAttributesNode = ((GlobalAttributesNode) globalAttributesNode)
						.load();
			return globalAttributesNode;
		}

		static void loadRulesFromFile(AttributesNode r
				throws FileNotFoundException
			if (attrs.exists()) {
				try (FileInputStream in = new FileInputStream(attrs)) {
					r.parse(in);
				}
			}
		}

	}

	private boolean shouldAutoDetach() {
		return getConfig().getBoolean(ConfigConstants.CONFIG_GC_SECTION
				ConfigConstants.CONFIG_KEY_AUTODETACH
	}

	@Override
	public void autoGC(ProgressMonitor monitor) {
		GC gc = new GC(this);
		gc.setPackConfig(new PackConfig(this));
		gc.setProgressMonitor(monitor);
		gc.setAuto(true);
		gc.setBackground(shouldAutoDetach());
		try {
			gc.gc();
		} catch (ParseException | IOException e) {
			throw new JGitInternalException(JGitText.get().gcFailed
		}
	}
}
