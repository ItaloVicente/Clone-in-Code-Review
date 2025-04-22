public abstract class RepositoryTestCase extends TestCase {

	protected final File trashParent = new File("trash");

	protected File trash;

	protected File trash_git;

	protected static final PersonIdent jauthor;

	protected static final PersonIdent jcommitter;

	static {
		jauthor = new PersonIdent("J. Author", "jauthor@example.com");
		jcommitter = new PersonIdent("J. Committer", "jcommitter@example.com");
	}

	protected boolean packedGitMMAP;

	/**
	 * Configure JGit before setting up test repositories.
	 */
	protected void configure() {
		final WindowCacheConfig c = new WindowCacheConfig();
		c.setPackedGitLimit(128 * WindowCacheConfig.KB);
		c.setPackedGitWindowSize(8 * WindowCacheConfig.KB);
		c.setPackedGitMMAP("true".equals(System.getProperty("jgit.junit.usemmap")));
		c.setDeltaBaseCacheLimit(8 * WindowCacheConfig.KB);
		WindowCache.reconfigure(c);
	}

	/**
	 * Utility method to delete a directory recursively. It is
	 * also used internally. If a file or directory cannot be removed
	 * it throws an AssertionFailure.
	 *
	 * @param dir
	 */
	protected void recursiveDelete(final File dir) {
		recursiveDelete(dir, false, getClass().getName() + "." + getName(), true);
	}

	protected static boolean recursiveDelete(final File dir, boolean silent,
			final String name, boolean failOnError) {
		assert !(silent && failOnError);
		if (!dir.exists())
			return silent;
		final File[] ls = dir.listFiles();
		if (ls != null) {
			for (int k = 0; k < ls.length; k++) {
				final File e = ls[k];
				if (e.isDirectory()) {
					silent = recursiveDelete(e, silent, name, failOnError);
				} else {
					if (!e.delete()) {
						if (!silent) {
							reportDeleteFailure(name, failOnError, e);
						}
						silent = !failOnError;
					}
				}
			}
		}
		if (!dir.delete()) {
			if (!silent) {
				reportDeleteFailure(name, failOnError, dir);
			}
			silent = !failOnError;
		}
		return silent;
	}

	private static void reportDeleteFailure(final String name,
			boolean failOnError, final File e) {
		String severity;
		if (failOnError)
			severity = "Error";
		else
			severity = "Warning";
		String msg = severity + ": Failed to delete " + e;
		if (name != null)
			msg += " in " + name;
		if (failOnError)
			fail(msg);
		else
			System.out.println(msg);
	}

