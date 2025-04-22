package org.eclipse.jgit.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.errors.CommandFailedException;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FS_POSIX extends FS {
	private final static Logger LOG = LoggerFactory.getLogger(FS_POSIX.class);

	private static final int DEFAULT_UMASK = 0022;
	private volatile int umask = -1;

	private volatile boolean supportsUnixNLink = true;

	private volatile AtomicFileCreation supportsAtomicCreateNewFile = AtomicFileCreation.UNDEFINED;

	private enum AtomicFileCreation {
		SUPPORTED
	}

	protected FS_POSIX() {
	}

	protected FS_POSIX(FS src) {
		super(src);
		if (src instanceof FS_POSIX) {
			umask = ((FS_POSIX) src).umask;
		}
	}

	private void determineAtomicFileCreationSupport() {
		AtomicFileCreation ret = getAtomicFileCreationSupportOption(
				SystemReader.getInstance().openUserConfig(null
		if (ret == AtomicFileCreation.UNDEFINED
				&& StringUtils.isEmptyOrNull(SystemReader.getInstance()
						.getenv(Constants.GIT_CONFIG_NOSYSTEM_KEY))) {
			ret = getAtomicFileCreationSupportOption(
					SystemReader.getInstance().openSystemConfig(null
		}
		supportsAtomicCreateNewFile = ret;
	}

	private AtomicFileCreation getAtomicFileCreationSupportOption(
			FileBasedConfig config) {
		try {
			config.load();
			String value = config.getString(ConfigConstants.CONFIG_CORE_SECTION
					null
					ConfigConstants.CONFIG_KEY_SUPPORTSATOMICFILECREATION);
			if (value == null) {
				return AtomicFileCreation.UNDEFINED;
			}
			return StringUtils.toBoolean(value)
					? AtomicFileCreation.SUPPORTED
					: AtomicFileCreation.NOT_SUPPORTED;
		} catch (IOException | ConfigInvalidException e) {
			return AtomicFileCreation.SUPPORTED;
		}
	}

	@Override
	public FS newInstance() {
		return new FS_POSIX(this);
	}

	public void setUmask(int umask) {
		this.umask = umask;
	}

	private int umask() {
		int u = umask;
		if (u == -1) {
			u = readUmask();
			umask = u;
		}
		return u;
	}

	private static int readUmask() {
		try {
			Process p = Runtime.getRuntime().exec(
					new String[] { "sh"
					null
			try (BufferedReader lineRead = new BufferedReader(
					new InputStreamReader(p.getInputStream()
							.defaultCharset().name()))) {
				if (p.waitFor() == 0) {
					String s = lineRead.readLine();
						return Integer.parseInt(s
					}
				}
				return DEFAULT_UMASK;
			}
		} catch (Exception e) {
			return DEFAULT_UMASK;
		}
	}

	@Override
	protected File discoverGitExe() {
		File gitExe = searchPath(path

		if (gitExe == null) {
			if (SystemReader.getInstance().isMacOS()) {
				if (searchPath(path
					String w;
					try {
						w = readPipe(userHome()
							new String[]{"bash"
							Charset.defaultCharset().name());
					} catch (CommandFailedException e) {
						LOG.warn(e.getMessage());
						return null;
					}
					if (!StringUtils.isEmptyOrNull(w)) {
						gitExe = new File(w);
					}
				}
			}
		}

		return gitExe;
	}

	@Override
	public boolean isCaseSensitive() {
		return !SystemReader.getInstance().isMacOS();
	}

	@Override
	public boolean supportsExecute() {
		return true;
	}

	@Override
	public boolean canExecute(File f) {
		return FileUtils.canExecute(f);
	}

	@Override
	public boolean setExecute(File f
		if (!isFile(f))
			return false;
		if (!canExecute)
			return f.setExecutable(false

		try {
			Path path = FileUtils.toPath(f);
			Set<PosixFilePermission> pset = Files.getPosixFilePermissions(path);

			pset.add(PosixFilePermission.OWNER_EXECUTE);

			int mask = umask();
			apply(pset
			apply(pset
			Files.setPosixFilePermissions(path
			return true;
		} catch (IOException e) {
			final boolean debug = Boolean.parseBoolean(SystemReader
			if (debug)
				System.err.println(e);
			return false;
		}
	}

	private static void apply(Set<PosixFilePermission> set
			int umask
		if ((umask & test) == 0) {
			set.add(perm);
		} else {
			set.remove(perm);
		}
	}

	@Override
	public ProcessBuilder runInShell(String cmd
		List<String> argv = new ArrayList<>(4 + args.length);
		argv.add(cmd);
		argv.addAll(Arrays.asList(args));
		ProcessBuilder proc = new ProcessBuilder();
		proc.command(argv);
		return proc;
	}

	@Override
	public ProcessResult runHookIfPresent(Repository repository
			String[] args
			String stdinArgs) throws JGitInternalException {
		return internalRunHookIfPresent(repository
				errRedirect
	}

	@Override
	public boolean retryFailedLockFileCommit() {
		return false;
	}

	@Override
	public boolean supportsSymlinks() {
		return true;
	}

	@Override
	public void setHidden(File path
	}

	@Override
	public Attributes getAttributes(File path) {
		return FileUtils.getFileAttributesPosix(this
	}

	@Override
	public File normalize(File file) {
		return FileUtils.normalize(file);
	}

	@Override
	public String normalize(String name) {
		return FileUtils.normalize(name);
	}

	@Override
	public File findHook(Repository repository
		final File gitdir = repository.getDirectory();
		if (gitdir == null) {
			return null;
		}
		final Path hookPath = gitdir.toPath().resolve(Constants.HOOKS)
				.resolve(hookName);
		if (Files.isExecutable(hookPath))
			return hookPath.toFile();
		return null;
	}

	@Override
	public boolean supportsAtomicCreateNewFile() {
		if (supportsAtomicCreateNewFile == AtomicFileCreation.UNDEFINED) {
			determineAtomicFileCreationSupport();
		}
		return supportsAtomicCreateNewFile == AtomicFileCreation.SUPPORTED;
	}

	@Override
	@SuppressWarnings("boxing")
	@Deprecated
	public boolean createNewFile(File lock) throws IOException {
		if (!lock.createNewFile()) {
			return false;
		}
		if (supportsAtomicCreateNewFile() || !supportsUnixNLink) {
			return true;
		}
		Path lockPath = lock.toPath();
		Path link = null;
		try {
			link = Files.createLink(
					Paths.get(lock.getAbsolutePath() + ".lnk")
					lockPath);
			Integer nlink = (Integer) (Files.getAttribute(lockPath
			if (nlink > 2) {
				LOG.warn(MessageFormat.format(
						JGitText.get().failedAtomicFileCreation
						nlink));
				return false;
			} else if (nlink < 2) {
				supportsUnixNLink = false;
			}
			return true;
		} catch (UnsupportedOperationException | IllegalArgumentException e) {
			supportsUnixNLink = false;
			return true;
		} finally {
			if (link != null) {
				Files.delete(link);
			}
		}
	}

	@Override
	public LockToken createNewFileAtomic(File file) throws IOException {
		if (!file.createNewFile()) {
			return token(false
		}
		if (supportsAtomicCreateNewFile() || !supportsUnixNLink) {
			return token(true
		}
		Path link = null;
		Path path = file.toPath();
		try {
			link = Files.createLink(Paths.get(uniqueLinkPath(file))
			Integer nlink = (Integer) (Files.getAttribute(path
			if (nlink.intValue() > 2) {
				LOG.warn(MessageFormat.format(
						JGitText.get().failedAtomicFileCreation
				return token(false
			} else if (nlink.intValue() < 2) {
				supportsUnixNLink = false;
			}
			return token(true
		} catch (UnsupportedOperationException | IllegalArgumentException
				| AccessDeniedException | SecurityException e) {
			supportsUnixNLink = false;
			return token(true
		}
	}

	private static LockToken token(boolean created
		return ((p != null) && Files.exists(p))
				? new LockToken(created
				: new LockToken(created
	}

	private static String uniqueLinkPath(File file) {
		UUID id = UUID.randomUUID();
				+ Long.toHexString(id.getMostSignificantBits())
				+ Long.toHexString(id.getLeastSignificantBits());
	}
}
