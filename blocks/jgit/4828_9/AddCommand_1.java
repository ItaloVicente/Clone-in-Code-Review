package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.util.FS;
import org.junit.Test;

public class CommitCommandTest extends RepositoryTestCase {

	@Test
	public void testExecutableRetention() throws Exception {
		StoredConfig config = db.getConfig();
		config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_FILEMODE
		config.save();

		FS executableFs = new FS() {

			public boolean supportsExecute() {
				return true;
			}

			public boolean setExecute(File f
				return true;
			}

			public ProcessBuilder runInShell(String cmd
				return null;
			}

			public boolean retryFailedLockFileCommit() {
				return false;
			}

			public FS newInstance() {
				return this;
			}

			protected File discoverGitPrefix() {
				return null;
			}

			public boolean canExecute(File f) {
				return true;
			}
		};

		Git git = Git.open(db.getDirectory()
		String path = "a.txt";
		writeTrashFile(path
		git.add().addFilepattern(path).call();
		RevCommit commit1 = git.commit().setMessage("commit").call();
		TreeWalk walk = TreeWalk.forPath(db
		assertNotNull(walk);
		assertEquals(FileMode.EXECUTABLE_FILE

		FS nonExecutableFs = new FS() {

			public boolean supportsExecute() {
				return false;
			}

			public boolean setExecute(File f
				return false;
			}

			public ProcessBuilder runInShell(String cmd
				return null;
			}

			public boolean retryFailedLockFileCommit() {
				return false;
			}

			public FS newInstance() {
				return this;
			}

			protected File discoverGitPrefix() {
				return null;
			}

			public boolean canExecute(File f) {
				return false;
			}
		};

		config = db.getConfig();
		config.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_FILEMODE
		config.save();

		Git git2 = Git.open(db.getDirectory()
		writeTrashFile(path
		RevCommit commit2 = git2.commit().setOnly(path).setMessage("commit2")
				.call();
		walk = TreeWalk.forPath(db
		assertNotNull(walk);
		assertEquals(FileMode.EXECUTABLE_FILE
	}
}
