package org.eclipse.jgit.niofs.internal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.AccessDeniedException;
import java.nio.file.AccessMode;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemAlreadyExistsException;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.NotDirectoryException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.WatchEvent;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileAttributeView;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.internal.ketch.KetchLeaderCache;
import org.eclipse.jgit.internal.ketch.KetchSystem;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.niofs.fs.AmbiguousFileSystemNameException;
import org.eclipse.jgit.niofs.fs.FileSystemState;
import org.eclipse.jgit.niofs.fs.attribute.DiffAttributes;
import org.eclipse.jgit.niofs.fs.attribute.HiddenAttributeView;
import org.eclipse.jgit.niofs.fs.attribute.HiddenAttributes;
import org.eclipse.jgit.niofs.fs.attribute.VersionAttributeView;
import org.eclipse.jgit.niofs.fs.attribute.VersionAttributes;
import org.eclipse.jgit.niofs.fs.options.CherryPickCopyOption;
import org.eclipse.jgit.niofs.fs.options.CommentedOption;
import org.eclipse.jgit.niofs.fs.options.MergeCopyOption;
import org.eclipse.jgit.niofs.fs.options.SquashOption;
import org.eclipse.jgit.niofs.internal.config.ConfigProperties;
import org.eclipse.jgit.niofs.internal.daemon.git.Daemon;
import org.eclipse.jgit.niofs.internal.daemon.ssh.BaseGitCommand;
import org.eclipse.jgit.niofs.internal.daemon.ssh.GitSSHService;
import org.eclipse.jgit.niofs.internal.hook.FileSystemHooks;
import org.eclipse.jgit.niofs.internal.manager.JGitFileSystemsManager;
import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.commands.Clone;
import org.eclipse.jgit.niofs.internal.op.commands.PathUtil;
import org.eclipse.jgit.niofs.internal.op.model.CommitContent;
import org.eclipse.jgit.niofs.internal.op.model.CommitInfo;
import org.eclipse.jgit.niofs.internal.op.model.CopyCommitContent;
import org.eclipse.jgit.niofs.internal.op.model.DefaultCommitContent;
import org.eclipse.jgit.niofs.internal.op.model.MoveCommitContent;
import org.eclipse.jgit.niofs.internal.op.model.PathInfo;
import org.eclipse.jgit.niofs.internal.op.model.PathType;
import org.eclipse.jgit.niofs.internal.op.model.RevertCommitContent;
import org.eclipse.jgit.niofs.internal.security.AuthenticationService;
import org.eclipse.jgit.niofs.internal.security.FileSystemAuthorization;
import org.eclipse.jgit.niofs.internal.security.PublicKeyAuthenticator;
import org.eclipse.jgit.niofs.internal.security.User;
import org.eclipse.jgit.niofs.internal.util.DescriptiveThreadFactory;
import org.eclipse.jgit.niofs.internal.util.EncodingUtil;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.storage.file.WindowCacheConfig;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.ReceiveCommand;
import org.eclipse.jgit.transport.ReceivePack;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.UploadPack;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.transport.resolver.ReceivePackFactory;
import org.eclipse.jgit.transport.resolver.RepositoryResolver;
import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.transport.resolver.UploadPackFactory;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.ProcessResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.nio.file.StandardOpenOption.READ;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.util.Collections.emptyList;
import static org.eclipse.jgit.lib.Constants.DOT_GIT_EXT;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.DEFAULT_SCHEME_SIZE;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_ENV_KEY_BRANCH_LIST;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_ENV_KEY_DEFAULT_REMOTE_NAME;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_ENV_KEY_DEST_PATH;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_ENV_KEY_INIT;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_ENV_KEY_MIRROR;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_ENV_KEY_PASSWORD;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_ENV_KEY_SUBDIRECTORY;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_ENV_KEY_USER_NAME;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.SCHEME;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.SCHEME_SIZE;
import static org.eclipse.jgit.niofs.internal.op.model.PathType.DIRECTORY;
import static org.eclipse.jgit.niofs.internal.op.model.PathType.NOT_FOUND;
import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkCondition;
import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotEmpty;
import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotNull;

public class JGitFileSystemProvider extends SecuredFileSystemProvider implements Disposable {

	private static final Logger LOG = LoggerFactory.getLogger(JGitFileSystemProvider.class);
	private static final TimeUnit LOCK_LAST_ACCESS_TIME_UNIT = TimeUnit.SECONDS;
	private static final long LOCK_LAST_ACCESS_THRESHOLD = 10;

	private final Map<String

	private final Object postponedEventsLock = new Object();

	private Daemon daemonService = null;

	private GitSSHService gitSSHService = null;

	private FS detectedFS = FS.DETECTED;

	private ExecutorService executorService;

	final KetchSystem system = new KetchSystem();

	final KetchLeaderCache leaders = new KetchLeaderCache(system);

	private AuthenticationService httpAuthenticator;
	private FileSystemAuthorization authorizer;

	JGitFileSystemProviderConfiguration config;

	JGitFileSystemsManager fsManager;

	JGitFileSystemsEventsManager fsEventsManager;

	public JGitFileSystemProvider() {
		this(new ConfigProperties(System.getProperties())
				Executors.newCachedThreadPool(new DescriptiveThreadFactory()));
	}

