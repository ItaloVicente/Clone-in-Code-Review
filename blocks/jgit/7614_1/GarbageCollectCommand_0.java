package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.storage.file.GC.RepoStatistics;
import org.eclipse.jgit.util.GitDateParser;
import org.junit.Before;
import org.junit.Test;

public class GarbageCollectCommandTest extends RepositoryTestCase {
	private Git git;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		git = new Git(db);
		String path = "a.txt";
		writeTrashFile(path
		git.add().addFilepattern(path).call();
		git.commit().setMessage("commit").call();
	}

	@Test
	public void testGConeCommit() throws Exception {
		Date expire = GitDateParser.parse("now"
		GarbageCollectResult res = git.garbageCollect().setExpire(expire)
				.call();
		RepoStatistics preStats = res.getPreRepositoryStatistics();
		RepoStatistics postStats = res.getPostRepositoryStatistics();
		checkRepoStatistics(preStats
		checkRepoStatistics(postStats
	}

	@Test
	public void testGCmoreCommits() throws Exception {
		writeTrashFile("a.txt"
		writeTrashFile("b.txt"
		writeTrashFile("c.txt"
		git.commit().setAll(true).setMessage("commit2").call();
		writeTrashFile("a.txt"
		writeTrashFile("b.txt"
		writeTrashFile("c.txt"
		git.commit().setAll(true).setMessage("commit3").call();
		GarbageCollectResult res = git.garbageCollect()
				.setExpire(GitDateParser.parse("now"
				.call();
		RepoStatistics preStats = res.getPreRepositoryStatistics();
		RepoStatistics postStats = res.getPostRepositoryStatistics();
		checkRepoStatistics(preStats
		checkRepoStatistics(postStats
	}

	private void checkRepoStatistics(RepoStatistics preStats
			long looseObjects
			long packedRefs
			long sizePackedObjects) {
		assertEquals(looseObjects
		assertEquals(looseRefs
		assertEquals(packedObjects
		assertEquals(packedRefs
		assertEquals(packFiles
		assertEquals(sizeLooseObjects
		assertEquals(sizePackedObjects
	}

}
