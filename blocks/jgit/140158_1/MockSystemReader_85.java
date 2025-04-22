
package org.eclipse.jgit.junit;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryCache;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.storage.file.WindowCacheConfig;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.SystemReader;
import org.junit.After;
import org.junit.Before;

public abstract class LocalDiskRepositoryTestCase {
	private static final boolean useMMAP = "true".equals(System
			.getProperty("jgit.junit.usemmap"));

	protected PersonIdent author;

	protected PersonIdent committer;

	protected MockSystemReader mockSystemReader;

	private final Set<Repository> toClose = new HashSet<>();
	private File tmp;

	@Before
	public void setUp() throws Exception {
		tmp = File.createTempFile("jgit_test_"
		CleanupThread.deleteOnShutdown(tmp);
		if (!tmp.delete() || !tmp.mkdir())
			throw new IOException("Cannot create " + tmp);

		mockSystemReader = new MockSystemReader();
		mockSystemReader.userGitConfig = new FileBasedConfig(new File(tmp
				"usergitconfig")
		mockSystemReader.userGitConfig.setBoolean(ConfigConstants.CONFIG_GC_SECTION
				null
		mockSystemReader.userGitConfig.save();
		ceilTestDirectories(getCeilings());
		SystemReader.setInstance(mockSystemReader);

		author = new PersonIdent("J. Author"
		committer = new PersonIdent("J. Committer"

		final WindowCacheConfig c = new WindowCacheConfig();
		c.setPackedGitLimit(128 * WindowCacheConfig.KB);
		c.setPackedGitWindowSize(8 * WindowCacheConfig.KB);
		c.setPackedGitMMAP(useMMAP);
		c.setDeltaBaseCacheLimit(8 * WindowCacheConfig.KB);
		c.install();
	}

	protected File getTemporaryDirectory() {
		return tmp.getAbsoluteFile();
	}

	protected List<File> getCeilings() {
		return Collections.singletonList(getTemporaryDirectory());
	}

	private void ceilTestDirectories(List<File> ceilings) {
		mockSystemReader.setProperty(Constants.GIT_CEILING_DIRECTORIES_KEY
	}

	private static String makePath(List<?> objects) {
		final StringBuilder stringBuilder = new StringBuilder();
		for (Object object : objects) {
			if (stringBuilder.length() > 0)
				stringBuilder.append(File.pathSeparatorChar);
			stringBuilder.append(object.toString());
		}
		return stringBuilder.toString();
	}

	@After
	public void tearDown() throws Exception {
		RepositoryCache.clear();
		for (Repository r : toClose)
			r.close();
		toClose.clear();

		if (useMMAP)
			System.gc();
		if (tmp != null)
			recursiveDelete(tmp
		if (tmp != null && !tmp.exists())
			CleanupThread.removed(tmp);

		SystemReader.setInstance(null);
	}

	protected void tick() {
		mockSystemReader.tick(5 * 60);
		final long now = mockSystemReader.getCurrentTime();
		final int tz = mockSystemReader.getTimezone(now);

		author = new PersonIdent(author
		committer = new PersonIdent(committer
	}

	protected void recursiveDelete(File dir) {
		recursiveDelete(dir
	}

	private static boolean recursiveDelete(final File dir
			boolean silent
		assert !(silent && failOnError);
		if (!dir.exists())
			return silent;
		final File[] ls = dir.listFiles();
		if (ls != null)
			for (File e : ls) {
                            if (e.isDirectory())
                                silent = recursiveDelete(e
                            else if (!e.delete()) {
                                if (!silent)
                                    reportDeleteFailure(failOnError
                                silent = !failOnError;
                            }
                }
		if (!dir.delete()) {
			if (!silent)
				reportDeleteFailure(failOnError
			silent = !failOnError;
		}
		return silent;
	}

	private static void reportDeleteFailure(boolean failOnError
		String severity = failOnError ? "ERROR" : "WARNING";
		String msg = severity + ": Failed to delete " + e;
		if (failOnError)
			fail(msg);
		else
			System.err.println(msg);
	}

	public static final int MOD_TIME = 1;

	public static final int SMUDGE = 2;

	public static final int LENGTH = 4;

	public static final int CONTENT_ID = 8;

	public static final int CONTENT = 16;

	public static final int ASSUME_UNCHANGED = 32;

	public static String indexState(Repository repo
			throws IllegalStateException
		DirCache dc = repo.readDirCache();
		StringBuilder sb = new StringBuilder();
		TreeSet<Long> timeStamps = new TreeSet<>();

		if (0 != (includedOptions & MOD_TIME)) {
			for (int i=0; i<dc.getEntryCount(); ++i)
				timeStamps.add(Long.valueOf(dc.getEntry(i).getLastModified()));
		}

		for (int i=0; i<dc.getEntryCount(); ++i) {
			DirCacheEntry entry = dc.getEntry(i);
			sb.append("["+entry.getPathString()+"
			int stage = entry.getStage();
			if (stage != 0)
				sb.append("
			if (0 != (includedOptions & MOD_TIME)) {
				sb.append("
						timeStamps.headSet(Long.valueOf(entry.getLastModified())).size());
			}
			if (0 != (includedOptions & SMUDGE))
				if (entry.isSmudged())
					sb.append("
			if (0 != (includedOptions & LENGTH))
				sb.append("
						+ Integer.toString(entry.getLength()));
			if (0 != (includedOptions & CONTENT_ID))
				sb.append("
			if (0 != (includedOptions & CONTENT)) {
				sb.append("
						+ new String(repo.open(entry.getObjectId()
								Constants.OBJ_BLOB).getCachedBytes()
			}
			if (0 != (includedOptions & ASSUME_UNCHANGED))
				sb.append("
						+ Boolean.toString(entry.isAssumeValid()));
			sb.append("]");
		}
		return sb.toString();
	}


	protected FileRepository createBareRepository() throws IOException {
	}

	protected FileRepository createWorkRepository() throws IOException {
	}

	protected FileRepository createRepository(boolean bare)
			throws IOException {
		return createRepository(bare
	}

	@Deprecated
	public FileRepository createRepository(boolean bare
			throws IOException {
		File gitdir = createUniqueTestGitDir(bare);
		FileRepository db = new FileRepository(gitdir);
		assertFalse(gitdir.exists());
		db.create(bare);
		if (autoClose) {
			addRepoToClose(db);
		}
		return db;
	}

	public void addRepoToClose(Repository r) {
		toClose.add(r);
	}

	protected File createTempDirectory(String name) throws IOException {
		File directory = new File(createTempFile()
		FileUtils.mkdirs(directory);
		return directory.getCanonicalFile();
	}

	protected File createUniqueTestGitDir(boolean bare) throws IOException {
		String gitdirName = createTempFile().getPath();
		if (!bare)
			gitdirName += "/";
		return new File(gitdirName + Constants.DOT_GIT);
	}

	protected File createTempFile() throws IOException {
		File p = File.createTempFile("tmp_"
		if (!p.delete()) {
			throw new IOException("Cannot obtain unique path " + tmp);
		}
		return p;
	}

	protected int runHook(final Repository db
			final String... args) throws IOException
		final String[] argv = new String[1 + args.length];
		argv[0] = hook.getAbsolutePath();
		System.arraycopy(args

		final Map<String
		env.put("GIT_DIR"
		putPersonIdent(env
		putPersonIdent(env

		final File cwd = db.getWorkTree();
		final Process p = Runtime.getRuntime().exec(argv
		p.getOutputStream().close();
		p.getErrorStream().close();
		p.getInputStream().close();
		return p.waitFor();
	}

	private static void putPersonIdent(final Map<String
			final String type
		final String ident = who.toExternalString();
		final String date = ident.substring(ident.indexOf("> ") + 2);
		env.put("GIT_" + type + "_NAME"
		env.put("GIT_" + type + "_EMAIL"
		env.put("GIT_" + type + "_DATE"
	}

	protected File write(String body) throws IOException {
		final File f = File.createTempFile("temp"
		try {
			write(f
			return f;
		} catch (Error | RuntimeException | IOException e) {
			f.delete();
			throw e;
		}
	}

	protected void write(File f
		JGitTestUtil.write(f
	}

	protected String read(File f) throws IOException {
		return JGitTestUtil.read(f);
	}

	private static String[] toEnvArray(Map<String
		final String[] envp = new String[env.size()];
		int i = 0;
		for (Map.Entry<String
			envp[i++] = e.getKey() + "=" + e.getValue();
		return envp;
	}

	private static HashMap<String
		return new HashMap<>(System.getenv());
	}

	private static final class CleanupThread extends Thread {
		private static final CleanupThread me;
		static {
			me = new CleanupThread();
			Runtime.getRuntime().addShutdownHook(me);
		}

		static void deleteOnShutdown(File tmp) {
			synchronized (me) {
				me.toDelete.add(tmp);
			}
		}

		static void removed(File tmp) {
			synchronized (me) {
				me.toDelete.remove(tmp);
			}
		}

		private final List<File> toDelete = new ArrayList<>();

		@Override
		public void run() {
			System.gc();
			synchronized (this) {
				boolean silent = false;
				boolean failOnError = false;
				for (File tmp : toDelete)
					recursiveDelete(tmp
			}
		}
	}
}
