package org.eclipse.jgit.lfs.server.fs;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.nio.file.Path;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.junit.JGitTestUtil;
import org.eclipse.jgit.lfs.CleanFilter;
import org.eclipse.jgit.lfs.SmudgeFilter;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.util.FileUtils;
import org.junit.Test;

public class CheckoutTest extends LfsServerTest {

	@Override
	public void setup() throws Exception {
		CleanFilter.register();
		SmudgeFilter.register();
		super.setup();

	}

	@Test
	public void testCheckout() throws Exception {
		Path tempDirectory = getTempDirectory();
		Path repoPath = tempDirectory.resolve("client");
		try (Git git = Git.init().setDirectory(repoPath.toFile()).setBare(false)
				.call()) {
			StoredConfig config = git.getRepository().getConfig();
			config.setString("lfs"
			config.setBoolean("filter"
			config.save();
			JGitTestUtil.writeTrashFile(git.getRepository()
					"*.txt filter=lfs");
			git.add().addFilepattern(".gitattributes").call();
			git.commit().setMessage("initial").call();

			JGitTestUtil.writeTrashFile(git.getRepository()
			git.add().addFilepattern("a.txt").call();
			RevCommit commit = git.commit().setMessage("add a").call();
			putContent(repoPath.resolve("a.txt"));

			JGitTestUtil.writeTrashFile(git.getRepository()
			git.add().addFilepattern("a.txt").call();
			git.commit().setMessage("modify a").call();

			FileUtils.delete(new File(repoPath.toFile()
					FileUtils.RECURSIVE);

			git.checkout().setName(commit.getName()).call();
			assertEquals("foo"
					JGitTestUtil.read(git.getRepository()

		}

	}
}
