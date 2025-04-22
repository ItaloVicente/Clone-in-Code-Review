
package org.eclipse.jgit.internal.storage.dfs;

import static org.eclipse.jgit.internal.storage.dfs.DfsObjDatabase.PackSource.COMPACT;
import static org.eclipse.jgit.internal.storage.dfs.DfsObjDatabase.PackSource.INSERT;
import static org.eclipse.jgit.internal.storage.pack.PackExt.PACK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Before;
import org.junit.Test;

public class DfsPackCompacterTest {
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
	public void testEstimateCompactPackSizeInNewRepo() throws Exception {
		RevCommit commit0 = commit().message("0").create();
		RevCommit commit1 = commit().message("1").parent(commit0).create();
		git.update("master"

		long inputPacksSize = 0;
		assertEquals(2
		for (DfsPackFile pack : odb.getPacks()) {
			assertEquals(INSERT
			inputPacksSize += pack.getPackDescription().getFileSize(PACK);
		}

		compact();

		assertEquals(1
		DfsPackFile pack = odb.getPacks()[0];
		assertEquals(COMPACT
		assertEquals(inputPacksSize
				pack.getPackDescription().getEstimatedPackSize());
	}

	@Test
	public void testEstimateGcPackSizeWithAnExistingGcPack() throws Exception {
		RevCommit commit0 = commit().message("0").create();
		RevCommit commit1 = commit().message("1").parent(commit0).create();
		git.update("master"

		compact();

		RevCommit commit2 = commit().message("2").parent(commit1).create();
		git.update("master"

		assertEquals(2
		boolean compactPackFound = false;
		boolean insertPackFound = false;
		long inputPacksSize = 0;
		for (DfsPackFile pack : odb.getPacks()) {
			DfsPackDescription packDescription = pack.getPackDescription();
			if (packDescription.getPackSource() == COMPACT) {
				compactPackFound = true;
			}
			if (packDescription.getPackSource() == INSERT) {
				insertPackFound = true;
			}
			inputPacksSize += packDescription.getFileSize(PACK);
		}
		assertTrue(compactPackFound);
		assertTrue(insertPackFound);

		compact();

		DfsPackFile pack = odb.getPacks()[0];
		assertEquals(COMPACT
		assertEquals(inputPacksSize
				pack.getPackDescription().getEstimatedPackSize());
	}

	private TestRepository<InMemoryRepository>.CommitBuilder commit() {
		return git.commit();
	}

	private void compact() throws IOException {
		DfsPackCompactor compactor = new DfsPackCompactor(repo);
		compactor.autoAdd();
		compactor.compact(null);
		odb.clearCache();
	}
}
