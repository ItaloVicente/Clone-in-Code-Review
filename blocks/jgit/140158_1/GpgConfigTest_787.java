package org.eclipse.jgit.lib;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FileUtils;
import org.junit.Before;
import org.junit.Test;

public class DirCacheCheckoutTestWithSymlinks extends RepositoryTestCase {
	@Before
	public void beforeMethod() {
		org.junit.Assume.assumeTrue(FS.DETECTED.supportsSymlinks());
	}

	@Test
	public void testDontDeleteSymlinkOnTopOfRootDir() throws Exception {
		File repos = createTempDirectory("repos");
		File testRepo = new File(repos
		testRepo.mkdirs();
		Git git = Git.init().setDirectory(testRepo).call();
		db = (FileRepository) git.getRepository();

		writeTrashFile("d/f"
		git.add().addFilepattern(".").call();
		RevCommit initial = git.commit().setMessage("inital").call();
		git.rm().addFilepattern("d/f").call();
		git.commit().setMessage("modifyOnMaster").call();
		git.checkout().setCreateBranch(true).setName("side")
				.setStartPoint(initial).call();
		writeTrashFile("d/f"
		git.add().addFilepattern(".").call();
		git.commit().setMessage("modifyOnSide").call();
		git.getRepository().close();

		File reposSymlink = createTempFile();
		FileUtils.createSymLink(reposSymlink

		Repository symlinkDB = FileRepositoryBuilder.create(new File(
				reposSymlink
		Git symlinkRepo = Git.wrap(symlinkDB);
		symlinkRepo.checkout().setName("master").call();

		assertTrue("The symlink to the repo should exist after a checkout"
				reposSymlink.exists());
	}
}
