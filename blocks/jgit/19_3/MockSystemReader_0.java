
package org.eclipse.jgit.junit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

import org.eclipse.jgit.lib.FileBasedConfig;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryCache;
import org.eclipse.jgit.lib.WindowCache;
import org.eclipse.jgit.lib.WindowCacheConfig;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.SystemReader;

public abstract class LocalDiskRepositoryTestCase extends TestCase {
	private static Thread shutdownHook;

	private static int testCount;

	private static final boolean useMMAP = "true".equals(System
			.getProperty("jgit.junit.usemmap"));

	protected PersonIdent author;

	protected PersonIdent committer;

	private final File trash = new File(new File("target")

	private final List<Repository> toClose = new ArrayList<Repository>();

	private MockSystemReader mockSystemReader;

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		if (shutdownHook == null) {
			shutdownHook = new Thread() {
				@Override
				public void run() {
					System.gc();
					recursiveDelete("SHUTDOWN"
				}
			};
			Runtime.getRuntime().addShutdownHook(shutdownHook);
		}

		recursiveDelete(testName()

		mockSystemReader = new MockSystemReader();
		mockSystemReader.userGitConfig = new FileBasedConfig(new File(trash
				"usergitconfig"));
		SystemReader.setInstance(mockSystemReader);

		final long now = mockSystemReader.getCurrentTime();
		final int tz = mockSystemReader.getTimezone(now);
		author = new PersonIdent("J. Author"
		author = new PersonIdent(author

		committer = new PersonIdent("J. Committer"
		committer = new PersonIdent(committer

		final WindowCacheConfig c = new WindowCacheConfig();
		c.setPackedGitLimit(128 * WindowCacheConfig.KB);
		c.setPackedGitWindowSize(8 * WindowCacheConfig.KB);
		c.setPackedGitMMAP(useMMAP);
		c.setDeltaBaseCacheLimit(8 * WindowCacheConfig.KB);
		WindowCache.reconfigure(c);
	}

	@Override
	protected void tearDown() throws Exception {
		RepositoryCache.clear();
		for (Repository r : toClose)
			r.close();
		toClose.clear();

		if (useMMAP)
			System.gc();

		recursiveDelete(testName()
		super.tearDown();
	}

	protected void tick() {
		final long delta = TimeUnit.MILLISECONDS.convert(5 * 60
				TimeUnit.SECONDS);
		final long now = author.getWhen().getTime() + delta;
		final int tz = mockSystemReader.getTimezone(now);

		author = new PersonIdent(author
		committer = new PersonIdent(committer
	}

	protected void recursiveDelete(final File dir) {
		recursiveDelete(testName()
	}

	private static boolean recursiveDelete(final String testName
			final File dir
		assert !(silent && failOnError);
		if (!dir.exists()) {
			return silent;
		}
		final File[] ls = dir.listFiles();
		if (ls != null) {
			for (int k = 0; k < ls.length; k++) {
				final File e = ls[k];
				if (e.isDirectory()) {
					silent = recursiveDelete(testName
				} else {
					if (!e.delete()) {
						if (!silent) {
							reportDeleteFailure(testName
						}
						silent = !failOnError;
					}
				}
			}
		}
		if (!dir.delete()) {
			if (!silent) {
				reportDeleteFailure(testName
			}
			silent = !failOnError;
		}
		return silent;
	}

	private static void reportDeleteFailure(final String testName
			final boolean failOnError
		final String severity;
		if (failOnError)
			severity = "ERROR";
		else
			severity = "WARNING";

		final String msg = severity + ": Failed to delete " + e + " in "
				+ testName;
		if (failOnError)
			fail(msg);
		else
			System.err.println(msg);
	}

	protected Repository createBareRepository() throws IOException {
	}

	protected Repository createWorkRepository() throws IOException {
	}

	private Repository createRepository(boolean bare) throws IOException {
		String uniqueId = System.currentTimeMillis() + "_" + (testCount++);
		String gitdirName = "test" + uniqueId + (bare ? "" : "/") + ".git";
		File gitdir = new File(trash
		Repository db = new Repository(gitdir);

		assertFalse(gitdir.exists());
		db.create();
		toClose.add(db);
		return db;
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

		final File cwd = db.getWorkDir();
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

	protected File write(final String body) throws IOException {
		final File f = File.createTempFile("temp"
		try {
			write(f
			return f;
		} catch (Error e) {
			f.delete();
			throw e;
		} catch (RuntimeException e) {
			f.delete();
			throw e;
		} catch (IOException e) {
			f.delete();
			throw e;
		}
	}

	protected void write(final File f
		f.getParentFile().mkdirs();
		Writer w = new OutputStreamWriter(new FileOutputStream(f)
		try {
			w.write(body);
		} finally {
			w.close();
		}
	}

	protected String read(final File f) throws IOException {
		final byte[] body = IO.readFully(f);
		return new String(body
	}

	private static String[] toEnvArray(final Map<String
		final String[] envp = new String[env.size()];
		int i = 0;
		for (Map.Entry<String
			envp[i++] = e.getKey() + "=" + e.getValue();
		}
		return envp;
	}

	private static HashMap<String
		return new HashMap<String
	}

	private String testName() {
		return getClass().getName() + "." + getName();
	}
}