	public JGitFileSystemProvider(final Map<String
		this(new ConfigProperties(gitPrefs)
	}

	public JGitFileSystemProvider(final ConfigProperties gitPrefs
		this.executorService = executorService;

		setupConfigs(gitPrefs);

		setupFileSystemsManager();

		setupFSEvents();

		setupGitDefaultCredentials();

		setupSSH();

		setupFullHostNames();

		setupDaemon();

		setupGitSSH();
	}

	private void setupFSEvents() {
		fsEventsManager = new JGitFileSystemsEventsManager();
	}

	protected void setupFileSystemsManager() {
		fsManager = new JGitFileSystemsManager(this
	}

	private void setupConfigs(ConfigProperties gitPrefs) {
		config = new JGitFileSystemProviderConfiguration();

		loadConfig(gitPrefs);
	}

	private void setupGitSSH() {
		if (config.isSshEnabled()) {
			buildAndStartSSH();
		} else {
			gitSSHService = null;
		}
	}

	private void setupDaemon() {
		if (config.isDaemonEnabled()) {
			buildAndStartDaemon();
		} else {
			daemonService = null;
		}
	}

	private void setupFullHostNames() {
		if (config.isDaemonEnabled()) {
			fullHostNames.put("git"
		}
		if (config.isSshEnabled()) {
			fullHostNames.put("ssh"
		}
	}

	private void setupSSH() {
		SshSessionFactory.setInstance(new JGitSSHConfigSessionFactory(config));
	}

	private void setupGitDefaultCredentials() {
		CredentialsProvider.setDefault(new UsernamePasswordCredentialsProvider("guest"
	}

	private void loadConfig(final ConfigProperties systemConfig) {

		config.load(systemConfig);

		if (config.httpProxyIsDefined()) {
			setupProxyAuthentication();
		}
	}

	private void setupProxyAuthentication() {
		Authenticator.setDefault(new HTTPProxyAuthenticator(config.getHttpProxyUser()
				config.getHttpsProxyUser()
	}

	public void onCloseFileSystem(final JGitFileSystem fileSystem) {
		fsManager.addClosedFileSystems(fileSystem);

		synchronized (postponedEventsLock) {
			fileSystem.clearPostponedWatchEvents();
		}

		if (fsManager.allTheFSAreClosed()) {
			forceStopDaemon();
			shutdownSSH();
			shutdownEventsManager();
		}
	}

	protected void shutdownEventsManager() {
		this.fsEventsManager.shutdown();
	}

	public void onDisposeFileSystem(final JGitFileSystem fileSystem) {
		onCloseFileSystem(fileSystem);
		fsManager.remove(fileSystem.id());
	}

	@Override
	public void setAuthorizer(FileSystemAuthorization authorizer) {
		this.authorizer = checkNotNull("authorizer"
	}

	@Override
	public void setJAASAuthenticator(AuthenticationService authenticator) {
		if (gitSSHService != null) {
			gitSSHService.setUserPassAuthenticator(authenticator);
		}
	}

	@Override
	public void setHTTPAuthenticator(final AuthenticationService httpAuthenticator) {
		this.httpAuthenticator = httpAuthenticator;
	}

	@Override
	public void setSSHAuthenticator(PublicKeyAuthenticator authenticator) {
		checkNotNull("authenticator"

		if (gitSSHService != null) {
			gitSSHService.setPublicKeyAuthenticator(authenticator);
		}
	}

	@Override
	public void dispose() {
		shutdown();
	}

	public void addHostName(final String protocol
		fullHostNames.put(protocol
	}

	public Map<String
		return new HashMap<>(fullHostNames);
	}

	public class RepositoryResolverImpl<T> implements RepositoryResolver<T> {

		@Override
		public Repository open(final T client
				throws RepositoryNotFoundException
			final User user = extractUser(client);
			final JGitFileSystem fs = fsManager.get(name);
			if (fs == null) {
				throw new RepositoryNotFoundException(name);
			}

			if (authorizer != null && !authorizer.authorize(fs
				throw new ServiceNotAuthorizedException("User not authorized.");
			}

			return fs.getGit().getRepository();
		}

		public JGitFileSystem resolveFileSystem(final Repository repository) {
			return fsManager.get(repository);
		}
	}

	private User extractUser(Object client) {
		if (httpAuthenticator != null && client instanceof HttpServletRequest) {
			return httpAuthenticator.getUser();
		} else if (client instanceof BaseGitCommand) {
			return ((BaseGitCommand) client).getUser();
		}

		return () -> "ANONYMOUS";
	}

	private void buildAndStartSSH() {
		final ReceivePackFactory receivePackFactory = (req

		final UploadPackFactory uploadPackFactory = (UploadPackFactory<BaseGitCommand>) (req
				db) -> new UploadPack(db) {
					{
						final JGitFileSystem fs = fsManager.get(db);
						fs.filterBranchAccess(this
					}
				};

		gitSSHService = new GitSSHService();

		gitSSHService.setup(config.getSshFileCertDir()
				InetSocketAddress.createUnresolved(config.getSshHostAddr()
				config.getSshIdleTimeout()
				getRepositoryResolver()

		gitSSHService.start();
	}

	public <T> ReceivePack getReceivePack(final String protocol
		return new ReceivePack(db) {
			{

				final JGitFileSystem fs = fsManager.get(db);
				final Map<String

				setPreReceiveHook((rp
					fs.lock();
					final User user = extractUser(req);
					for (final ReceiveCommand command : commands2) {
						fs.checkBranchAccess(command
						final RevCommit lastCommit = fs.getGit().getLastCommit(command.getRefName());
						oldTreeRefs.put(command.getRefName()
					}
				});

				setPostReceiveHook((rp
					fs.unlock();
					fs.notifyExternalUpdate();
					final User user = extractUser(req);
					for (Map.Entry<String
						final List<RevCommit> commits = fs.getGit().listCommits(oldTreeRef.getValue()
								fs.getGit().getLastCommit(oldTreeRef.getKey()));
						for (final RevCommit revCommit : commits) {
							final RevTree parent = revCommit.getParentCount() > 0 ? revCommit.getParent(0).getTree()
									: null;
							notifyDiffs(fs
									revCommit.getFullMessage()
						}
					}
				});
			}
		};
	}

	public <T> RepositoryResolverImpl<T> getRepositoryResolver() {
		return new RepositoryResolverImpl<>();
	}

	void buildAndStartDaemon() {
		if (daemonService == null || !daemonService.isRunning()) {
			daemonService = new Daemon(new InetSocketAddress(config.getDaemonHostAddr()
					new ExecutorWrapper(executorService)
			daemonService.setRepositoryResolver(new RepositoryResolverImpl<>());
			try {
				daemonService.start();
			} catch (java.io.IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private void shutdownSSH() {
		if (gitSSHService != null) {
			gitSSHService.stop();
		}
	}

	void forceStopDaemon() {
		if (daemonService != null && daemonService.isRunning()) {
			daemonService.stop();
		}
	}

	public void shutdown() {

		for (JGitFileSystem jGitFileSystem : fsManager.getOpenFileSystems()) {
			try {
				jGitFileSystem.close();
			} catch (IOException e) {
				LOG.error("Failed to close the file system [" + jGitFileSystem.getName() + "]."
			}
		}
		shutdownSSH();
		forceStopDaemon();
		fsManager.clear();
	}

	public File getGitRepoContainerDir() {
		return config.getGitReposParentDir();
	}

	@Override
	public String getScheme() {
		return SCHEME;
	}

	@Override
	public FileSystem newFileSystem(final Path path
			throws IllegalArgumentException
		throw new UnsupportedOperationException();
	}

	@Override
	public FileSystem newFileSystem(final URI uri
			throws IllegalArgumentException
		checkNotNull("uri"
		checkCondition("uri scheme not supported"
		checkURI("uri"
		checkNotNull("env"

		String fsName = extractFSName(uri);

		validateFSName(uri

		String envUsername = extractEnvProperty(GIT_ENV_KEY_USER_NAME
		String envPassword = extractEnvProperty(GIT_ENV_KEY_PASSWORD

		fsManager.newFileSystem(() -> fullHostNames
				() -> buildCredential(envUsername

		JGitFileSystem fs = fsManager.get(fsName);

		boolean init = false;

		if (env.containsKey(GIT_ENV_KEY_INIT) && Boolean.parseBoolean(env.get(GIT_ENV_KEY_INIT).toString())) {
			init = true;
		}

		if (!env.containsKey(GIT_ENV_KEY_DEFAULT_REMOTE_NAME) && init) {
			try {
				final OutputStream stream = newOutputStream(getPath(initURI)
				final String _init = "Repository Init Content\n" + "=======================\n" + "\n"
						+ "Your project description here.";
				stream.write(_init.getBytes());
				stream.close();
			} catch (final Exception e) {
				e.printStackTrace();
				LOG.info("Repository initialization may have failed."
			}
		}

		if (config.isEnableKetch()) {
			createNewGitRepo(env
		}

		if (config.isDaemonEnabled() && daemonService != null && !daemonService.isRunning()) {
			buildAndStartDaemon();
		}

		return fs;
	}

	static Map<FileSystemHooks

		return Arrays.stream(FileSystemHooks.values()).filter(h -> env.get(h.name()) != null)
				.collect(Collectors.toMap(Function.identity()
	}

	private void validateFSName(URI uri
		if (fsManager.containsKey(fsName)) {
			throw new FileSystemAlreadyExistsException("There is already a FS for " + uri + ".");
		}
		if (fsName.contains("/")) {
			String fsNameRoot = fsName.substring(0
			if (fsManager.containsKey(fsNameRoot)) {
				throw new AmbiguousFileSystemNameException(
						"The file system name" + fsName + " is ambiguous with another FS registered");
			}
		}
		if (fsManager.containsRoot(fsName)) {
			throw new AmbiguousFileSystemNameException(
					"The file system name" + fsName + " is ambiguous with another FS registered");
		}
	}

	private String extractEnvProperty(String key
		if (env == null || env.get(key) == null) {
			return null;
		}
		return env.get(key).toString();
	}

	protected Git createNewGitRepo(Map<String

		final File repoDest = getRepoDest(env

		File directory = repoDest.getParentFile();
		String lockName = directory.getName();

		if (!directory.exists()) {
			directory.mkdirs();
		}

		FileSystemLock physicalLock = createLock(directory
		try {
			physicalLock.lock();

			return createNewGitRepo(env
		} finally {
			physicalLock.unlock();
		}
	}

	protected Git createNewGitRepo(Map<String
		final Git git;

		String envUsername = extractEnvProperty(GIT_ENV_KEY_USER_NAME
		String envPassword = extractEnvProperty(GIT_ENV_KEY_PASSWORD
		Boolean envMirror = (Boolean) env.get(GIT_ENV_KEY_MIRROR);
		boolean isMirror = envMirror == null ? true : envMirror;

		CredentialsProvider credential = buildCredential(envUsername

		if (syncWithRemote(env
			final String origin = env.get(GIT_ENV_KEY_DEFAULT_REMOTE_NAME).toString();
			final List<String> branches = (List<String>) env.get(GIT_ENV_KEY_BRANCH_LIST);
			final String subdirectory = (String) env.get(GIT_ENV_KEY_SUBDIRECTORY);
			try {
				if (this.isForkOrigin(origin)) {
					git = Git.fork(this.getGitRepoContainerDir()
							config.isEnableKetch() ? leaders : null
				} else if (subdirectory != null) {
					if (isMirror) {
						throw new UnsupportedOperationException(
								"Cannot make mirror repository when cloning subdirectory.");
					}
					git = Git.cloneSubdirectory(repoDest
							config.getHookDir()
				} else {
					git = Git.clone(repoDest
							config.isEnableKetch() ? leaders : null
				}
			} catch (Clone.CloneException | IOException ce) {
				fsManager.remove(fsName);
				throw new RuntimeException(ce);
			}
		} else {
			git = Git.createRepository(repoDest
					config.isSslVerify());
		}
		return git;
	}

	private FileSystemLock createLock(File directory
		return FileSystemLockManager.getInstance().getFileSystemLock(directory
				LOCK_LAST_ACCESS_TIME_UNIT
	}

	private File getRepoDest(Map<String
		final String outPath = (String) env.get(GIT_ENV_KEY_DEST_PATH);
		final File repoDest;

		if (outPath != null) {
			repoDest = new File(outPath
		} else {
			repoDest = new File(config.getGitReposParentDir()
		}
		return repoDest;
	}

	private boolean syncWithRemote(Map<String
		return env.containsKey(GIT_ENV_KEY_DEFAULT_REMOTE_NAME) && !repoDest.exists();
	}

	String extractFSName(final URI _uri) {
		if (uri.endsWith("/")) {
			uri = uri.substring(0
		}
		if (uri.contains("@")) {
			uri = uri.substring(uri.indexOf('@') + 1);
		}
		if (uri.contains(":")) {
			uri = uri.substring(0
		}

		return uri;
	}

	private boolean isForkOrigin(final String originURI) {
		return originURI.matches("(^\\w+\\/\\w+$)");
	}

	@Override
	public FileSystem getFileSystem(final URI uri)
			throws IllegalArgumentException
		checkNotNull("uri"
		checkCondition("uri scheme not supported"
		checkURI("uri"

		JGitFileSystem fileSystem = deepLookupFSFrom(uri);

		if (hasSyncFlag(uri)) {
			try {
				final String treeRef = "master";
				final ObjectId oldHead = fileSystem.getGit().getTreeFromRef(treeRef);
				final Map<String
				try {
					fileSystem.lock();
					final Map.Entry<String
							params.get("sync"));
					fileSystem.getGit().fetch(fileSystem.getCredential()
					fileSystem.getGit().syncRemote(remote);
				} finally {
					fileSystem.unlock();
				}
				final ObjectId newHead = fileSystem.getGit().getTreeFromRef(treeRef);
				notifyDiffs(fileSystem
			} catch (final Exception ex) {
				throw new RuntimeException("Failed to sync repository."
			}
		}
		if (hasPushFlag(uri)) {
			try {
				final Map<String
				fileSystem.getGit().push(fileSystem.getCredential()
						new AbstractMap.SimpleEntry<>("upstream"
			} catch (final Exception ex) {
				throw new RuntimeException("Failed to push repository."
			}
		}

		return fileSystem;
	}

	String extractFSNameWithPath(final URI uri) {
		checkNotNull("uri"

		String path = uri.getAuthority() + uri.getPath();

		int index = path.indexOf('@');
		if (index != -1) {
			path = path.substring(index + 1);
		}
		return path;
	}

	@Override
	public Path getPath(final URI uri) throws IllegalArgumentException
		checkNotNull("uri"
		checkCondition("uri scheme not supported"
		checkURI("uri"

		if (LOG.isDebugEnabled()) {
			LOG.debug("Accessing uri " + uri.toString());
		}

		Path path;

		JGitFileSystem fileSystem = deepLookupFSFrom(uri);

		String branch = extractBranchFrom(uri);

		String host = buildHostFrom(fileSystem

		String pathStr = buildPathFrom(uri
		path = JGitPathImpl.create(fileSystem

		return path;
	}

	private String buildPathFrom(URI uri
		String pathStr = uri.toString();
		pathStr = pathStr.replace(host
		pathStr = EncodingUtil.decode(pathStr);
		if (pathStr.startsWith("/:")) {
			pathStr = pathStr.substring(2);
		}
		return pathStr;
	}

	private String buildHostFrom(JGitFileSystem fileSystem
		String host = branch + fileSystem.getName();

		return host;
	}

	private String extractBranchFrom(URI uri) {
		String branch = "";

		int index = uri.toString().indexOf('@');
		if (index != -1) {
			branch = uri.toString().substring(0
		}
		return branch;
	}

	public String extractPath(final URI uri) {
		checkNotNull("uri"

		return getPath(uri).toString();
	}

	private JGitFileSystem deepLookupFSFrom(URI uri) {

		String fullURI = extractFSNameWithPath(uri);
		int index = fullURI.indexOf("/");
		JGitFileSystem jGitFileSystem = fsManager.get(fullURI);

		while (jGitFileSystem == null && index >= 0) {

			String fsCandidate = fullURI.substring(0
			jGitFileSystem = fsManager.get(fsCandidate);

			index = fullURI.indexOf("/"
		}

		if (jGitFileSystem == null) {
			throw new FileSystemNotFoundException("No filesystem for uri (" + uri + ") found.");
		}

		return jGitFileSystem;
	}

	@Override
	public InputStream newInputStream(final Path path
			throws IllegalArgumentException
		checkNotNull("path"

		final JGitPathImpl gPath = toPathImpl(path);

		return cast(gPath.getFileSystem()).getGit().blobAsInputStream(gPath.getRefTree()
	}

	@Override
	public OutputStream newOutputStream(final Path path
			throws IllegalArgumentException
		checkNotNull("path"

		final JGitPathImpl gPath = toPathImpl(path);
		final PathInfo result = cast(gPath.getFileSystem()).getGit().getPathInfo(gPath.getRefTree()

		if (result.getPathType().equals(PathType.DIRECTORY)) {
			throw new NotDirectoryException(path.toString());
		}

		try {
			final File file = File.createTempFile("gitz"
			return new FilterOutputStream(new FileOutputStream(file)) {
				@Override
				public void close() throws java.io.IOException {
					super.close();

					commit(gPath
							new DefaultCommitContent(new HashMap<String
								{
									put(gPath.getPath()
								}
							}));
				}
			};
		} catch (java.io.IOException e) {
			throw new IOException("Could not create file or output stream."
		}
	}

	private JGitFileSystem cast(final FileSystem fileSystem) {
		return (JGitFileSystem) fileSystem;
	}

	private CommitInfo buildCommitInfo(final String defaultMessage
		String sessionId = null;
		String name = null;
		String email = null;
		String message = defaultMessage;
		TimeZone timeZone = null;
		Date when = null;

		if (options != null && !options.isEmpty()) {
			final CommentedOption op = extractCommentedOption(options);
			if (op != null) {
				sessionId = op.getSessionId();
				name = op.getName();
				email = op.getEmail();
				if (op.getMessage() != null && !op.getMessage().trim().isEmpty()) {
					message = op.getMessage() + " " + defaultMessage;
				}
				timeZone = op.getTimeZone();
				when = op.getWhen();
			}
		}

		return new CommitInfo(sessionId
	}

	private CommentedOption extractCommentedOption(final Collection<?> options) {
		for (final Object option : options) {
			if (option instanceof CommentedOption) {
				return (CommentedOption) option;
			}
		}
		return null;
	}

	@Override
	public FileChannel newFileChannel(final Path path
			final FileAttribute<?>... attrs)
			throws IllegalArgumentException
		throw new UnsupportedOperationException();
	}

	@Override
	public AsynchronousFileChannel newAsynchronousFileChannel(final Path path
			final ExecutorService executor
			throws IllegalArgumentException
		throw new UnsupportedOperationException();
	}

	@Override
	public SeekableByteChannel newByteChannel(final Path path
			final FileAttribute<?>... attrs)
			throws IllegalArgumentException
		final JGitPathImpl gPath = toPathImpl(path);

		if (exists(path)) {
			if (!shouldCreateOrOpenAByteChannel(options)) {
				throw new FileAlreadyExistsException(path.toString());
			}
		}

		final PathInfo result = cast(gPath.getFileSystem()).getGit().getPathInfo(gPath.getRefTree()

		if (result.getPathType().equals(PathType.DIRECTORY)) {
			throw new NotDirectoryException(path.toString());
		}

		try {
			if (options != null && options.contains(READ)) {
				return openAByteChannel(path);
			} else {
				return createANewByteChannel(path
			}
		} catch (java.io.IOException e) {
			throw new IOException("Failed to open or create a byte channel."
		} finally {
			cast(path).clearCache();
		}
	}

	private SeekableByteChannel createANewByteChannel(final Path path
			final JGitPathImpl gPath
		final File file = File.createTempFile("gitz"

		return new SeekableByteChannelWrapper(new RandomAccessFile(file
			@Override
			public void close() throws java.io.IOException {
				super.close();
				commit(gPath
						new DefaultCommitContent(new HashMap<String
							{
								put(gPath.getPath()
							}
						}));
			}
		};
	}

	private SeekableByteChannel openAByteChannel(Path path) throws FileNotFoundException {
		return new RandomAccessFile(path.toFile()
	}

	private boolean shouldCreateOrOpenAByteChannel(Set<? extends OpenOption> options) {
		return (options != null && (options.contains(TRUNCATE_EXISTING) || options.contains(READ)));
	}

	protected boolean exists(final Path path) {
		try {
			readAttributes(path
			return true;
		} catch (final Exception ignored) {
		}
		return false;
	}

	@Override
	public DirectoryStream<Path> newDirectoryStream(final Path path
			throws IOException
		checkNotNull("path"
		final DirectoryStream.Filter<? super Path> filter;
		if (pfilter == null) {
			filter = entry -> true;
		} else {
			filter = pfilter;
		}

		final JGitPathImpl gPath = toPathImpl(path);

		final PathInfo result = cast(gPath.getFileSystem()).getGit().getPathInfo(gPath.getRefTree()

		if (!result.getPathType().equals(PathType.DIRECTORY)) {
			throw new NotDirectoryException(path.toString());
		}

		final List<PathInfo> pathContent = cast(gPath.getFileSystem()).getGit().listPathContent(gPath.getRefTree()
				gPath.getPath());

		return new DirectoryStream<Path>() {
			boolean isClosed = false;

			@Override
			public void close() throws IOException {
				if (isClosed) {
					throw new IOException("This stream is closed.");
				}
				isClosed = true;
			}

			@Override
			public Iterator<Path> iterator() {
				if (isClosed) {
					throw new RuntimeException("This stream is closed.");
				}
				return new Iterator<Path>() {
					int i = -1;
					Path nextEntry = null;
					boolean atEof = false;

					@Override
					public boolean hasNext() {
						if (nextEntry == null && !atEof) {
							nextEntry = readNextEntry();
						}
						return nextEntry != null;
					}

					@Override
					public Path next() {
						final Path result;
						if (nextEntry == null && !atEof) {
							result = readNextEntry();
						} else {
							result = nextEntry;
							nextEntry = null;
						}
						if (result == null) {
							throw new NoSuchElementException();
						}
						return result;
					}

					private Path readNextEntry() {
						if (atEof) {
							return null;
						}

						Path result = null;
						while (true) {
							i++;
							if (i >= pathContent.size()) {
								atEof = true;
								break;
							}

							final PathInfo content = pathContent.get(i);
							final Path path = JGitPathImpl.create(gPath.getFileSystem()
									gPath.getHost()
							try {
								if (filter.accept(path)) {
									result = path;
									break;
								}
							} catch (IOException e) {
								throw new RuntimeException(e);
							}
						}

						return result;
					}

					@Override
					public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			}
		};
	}

	@Override
	public void createDirectory(final Path path
			throws UnsupportedOperationException
		checkNotNull("path"

		final JGitPathImpl gPath = toPathImpl(path);

		final PathInfo result = cast(gPath.getFileSystem()).getGit().getPathInfo(gPath.getRefTree()

		if (!result.getPathType().equals(NOT_FOUND)) {
			throw new FileAlreadyExistsException(path.toString());
		}

		try {
			final OutputStream outputStream = newOutputStream(path.resolve(".gitkeep"));
			outputStream.close();
		} catch (final Exception e) {
			throw new IOException("Failed to write to or close the output stream."
		}
	}

	@Override
	public void createSymbolicLink(final Path link
			throws UnsupportedOperationException
		throw new UnsupportedOperationException();
	}

	@Override
	public void createLink(final Path link
			throws UnsupportedOperationException
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(final Path path) throws IOException
		checkNotNull("path"

		if (path instanceof JGitFSPath) {
			deleteFS(path.getFileSystem());
			return;
		}

		final JGitPathImpl gPath = toPathImpl(path);

		if (isBranch(gPath)) {
			deleteBranch(gPath);
			return;
		}

		deleteAsset(gPath);
	}

	protected boolean deleteFS(final FileSystem _fs) throws IOException {

		final JGitFileSystemImpl fileSystem = (JGitFileSystemImpl) _fs;
		final File gitDir = fileSystem.getGit().getRepository().getDirectory();
		File parentDir = gitDir.getParentFile();
		FileSystemLock physicalLock = createLock(parentDir

		try {
			physicalLock.lock();
			fileSystem.close();
			fileSystem.dispose();
			if (System.getProperty("os.name").toLowerCase().contains("windows")) {
				new WindowCacheConfig().install();
			}

			fsManager.remove(fileSystem.getName());

			FileUtils.delete(gitDir

			cleanupParentDir(gitDir);
			return true;
		} catch (java.io.IOException e) {
			throw new IOException("Failed to remove the git repository."
		} finally {
			physicalLock.unlock();
		}
	}

	private void cleanupParentDir(File gitDir) throws java.io.IOException {
		final File parentDir = gitDir.getParentFile();
		if (parentDir.isDirectory() && parentDirIsEmpty(parentDir) && !parentDir.equals(getGitRepoContainerDir())) {
			FileUtils.delete(parentDir
		}
	}

	private boolean parentDirIsEmpty(File parentDir) {
		return parentDir.list().length == 0;
	}

	public void deleteAsset(final JGitPathImpl path) throws IOException {
		final PathInfo result = cast(path.getFileSystem()).getGit().getPathInfo(path.getRefTree()

		if (result.getPathType().equals(PathType.DIRECTORY)) {
			if (deleteNonEmptyDirectory()) {
				deleteResource(path);
				return;
			}
			final List<PathInfo> content = cast(path.getFileSystem()).getGit().listPathContent(path.getRefTree()
					path.getPath());
			if (content.size() == 1 && content.get(0).getPath().equals(path.getPath().substring(1) + "/.gitkeep")) {
				delete(path.resolve(".gitkeep"));
				deleteResource(path);
				return;
			}
			throw new DirectoryNotEmptyException(path.toString());
		}

		if (result.getPathType().equals(NOT_FOUND)) {
			throw new NoSuchFileException(path.toString());
		}

		deleteResource(path);
	}

	private void deleteResource(final JGitPathImpl path) {
		delete(path
	}

	private boolean deleteNonEmptyDirectory() {
		return false;
	}

	public void deleteBranch(final JGitPathImpl path) throws IOException {
		final Ref branch = cast(path.getFileSystem()).getGit().getRef(path.getRefTree());

		if (branch == null) {
			throw new NoSuchFileException(path.toString());
		}

		try {
			cast(path.getFileSystem()).lock();
			cast(path.getFileSystem()).getGit().deleteRef(branch);
		} finally {
			cast(path.getFileSystem()).unlock();
		}
	}

	@Override
	public boolean deleteIfExists(final Path path) throws IOException
		checkNotNull("path"

		if (path instanceof JGitFSPath) {
			return deleteFS(path.getFileSystem());
		}

		final JGitPathImpl gPath = toPathImpl(path);

		if (isBranch(gPath)) {
			return deleteBranchIfExists(gPath);
		}

		return deleteAssetIfExists(gPath);
	}

	public boolean deleteBranchIfExists(final JGitPathImpl path) throws IOException {
		try {
			deleteBranch(path);
			return true;
		} catch (final NoSuchFileException ignored) {
			return false;
		}
	}

	public boolean deleteAssetIfExists(final JGitPathImpl path) throws IOException {
		final PathInfo result = cast(path.getFileSystem()).getGit().getPathInfo(path.getRefTree()

		if (result.getPathType().equals(PathType.DIRECTORY)) {
			if (deleteNonEmptyDirectory()) {
				deleteResource(path);
				return true;
			}
			final List<PathInfo> content = cast(path.getFileSystem()).getGit().listPathContent(path.getRefTree()
					path.getPath());
			if (content.size() == 1 && content.get(0).getPath().equals(path.getPath().substring(1) + "/.gitkeep")) {
				delete(path.resolve(".gitkeep"));
				return true;
			}
			throw new DirectoryNotEmptyException(path.toString());
		}

		if (result.getPathType().equals(NOT_FOUND)) {
			return false;
		}

		deleteResource(path);
		return true;
	}

	@Override
	public Path readSymbolicLink(final Path link) throws UnsupportedOperationException
		checkNotNull("link"
		throw new UnsupportedOperationException();
	}

	@Override
	public void copy(final Path source
			throws UnsupportedOperationException
		checkNotNull("source"
		checkNotNull("target"

		final JGitPathImpl gSource = toPathImpl(source);
		final JGitPathImpl gTarget = toPathImpl(target);
		final boolean isBranch = isBranch(gSource) && isBranch(gTarget);

		if (options.length == 1 && options[0] instanceof MergeCopyOption) {
			if (!isBranch) {
				throw new IOException("Merge needs source and target as root.");
			}
			this.merge(gSource
		} else if (options.length == 1 && options[0] instanceof CherryPickCopyOption) {
			if (!isBranch) {
				throw new IOException("Cherry pick needs source and target as root.");
			}
			final String[] commits = ((CherryPickCopyOption) options[0]).getCommits();
			if (commits == null || commits.length == 0) {
				throw new IOException("Cherry pick needs at least one commit id.");
			}
			cherryPick(gSource
		} else {
			if (isBranch) {
				copyBranch(gSource
				return;
			}
			copyAsset(gSource
		}
	}

	private void merge(final JGitPathImpl source

		try {
			cast(target.getFileSystem()).lock();
			cast(source.getFileSystem()).getGit().merge(source.getRefTree()
		} finally {
			cast(target.getFileSystem()).unlock();
		}
	}

	private void cherryPick(final JGitPathImpl source
			throws IOException {
		try {
			cast(target.getFileSystem()).lock();
			cast(source.getFileSystem()).getGit().cherryPick(target
		} finally {
			cast(target.getFileSystem()).unlock();
		}
	}

	private void copyBranch(final JGitPathImpl source
			throws FileAlreadyExistsException
		checkCondition("source and target should have same file system"
		if (existsBranch(target)) {
			throw new FileAlreadyExistsException(target.toString());
		}
		if (!existsBranch(source)) {
			throw new NoSuchFileException(target.toString());
		}
		createBranch(source
	}

	private void copyAsset(final JGitPathImpl source
			throws IOException {
		final PathInfo sourceResult = cast(source.getFileSystem()).getGit().getPathInfo(source.getRefTree()
				source.getPath());
		final PathInfo targetResult = cast(target.getFileSystem()).getGit().getPathInfo(target.getRefTree()
				target.getPath());

		if (!isRoot(target) && targetResult.getPathType() != NOT_FOUND) {
			if (!contains(options
				throw new FileAlreadyExistsException(target.toString());
			}
		}

		if (sourceResult.getPathType() == NOT_FOUND) {
			throw new NoSuchFileException(target.toString());
		}

		if (!source.getRefTree().equals(target.getRefTree())) {
			copyAssetContent(source
		} else if (!source.getFileSystem().equals(target.getFileSystem())) {
			copyAssetContent(source
		} else {
			final Map<JGitPathImpl
			if (sourceResult.getPathType() == DIRECTORY) {
				sourceDest.putAll(mapDirectoryContent(source
			} else {
				sourceDest.put(source
			}

			copyFiles(source
		}
	}

	private void copyAssetContent(final JGitPathImpl source
			throws IOException {
		final PathInfo sourceResult = cast(source.getFileSystem()).getGit().getPathInfo(source.getRefTree()
				source.getPath());
		final PathInfo targetResult = cast(target.getFileSystem()).getGit().getPathInfo(target.getRefTree()
				target.getPath());

		if (!isRoot(target) && targetResult.getPathType() != NOT_FOUND) {
			if (!contains(options
				throw new FileAlreadyExistsException(target.toString());
			}
		}

		if (sourceResult.getPathType() == NOT_FOUND) {
			throw new NoSuchFileException(target.toString());
		}

		if (sourceResult.getPathType() == DIRECTORY) {
			copyDirectory(source
			return;
		}

		copyFile(source
	}

	private boolean contains(final CopyOption[] options
		for (final CopyOption option : options) {
			if (option.equals(opt)) {
				return true;
			}
		}
		return false;
	}

	private void copyDirectory(final JGitPathImpl source
			throws IOException {
		final List<JGitPathImpl> directories = new ArrayList<>();
		for (final Path path : newDirectoryStream(source
			final JGitPathImpl gPath = toPathImpl(path);
			final PathInfo pathResult = cast(gPath.getFileSystem()).getGit().getPathInfo(gPath.getRefTree()
					gPath.getPath());
			if (pathResult.getPathType() == DIRECTORY) {
				directories.add(gPath);
				continue;
			}
			final JGitPathImpl gTarget = composePath(target

			copyFile(gPath
		}
		for (final JGitPathImpl directory : directories) {
			createDirectory(composePath(target
		}
	}

	private JGitPathImpl composePath(final JGitPathImpl directory
			final CopyOption... options) {
		if (directory.getPath().endsWith("/")) {
			return toPathImpl(getPath(URI.create(directory.toUri().toString() + uriEncode(fileName.toString(false)))));
		}
		return toPathImpl(
				getPath(URI.create(directory.toUri().toString() + "/" + uriEncode(fileName.toString(false)))));
	}

	private String uriEncode(final String s) {
		try {
			return URLEncoder.encode(s
		} catch (UnsupportedEncodingException e) {
			return s;
		}
	}

	private void copyFile(final JGitPathImpl source
			throws IOException {

		final InputStream in = newInputStream(source
		final SeekableByteChannel out = newByteChannel(target
			{
				add(StandardOpenOption.TRUNCATE_EXISTING);
				for (final CopyOption _option : options) {
					if (_option instanceof OpenOption) {
						add((OpenOption) _option);
					}
				}
			}
		});

		try {
			int count;
			byte[] buffer = new byte[8192];
			while ((count = in.read(buffer)) > 0) {
				out.write(ByteBuffer.wrap(buffer
			}
		} catch (Exception e) {
			throw new IOException("Failed to copy file from '" + source + "' to '" + target + "'"
		} finally {
			try {
				out.close();
			} catch (java.io.IOException e) {
				throw new IOException("Could not close output stream."
			} finally {
				try {
					in.close();
				} catch (java.io.IOException e) {
					throw new IOException("Could not close input stream."
				}
			}
		}
	}

	private OpenOption[] convert(CopyOption... options) {
		if (options == null || options.length == 0) {
			return new OpenOption[0];
		}
		final List<OpenOption> newOptions = new ArrayList<>(options.length);
		for (final CopyOption option : options) {
			if (option instanceof OpenOption) {
				newOptions.add((OpenOption) option);
			}
		}

		return newOptions.toArray(new OpenOption[newOptions.size()]);
	}

	private void createBranch(final JGitPathImpl source
		try {
			cast(target.getFileSystem()).lock();
			cast(source.getFileSystem()).getGit().createRef(source.getRefTree()
		} finally {
			cast(target.getFileSystem()).unlock();
		}
	}

	private boolean existsBranch(final JGitPathImpl path) {
		return cast(path.getFileSystem()).getGit().getRef(path.getRefTree()) != null;
	}

	private boolean isBranch(final JGitPathImpl path) {
		return path.getPath().length() == 1 && path.getPath().equals("/");
	}

	private boolean isRoot(final JGitPathImpl path) {
		return isBranch(path);
	}

	private boolean hasSameFileSystem(final JGitPathImpl source
		return source.getFileSystem().equals(target.getFileSystem());
	}

	@Override
	public void move(final Path source
			throws DirectoryNotEmptyException
		checkNotNull("source"
		checkNotNull("target"

		final JGitPathImpl gSource = toPathImpl(source);
		final JGitPathImpl gTarget = toPathImpl(target);

		final boolean isSourceBranch = isBranch(gSource);
		final boolean isTargetBranch = isBranch(gTarget);

		if (isSourceBranch && isTargetBranch) {
			moveBranch(gSource
			return;
		}
		moveAsset(gSource
	}

	private void moveBranch(final JGitPathImpl source
			throws IOException {
		checkCondition("source and target should have same file system"

		if (!exists(source)) {
			throw new NoSuchFileException(target.toString());
		}

		boolean targetExists = existsBranch(target);
		if (targetExists && !contains(options
			throw new FileAlreadyExistsException(target.toString());
		}

		if (!targetExists) {
			createBranch(source
			deleteBranch(source);
		} else {
			commit(target
					new RevertCommitContent(source.getRefTree()));
		}
	}

	private void moveAsset(final JGitPathImpl source
			throws IOException {
		final PathInfo sourceResult = cast(source.getFileSystem()).getGit().getPathInfo(source.getRefTree()
				source.getPath());
		final PathInfo targetResult = cast(target.getFileSystem()).getGit().getPathInfo(target.getRefTree()
				target.getPath());

		if (!isRoot(target) && targetResult.getPathType() != NOT_FOUND) {
			if (!contains(options
				throw new FileAlreadyExistsException(target.toString());
			}
		}

		if (sourceResult.getPathType() == NOT_FOUND) {
			throw new NoSuchFileException(target.toString());
		}

		if (!source.getRefTree().equals(target.getRefTree())) {
			copy(source
			delete(source);
		} else {
			final Map<JGitPathImpl
			if (sourceResult.getPathType() == DIRECTORY) {
				fromTo.putAll(mapDirectoryContent(source
			} else {
				fromTo.put(source
			}

			moveFiles(source
		}
	}

	private Map<JGitPathImpl
			final CopyOption... options) throws IOException {
		final Map<JGitPathImpl
		for (final Path path : newDirectoryStream(source
			final JGitPathImpl gPath = toPathImpl(path);
			final PathInfo pathResult = cast(gPath.getFileSystem()).getGit().getPathInfo(gPath.getRefTree()
					gPath.getPath());
			if (pathResult.getPathType() == DIRECTORY) {
				fromTo.putAll(mapDirectoryContent(gPath
			} else {
				final JGitPathImpl gTarget = composePath(target
				fromTo.put(gPath
			}
		}

		return fromTo;
	}

	private void moveFiles(final JGitPathImpl source
			final Map<JGitPathImpl
		final Map<String
		for (final Map.Entry<JGitPathImpl
			result.put(PathUtil.normalize(fromToEntry.getKey().getPath())
					PathUtil.normalize(fromToEntry.getValue().getPath()));
		}
		commit(source
				Arrays.asList(options))
	}

	private void copyFiles(final JGitPathImpl source
			final Map<JGitPathImpl
		final Map<String
		for (final Map.Entry<JGitPathImpl
			result.put(PathUtil.normalize(sourceDestEntry.getKey().getPath())
					PathUtil.normalize(sourceDestEntry.getValue().getPath()));
		}
		commit(source
				Arrays.asList(options))
	}

	@Override
	public boolean isSameFile(final Path pathA
		checkNotNull("pathA"
		checkNotNull("pathB"

		final JGitPathImpl gPathA = toPathImpl(pathA);
		final JGitPathImpl gPathB = toPathImpl(pathB);

		final PathInfo resultA = cast(gPathA.getFileSystem()).getGit().getPathInfo(gPathA.getRefTree()
				gPathA.getPath());
		final PathInfo resultB = cast(gPathB.getFileSystem()).getGit().getPathInfo(gPathB.getRefTree()
				gPathB.getPath());

		if (resultA.getPathType() == PathType.FILE && resultA.getObjectId().equals(resultB.getObjectId())) {
			return true;
		}

		return pathA.equals(pathB);
	}

	@Override
	public boolean isHidden(final Path path) throws IllegalArgumentException
		checkNotNull("path"

		final JGitPathImpl gPath = toPathImpl(path);

		if (gPath.getFileName() == null) {
			return false;
		}

		return toPathImpl(path.getFileName()).toString(false).startsWith(".");
	}

	@Override
	public FileStore getFileStore(final Path path) throws IOException
		checkNotNull("path"

		return new JGitFileStore(cast(toPathImpl(path).getFileSystem()).getGit().getRepository());
	}

	@Override
	public void checkAccess(final Path path
			NoSuchFileException
		checkNotNull("path"

		final JGitPathImpl gPath = toPathImpl(path);

		final PathInfo result = cast(gPath.getFileSystem()).getGit().getPathInfo(gPath.getRefTree()

		if (result.getPathType().equals(NOT_FOUND)) {
			throw new NoSuchFileException(path.toString());
		}
	}

	@Override
	public <V extends FileAttributeView> V getFileAttributeView(final Path path
			final LinkOption... options) {
		checkNotNull("path"
		checkNotNull("type"

		final JGitPathImpl gPath = toPathImpl(path);

		final PathInfo pathResult = cast(gPath.getFileSystem()).getGit().getPathInfo(gPath.getRefTree()
				gPath.getPath());
		if (pathResult.getPathType().equals(NOT_FOUND)) {
			throw new RuntimeException(path.toString());
		}

		final V resultView = gPath.getAttrView(type);

		if (resultView == null) {
			if (type == BasicFileAttributeView.class || type == JGitBasicAttributeView.class) {
				final V newView = (V) new JGitBasicAttributeView(gPath);
				gPath.addAttrView(newView);
				return newView;
			} else if (type == HiddenAttributeView.class || type == HiddenAttributeViewImpl.class
					|| type == JGitHiddenAttributeViewImpl.class) {
				final V newView = (V) new JGitHiddenAttributeViewImpl(gPath);
				gPath.addAttrView(newView);
				return newView;
			} else if (type == VersionAttributeView.class || type == VersionAttributeViewImpl.class
					|| type == JGitVersionAttributeViewImpl.class) {
				final V newView = (V) new JGitVersionAttributeViewImpl(gPath);
				gPath.addAttrView(newView);
				return newView;
			}
		}

		return resultView;
	}

	private ExtendedAttributeView getFileAttributeView(final JGitPathImpl path
			final LinkOption... options) {
		final ExtendedAttributeView view = cast(path).getAttrView(name);

		if (view == null) {
			if (name.equals("basic")) {
				final JGitBasicAttributeView newView = new JGitBasicAttributeView(path);
				path.addAttrView(newView);
				return newView;
			} else if (name.equals("extended")) {
				final JGitHiddenAttributeViewImpl newView = new JGitHiddenAttributeViewImpl(path);
				path.addAttrView(newView);
				return newView;
			} else if (name.equals("version")) {
				final JGitVersionAttributeViewImpl newView = new JGitVersionAttributeViewImpl(path);
				path.addAttrView(newView);
				return newView;
			} else if (name.equals("diff")) {
				final JGitDiffAttributeViewImpl newView = new JGitDiffAttributeViewImpl(path
				path.addAttrView(newView);
				return newView;
			}
		}
		return view;
	}

	private JGitPathImpl cast(final Path path) {
		return (JGitPathImpl) path;
	}

	@Override
	public <A extends BasicFileAttributes> A readAttributes(final Path path
			final LinkOption... options)
			throws NoSuchFileException
		checkNotNull("path"
		checkNotNull("type"

		final JGitPathImpl gPath = toPathImpl(path);

		final PathInfo pathResult = cast(gPath.getFileSystem()).getGit().getPathInfo(gPath.getRefTree()
				gPath.getPath());
		if (pathResult.getPathType().equals(NOT_FOUND)) {
			throw new NoSuchFileException(path.toString());
		}

		if (type == VersionAttributes.class) {
			final JGitVersionAttributeViewImpl view = getFileAttributeView(path
					options);
			return (A) view.readAttributes();
		} else if (type == HiddenAttributes.class) {
			final JGitHiddenAttributeViewImpl view = getFileAttributeView(path
					options);
			return (A) view.readAttributes();
		} else if (type == DiffAttributes.class) {
			final JGitDiffAttributeViewImpl view = getFileAttributeView(path
			return (A) view.readAttributes();
		} else if (type == BasicFileAttributes.class) {
			final JGitBasicAttributeView view = getFileAttributeView(path
			return (A) view.readAttributes();
		}

		return null;
	}

	@Override
	public Map<String
			throws UnsupportedOperationException
		checkNotNull("path"
		checkNotEmpty("attributes"

		final String[] s = split(attributes);
		if (s[0].length() == 0) {
			throw new IllegalArgumentException(attributes);
		}

		if (s[0].equals("diff")) {
			final ExtendedAttributeView view = getFileAttributeView(toPathImpl(path)

			return view.readAttributes("diff");
		} else {
			final ExtendedAttributeView view = getFileAttributeView(toPathImpl(path)
			if (view == null) {
				throw new UnsupportedOperationException("View '" + s[0] + "' not available");
			}

			return view.readAttributes(s[1].split("
		checkNotEmpty("attributes"

		if (attribute.equals(SquashOption.SQUASH_ATTR) && value instanceof SquashOption) {
			this.lockAndSquash(path
			return;
		}

		if (attribute.equals(FileSystemState.FILE_SYSTEM_STATE_ATTR)) {
			JGitFileSystem fileSystem = (JGitFileSystem) path.getFileSystem();
			try {
				fileSystem.lock();

				if (value instanceof CommentedOption) {
					fileSystem.setBatchCommitInfo("Batch mode"
					fileSystem.unlock();
					return;
				}

				final boolean isOriginalStateBatch = fileSystem.isOnBatch();

				fileSystem.setState(value.toString());
				FileSystemState.valueOf(value.toString());

				if (isOriginalStateBatch && !fileSystem.isOnBatch()) {
					fileSystem.setBatchCommitInfo(null);
					firePostponedBatchEvents(fileSystem);
					postCommitHook(fileSystem);
				}
				fileSystem.setHadCommitOnBatchState(false);
			} finally {
				fileSystem.unlock();
			}
			return;
		}

		final String[] s = split(attribute);
		if (s[0].length() == 0) {
			throw new IllegalArgumentException(attribute);
		}
		final ExtendedAttributeView view = getFileAttributeView(toPathImpl(path)
		if (view == null) {
			throw new UnsupportedOperationException("View '" + s[0] + "' not available");
		}

		view.setAttribute(s[1]
	}

	private void lockAndSquash(final Path path
		final JGitFileSystem fileSystem = (JGitFileSystem) path.getFileSystem();
		try {
			fileSystem.lock();
			final JGitPathImpl gSource = toPathImpl(path);
			String commitMessage = checkNotEmpty("commitMessage"
			String startCommit = checkNotEmpty("startCommit"
			cast(gSource.getFileSystem()).getGit().squash(gSource.getRefTree()
		} finally {
			fileSystem.unlock();
		}
	}

	private void checkURI(final String paramName
		checkNotNull("uri"

		if (uri.getAuthority() == null || uri.getAuthority().isEmpty()) {
			throw new IllegalArgumentException(
					"Parameter named '" + paramName + "' is invalid
		}

		int atIndex = uri.getPath().indexOf("@");
		if (atIndex != -1 && !uri.getAuthority().contains("@")) {
			if (uri.getPath().indexOf("/"
				throw new IllegalArgumentException(
						"Parameter named '" + paramName + "' is invalid
			}
		}
	}

	public String extractHostForPath(final URI uri) {
		checkNotNull("uri"

		int atIndex = uri.getPath().indexOf("@");
		if (atIndex != -1 && !uri.getAuthority().contains("@")) {
			return uri.getAuthority() + uri.getPath().substring(0
		}

		return uri.getAuthority();
	}

	private boolean hasSyncFlag(final URI uri) {
		checkNotNull("uri"

		return uri.getQuery() != null && uri.getQuery().contains("sync");
	}

	private boolean hasForceFlag(URI uri) {
		checkNotNull("uri"

		return uri.getQuery() != null && uri.getQuery().contains("force");
	}

	private boolean hasPushFlag(final URI uri) {
		checkNotNull("uri"

		return uri.getQuery() != null && uri.getQuery().contains("push");
	}

	private static Map<String
		final String[] params = uri.getQuery().split("&");
		return new HashMap<String
			{
				for (String param : params) {
					final String[] kv = param.split("=");
					final String name = kv[0];
					final String value;
					if (kv.length == 2) {
						value = kv[1];
					} else {
						value = "";
					}

					put(name
				}
			}
		};
	}

	private CredentialsProvider buildCredential(String username
		if (username != null) {
			if (password != null) {
				return new UsernamePasswordCredentialsProvider(username
			}
			return new UsernamePasswordCredentialsProvider(username
		}
		return CredentialsProvider.getDefault();
	}

	private JGitPathImpl toPathImpl(final Path path) {
		if (path instanceof JGitPathImpl) {
			return (JGitPathImpl) path;
		}
		throw new IllegalArgumentException("Path not supported by current provider.");
	}

	private String[] split(final String attribute) {
		final String[] s = new String[2];
		final int pos = attribute.indexOf(':');
		if (pos == -1) {
			s[0] = "basic";
			s[1] = attribute;
		} else {
			s[0] = attribute.substring(0
			s[1] = (pos == attribute.length()) ? "" : attribute.substring(pos + 1);
		}
		return s;
	}

	private int getSchemeSize(final URI uri) {
		if (uri.getScheme().equals(SCHEME)) {
			return SCHEME_SIZE;
		}
		return DEFAULT_SCHEME_SIZE;
	}

	private void delete(final JGitPathImpl path
		commit(path
			{
				put(path.getPath()
			}
		}));
	}

	private void commit(final JGitPathImpl path

		final JGitFileSystem fileSystem = (JGitFileSystem) path.getFileSystem();
		try {
			fileSystem.lock();

			final Git git = fileSystem.getGit();
			final String branchName = path.getRefTree();
			final boolean batchState = fileSystem.isOnBatch();
			final boolean amend = batchState && fileSystem.isHadCommitOnBatchState(path.getRoot());
			final ObjectId oldHead = cast(path.getFileSystem()).getGit().getTreeFromRef(branchName);

			final boolean hasCommit;
			if (batchState && fileSystem.getBatchCommitInfo() != null) {
				hasCommit = git.commit(branchName
			} else {
				hasCommit = git.commit(branchName
			}

			if (!batchState) {
				if (hasCommit) {
					int value = fileSystem.incrementAndGetCommitCount();
					if (value >= config.getCommitLimit()) {
						git.gc();
						fileSystem.resetCommitCount();
					}
				}

				final ObjectId newHead = cast(path.getFileSystem()).getGit().getTreeFromRef(branchName);

				postCommitHook(fileSystem);

				notifyDiffs((JGitFileSystem) path.getFileSystem()
						commitInfo.getName()
			} else {
				synchronized (postponedEventsLock) {

					String sessionId;
					String userName;
					String message;
					if (fileSystem.getBatchCommitInfo() != null) {
						sessionId = fileSystem.getBatchCommitInfo().getSessionId();
						userName = fileSystem.getBatchCommitInfo().getName();
						message = fileSystem.getBatchCommitInfo().getMessage();
					} else {
						sessionId = commitInfo.getSessionId();
						userName = commitInfo.getName();
						message = commitInfo.getMessage();
					}

					final ObjectId newHead = cast(path.getFileSystem()).getGit().getTreeFromRef(branchName);
					List<WatchEvent<?>> postponedWatchEvents = compareDiffs(cast(path.getFileSystem())
							sessionId

					fileSystem.addPostponedWatchEvents(postponedWatchEvents);
				}
			}

			if (cast(path.getFileSystem()).isOnBatch() && !fileSystem.isHadCommitOnBatchState(path.getRoot())) {
				fileSystem.setHadCommitOnBatchState(path.getRoot()
			}
		} finally {
			fileSystem.unlock();
		}
	}

	private void postCommitHook(final JGitFileSystem fileSystem) {

		ProcessResult result = detectedFS.runHookIfPresent(fileSystem.getGit().getRepository()
				new String[0]);

		if (result.getStatus().equals(ProcessResult.Status.OK)) {
			fileSystem.notifyPostCommit(result.getExitCode());
		}
	}

	private void firePostponedBatchEvents(JGitFileSystem fileSystem) {
		synchronized (postponedEventsLock) {

			if (fileSystem.hasPostponedEvents()) {
				fileSystem.publishEvents(fileSystem.getRootDirectories().iterator().next()
						fileSystem.getPostponedWatchEvents());
			}

			fileSystem.clearPostponedWatchEvents();

			int value = fileSystem.incrementAndGetCommitCount();
			if (value >= config.getCommitLimit()) {
				fileSystem.getGit().gc();
				fileSystem.resetCommitCount();
			}
		}
	}

	List<WatchEvent<?>> notifyDiffs(final JGitFileSystem fs
			final String userName

		List<WatchEvent<?>> watchEvents = compareDiffs(fs

		final String tree;
		if (_tree.startsWith("refs/")) {
			tree = _tree.substring(_tree.lastIndexOf("/") + 1);
		} else {
			tree = _tree;
		}

		final String host = tree + "@" + fs.getName();

		final Path root = JGitPathImpl.createRoot(fs
		if (!watchEvents.isEmpty()) {
			fs.publishEvents(root
		}
		return watchEvents;
	}

	List<WatchEvent<?>> compareDiffs(final JGitFileSystem fs
			final String userName

		final String tree;
		if (_tree.startsWith("refs/")) {
			tree = _tree.substring(_tree.lastIndexOf("/") + 1);
		} else {
			tree = _tree;
		}

		final String host = tree + "@" + fs.getName();

		final List<DiffEntry> diff = fs.getGit().listDiffs(oldHead
		final List<WatchEvent<?>> events = new ArrayList<>(diff.size());

		for (final DiffEntry diffEntry : diff) {
			final Path oldPath;
			if (!diffEntry.getOldPath().equals(DiffEntry.DEV_NULL)) {
				oldPath = JGitPathImpl.create(fs
			} else {
				oldPath = null;
			}

			final Path newPath;
			if (!diffEntry.getNewPath().equals(DiffEntry.DEV_NULL)) {
				final PathInfo pathInfo = fs.getGit().getPathInfo(tree
				newPath = JGitPathImpl.create(fs
			} else {
				newPath = null;
			}

			WatchEvent e = new JGitWatchEvent(sessionId
					newPath);
			events.add(e);
		}

		return events;
	}

	GitSSHService getGitSSHService() {
		return gitSSHService;
	}

	public JGitFileSystemProviderConfiguration getConfig() {
		return config;
	}

	private static class ExecutorWrapper implements Executor {

		private final ExecutorService simpleAsyncExecutor;

		public ExecutorWrapper(ExecutorService simpleAsyncExecutor) {
			this.simpleAsyncExecutor = checkNotNull("simpleAsyncExecutor"
		}

		@Override
		public void execute(Runnable command) {
			simpleAsyncExecutor.execute(command);
		}
	}

	public void setDetectedFS(final FS detectedFS) {
		this.detectedFS = detectedFS;
	}

	public JGitFileSystemsManager getFsManager() {
		return fsManager;
	}
}
