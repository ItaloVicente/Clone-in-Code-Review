
package org.eclipse.jgit.util;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.errors.CommandFailedException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.treewalk.FileTreeIterator.FileEntry;
import org.eclipse.jgit.treewalk.FileTreeIterator.FileModeStrategy;
import org.eclipse.jgit.treewalk.WorkingTreeIterator.Entry;
import org.eclipse.jgit.util.ProcessResult.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class FS {
	private static final Logger LOG = LoggerFactory.getLogger(FS.class);

	protected static final Entry[] NO_ENTRIES = {};

	public static class FSFactory {
		protected FSFactory() {
		}

		public FS detect(Boolean cygwinUsed) {
			if (SystemReader.getInstance().isWindows()) {
				if (cygwinUsed == null)
					cygwinUsed = Boolean.valueOf(FS_Win32_Cygwin.isCygwin());
				if (cygwinUsed.booleanValue())
					return new FS_Win32_Cygwin();
				else
					return new FS_Win32();
			} else {
				return new FS_POSIX();
			}
		}
	}

	public static class ExecutionResult {
		private TemporaryBuffer stdout;

		private TemporaryBuffer stderr;

		private int rc;

		public ExecutionResult(TemporaryBuffer stdout
				int rc) {
			this.stdout = stdout;
			this.stderr = stderr;
			this.rc = rc;
		}

		public TemporaryBuffer getStdout() {
			return stdout;
		}

		public TemporaryBuffer getStderr() {
			return stderr;
		}

		public int getRc() {
			return rc;
		}
	}

	public static final FS DETECTED = detect();

	private volatile static FSFactory factory;

	public static FS detect() {
		return detect(null);
	}

	public static FS detect(Boolean cygwinUsed) {
		if (factory == null) {
			factory = new FS.FSFactory();
		}
		return factory.detect(cygwinUsed);
	}

	private volatile Holder<File> userHome;

	private volatile Holder<File> gitSystemConfig;

	protected FS() {
	}

	protected FS(FS src) {
		userHome = src.userHome;
		gitSystemConfig = src.gitSystemConfig;
	}

	public abstract FS newInstance();

	public abstract boolean supportsExecute();

	public boolean supportsAtomicCreateNewFile() {
		return true;
	}

	public boolean supportsSymlinks() {
		return false;
	}

	public abstract boolean isCaseSensitive();

	public abstract boolean canExecute(File f);

	public abstract boolean setExecute(File f

	public long lastModified(File f) throws IOException {
		return FileUtils.lastModified(f);
	}

	public void setLastModified(File f
		FileUtils.setLastModified(f
	}

	public long length(File path) throws IOException {
		return FileUtils.getLength(path);
	}

	public void delete(File f) throws IOException {
		FileUtils.delete(f);
	}

	public File resolve(File dir
		final File abspn = new File(name);
		if (abspn.isAbsolute())
			return abspn;
		return new File(dir
	}

	public File userHome() {
		Holder<File> p = userHome;
		if (p == null) {
			p = new Holder<>(userHomeImpl());
			userHome = p;
		}
		return p.value;
	}

	public FS setUserHome(File path) {
		userHome = new Holder<>(path);
		return this;
	}

	public abstract boolean retryFailedLockFileCommit();

	public BasicFileAttributes fileAttributes(File file) throws IOException {
		return FileUtils.fileAttributes(file);
	}

	protected File userHomeImpl() {
		final String home = AccessController
				.doPrivileged(new PrivilegedAction<String>() {
					@Override
					public String run() {
					}
				});
		if (home == null || home.length() == 0)
			return null;
		return new File(home).getAbsoluteFile();
	}

	protected static File searchPath(String path
		if (path == null)
			return null;

		for (String p : path.split(File.pathSeparator)) {
			for (String command : lookFor) {
				final File e = new File(p
				if (e.isFile())
					return e.getAbsoluteFile();
			}
		}
		return null;
	}

	@Nullable
	protected static String readPipe(File dir
			String encoding) throws CommandFailedException {
		return readPipe(dir
	}

	@Nullable
	protected static String readPipe(File dir
			String encoding
			throws CommandFailedException {
		final boolean debug = LOG.isDebugEnabled();
		try {
			if (debug) {
				LOG.debug("readpipe " + Arrays.asList(command) + "
					String l;
					while ((l = lineRead.readLine()) != null) {
						LOG.debug(l);
					}
				}
			}

			for (;;) {
				try {
					int rc = p.waitFor();
					gobbler.join();
					if (rc == 0 && !gobbler.fail.get()) {
						return r;
					} else {
						if (debug) {
						}
						throw new CommandFailedException(rc
								gobbler.errorMessage.get()
								gobbler.exception.get());
					}
				} catch (InterruptedException ie) {
				}
			}
		} catch (IOException e) {
			LOG.error("Caught exception in FS.readPipe()"
		}
		if (debug) {
		}
		return null;
	}

	private static class GobblerThread extends Thread {

		private static final int PROCESS_EXIT_TIMEOUT = 5;

		private final Process p;
		private final String desc;
		private final String dir;
		final AtomicBoolean fail = new AtomicBoolean();
		final AtomicReference<String> errorMessage = new AtomicReference<>();
		final AtomicReference<Throwable> exception = new AtomicReference<>();

		GobblerThread(Process p
			this.p = p;
			this.desc = Arrays.toString(command);
			this.dir = Objects.toString(dir);
		}

		@Override
		public void run() {
			StringBuilder err = new StringBuilder();
			try (InputStream is = p.getErrorStream()) {
				int ch;
				while ((ch = is.read()) != -1) {
					err.append((char) ch);
				}
			} catch (IOException e) {
				if (waitForProcessCompletion(e) && p.exitValue() != 0) {
					setError(e
					fail.set(true);
				} else {
				}
			} finally {
				if (waitForProcessCompletion(null) && err.length() > 0) {
					setError(null
					if (p.exitValue() != 0) {
						fail.set(true);
					}
				}
			}
		}

		@SuppressWarnings("boxing")
		private boolean waitForProcessCompletion(IOException originalError) {
			try {
				if (!p.waitFor(PROCESS_EXIT_TIMEOUT
					setError(originalError
							JGitText.get().commandClosedStderrButDidntExit
							desc
					fail.set(true);
					return false;
				}
			} catch (InterruptedException e) {
				setError(originalError
						JGitText.get().threadInterruptedWhileRunning
				fail.set(true);
				return false;
			}
			return true;
		}

		private void setError(IOException e
			exception.set(e);
			errorMessage.set(MessageFormat.format(
					JGitText.get().exceptionCaughtDuringExecutionOfCommand
					desc
		}
	}

	protected abstract File discoverGitExe();

	protected File discoverGitSystemConfig() {
		File gitExe = discoverGitExe();
		if (gitExe == null) {
			return null;
		}

		String v;
		try {
			v = readPipe(gitExe.getParentFile()
				new String[] { "git"
				Charset.defaultCharset().name());
		} catch (CommandFailedException e) {
			LOG.warn(e.getMessage());
			return null;
		}
		if (StringUtils.isEmptyOrNull(v)
			return null;
		}

		Map<String
		env.put("GIT_EDITOR"

		String w;
		try {
			w = readPipe(gitExe.getParentFile()
				new String[] { "git"
				Charset.defaultCharset().name()
		} catch (CommandFailedException e) {
			LOG.warn(e.getMessage());
			return null;
		}
		if (StringUtils.isEmptyOrNull(w)) {
			return null;
		}

		return new File(w);
	}

	public File getGitSystemConfig() {
		if (gitSystemConfig == null) {
			gitSystemConfig = new Holder<>(discoverGitSystemConfig());
		}
		return gitSystemConfig.value;
	}

	public FS setGitSystemConfig(File configFile) {
		gitSystemConfig = new Holder<>(configFile);
		return this;
	}

	protected static File resolveGrandparentFile(File grandchild) {
		if (grandchild != null) {
			File parent = grandchild.getParentFile();
			if (parent != null)
				return parent.getParentFile();
		}
		return null;
	}

	public String readSymLink(File path) throws IOException {
		return FileUtils.readSymLink(path);
	}

	public boolean isSymLink(File path) throws IOException {
		return FileUtils.isSymlink(path);
	}

	public boolean exists(File path) {
		return FileUtils.exists(path);
	}

	public boolean isDirectory(File path) {
		return FileUtils.isDirectory(path);
	}

	public boolean isFile(File path) {
		return FileUtils.isFile(path);
	}

	public boolean isHidden(File path) throws IOException {
		return FileUtils.isHidden(path);
	}

	public void setHidden(File path
		FileUtils.setHidden(path
	}

	public void createSymLink(File path
		FileUtils.createSymLink(path
	}

	@Deprecated
	public boolean createNewFile(File path) throws IOException {
		return path.createNewFile();
	}

	public static class LockToken implements Closeable {
		private boolean isCreated;

		private Optional<Path> link;

		LockToken(boolean isCreated
			this.isCreated = isCreated;
			this.link = link;
		}

		public boolean isCreated() {
			return isCreated;
		}

		@Override
		public void close() {
			if (!link.isPresent()) {
				return;
			}
			Path p = link.get();
			if (!Files.exists(p)) {
				return;
			}
			try {
				Files.delete(p);
			} catch (IOException e) {
				LOG.error(MessageFormat
						.format(JGitText.get().closeLockTokenFailed
			}
		}

		@Override
		public String toString() {
					"
		}
	}

	public LockToken createNewFileAtomic(File path) throws IOException {
		return new LockToken(path.createNewFile()
	}

	public String relativize(String base
		return FileUtils.relativizePath(base
	}

	public Entry[] list(File directory
		final File[] all = directory.listFiles();
		if (all == null) {
			return NO_ENTRIES;
		}
		final Entry[] result = new Entry[all.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = new FileEntry(all[i]
		}
		return result;
	}

	public ProcessResult runHookIfPresent(Repository repository
			final String hookName
			String[] args) throws JGitInternalException {
		return runHookIfPresent(repository
				null);
	}

	public ProcessResult runHookIfPresent(Repository repository
			final String hookName
			String[] args
			String stdinArgs) throws JGitInternalException {
		return new ProcessResult(Status.NOT_SUPPORTED);
	}

	protected ProcessResult internalRunHookIfPresent(Repository repository
			final String hookName
			PrintStream errRedirect
			throws JGitInternalException {
		final File hookFile = findHook(repository
		if (hookFile == null)
			return new ProcessResult(Status.NOT_PRESENT);

		final String hookPath = hookFile.getAbsolutePath();
		final File runDirectory;
		if (repository.isBare())
			runDirectory = repository.getDirectory();
		else
			runDirectory = repository.getWorkTree();
		final String cmd = relativize(runDirectory.getAbsolutePath()
				hookPath);
		ProcessBuilder hookProcess = runInShell(cmd
		hookProcess.directory(runDirectory);
		Map<String
		environment.put(Constants.GIT_DIR_KEY
				repository.getDirectory().getAbsolutePath());
		if (!repository.isBare()) {
			environment.put(Constants.GIT_WORK_TREE_KEY
					repository.getWorkTree().getAbsolutePath());
		}
		try {
			return new ProcessResult(runProcess(hookProcess
					errRedirect
		} catch (IOException e) {
			throw new JGitInternalException(MessageFormat.format(
					JGitText.get().exceptionCaughtDuringExecutionOfHook
					hookName)
		} catch (InterruptedException e) {
			throw new JGitInternalException(MessageFormat.format(
					JGitText.get().exceptionHookExecutionInterrupted
							hookName)
		}
	}


	public File findHook(Repository repository
		File gitDir = repository.getDirectory();
		if (gitDir == null)
			return null;
		final File hookFile = new File(new File(gitDir
				Constants.HOOKS)
		return hookFile.isFile() ? hookFile : null;
	}

	public int runProcess(ProcessBuilder processBuilder
			OutputStream outRedirect
			throws IOException
		InputStream in = (stdinArgs == null) ? null : new ByteArrayInputStream(
						stdinArgs.getBytes(UTF_8));
		return runProcess(processBuilder
	}

	public int runProcess(ProcessBuilder processBuilder
			OutputStream outRedirect
			InputStream inRedirect) throws IOException
			InterruptedException {
		final ExecutorService executor = Executors.newFixedThreadPool(2);
		Process process = null;
		IOException ioException = null;
		try {
			process = processBuilder.start();
			executor.execute(
					new StreamGobbler(process.getErrorStream()
			executor.execute(
					new StreamGobbler(process.getInputStream()
			OutputStream outputStream = process.getOutputStream();
			try {
				if (inRedirect != null) {
					new StreamGobbler(inRedirect
				}
			} finally {
				try {
					outputStream.close();
				} catch (IOException e) {
				}
			}
			return process.waitFor();
		} catch (IOException e) {
			ioException = e;
		} finally {
			shutdownAndAwaitTermination(executor);
			if (process != null) {
				try {
					process.waitFor();
				} catch (InterruptedException e) {
					Thread.interrupted();
				}
				if (inRedirect != null) {
					inRedirect.close();
				}
				try {
					process.getErrorStream().close();
				} catch (IOException e) {
					ioException = ioException != null ? ioException : e;
				}
				try {
					process.getInputStream().close();
				} catch (IOException e) {
					ioException = ioException != null ? ioException : e;
				}
				try {
					process.getOutputStream().close();
				} catch (IOException e) {
					ioException = ioException != null ? ioException : e;
				}
				process.destroy();
			}
		}
		throw ioException;
	}

	private static boolean shutdownAndAwaitTermination(ExecutorService pool) {
		boolean hasShutdown = true;
		try {
			if (!pool.awaitTermination(60
				if (!pool.awaitTermination(60
					hasShutdown = false;
			}
		} catch (InterruptedException ie) {
			pool.shutdownNow();
			Thread.currentThread().interrupt();
			hasShutdown = false;
		}
		return hasShutdown;
	}

	public abstract ProcessBuilder runInShell(String cmd

	public ExecutionResult execute(ProcessBuilder pb
			throws IOException
		try (TemporaryBuffer stdout = new TemporaryBuffer.LocalFile(null);
				TemporaryBuffer stderr = new TemporaryBuffer.Heap(1024
						1024 * 1024)) {
			int rc = runProcess(pb
			return new ExecutionResult(stdout
		}
	}

	private static class Holder<V> {
		final V value;

		Holder(V value) {
			this.value = value;
		}
	}

	public static class Attributes {

		public boolean isDirectory() {
			return isDirectory;
		}

		public boolean isExecutable() {
			return isExecutable;
		}

		public boolean isSymbolicLink() {
			return isSymbolicLink;
		}

		public boolean isRegularFile() {
			return isRegularFile;
		}

		public long getCreationTime() {
			return creationTime;
		}

		public long getLastModifiedTime() {
			return lastModifiedTime;
		}

		private final boolean isDirectory;

		private final boolean isSymbolicLink;

		private final boolean isRegularFile;

		private final long creationTime;

		private final long lastModifiedTime;

		private final boolean isExecutable;

		private final File file;

		private final boolean exists;

		protected long length = -1;

		final FS fs;

		Attributes(FS fs
				boolean isExecutable
				boolean isRegularFile
				long lastModifiedTime
			this.fs = fs;
			this.file = file;
			this.exists = exists;
			this.isDirectory = isDirectory;
			this.isExecutable = isExecutable;
			this.isSymbolicLink = isSymbolicLink;
			this.isRegularFile = isRegularFile;
			this.creationTime = creationTime;
			this.lastModifiedTime = lastModifiedTime;
			this.length = length;
		}

		public Attributes(File path
			this(fs
		}

		public long getLength() {
			if (length == -1)
				return length = file.length();
			return length;
		}

		public String getName() {
			return file.getName();
		}

		public File getFile() {
			return file;
		}

		boolean exists() {
			return exists;
		}
	}

	public Attributes getAttributes(File path) {
		boolean isDirectory = isDirectory(path);
		boolean isFile = !isDirectory && path.isFile();
		assert path.exists() == isDirectory || isFile;
		boolean exists = isDirectory || isFile;
		boolean canExecute = exists && !isDirectory && canExecute(path);
		boolean isSymlink = false;
		long lastModified = exists ? path.lastModified() : 0L;
		long createTime = 0L;
		return new Attributes(this
				isSymlink
	}

	public File normalize(File file) {
		return file;
	}

	public String normalize(String name) {
		return name;
	}

	private static class StreamGobbler implements Runnable {
		private InputStream in;

		private OutputStream out;

		public StreamGobbler(InputStream stream
			this.in = stream;
			this.out = output;
		}

		@Override
		public void run() {
			try {
				copy();
			} catch (IOException e) {
			}
		}

		void copy() throws IOException {
			boolean writeFailure = false;
			byte buffer[] = new byte[4096];
			int readBytes;
			while ((readBytes = in.read(buffer)) != -1) {
				if (!writeFailure && out != null) {
					try {
						out.write(buffer
						out.flush();
					} catch (IOException e) {
						writeFailure = true;
					}
				}
			}
		}
	}
}
