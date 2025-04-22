
package org.eclipse.jgit.internal.storage.pack;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import org.eclipse.jgit.internal.storage.dfs.DfsFsck;
import org.eclipse.jgit.internal.storage.dfs.DfsObjDatabase;
import org.eclipse.jgit.internal.storage.dfs.DfsRepositoryDescription;
import org.eclipse.jgit.internal.storage.dfs.InMemoryRepository;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Before;
import org.junit.Test;

public class DfsFsckTest {
	private TestRepository<InMemoryRepository> git;

	private InMemoryRepository repo;

	private DfsObjDatabase odb;

	@Before
	public void setUp() throws IOException {
		DfsRepositoryDescription desc = new DfsRepositoryDescription("test");
		git = new TestRepository<>(new InMemoryRepository(desc));
		repo = git.getRepository();
		odb = repo.getObjectDatabase();
	}

	@Test
	public void testHealthyRepo() throws Exception {
		RevCommit commit0 = git.commit().message("0").create();
		RevCommit commit1 = git.commit().message("1").parent(commit0).create();
		git.update("master"

		DfsFsck fsck = new DfsFsck(repo);
		fsck.check(null);

		assertEquals(fsck.getCorruptObjects().size()
		assertEquals(fsck.getMissingObjects().size()
		assertEquals(fsck.getCorruptedIndex().size()
	}
}
