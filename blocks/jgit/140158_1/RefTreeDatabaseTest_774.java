
package org.eclipse.jgit.internal.storage.reftree;

import static org.eclipse.jgit.lib.Constants.HEAD;
import static org.eclipse.jgit.lib.Constants.MASTER;
import static org.eclipse.jgit.lib.Constants.ORIG_HEAD;
import static org.eclipse.jgit.lib.Constants.R_HEADS;
import static org.eclipse.jgit.lib.RefDatabase.ALL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.junit.Before;
import org.junit.Test;

public class LocalDiskRefTreeDatabaseTest extends LocalDiskRepositoryTestCase {
	private FileRepository repo;
	private RefTreeDatabase refdb;
	private RefDatabase bootstrap;

	private TestRepository<FileRepository> testRepo;
	private RevCommit A;
	private RevCommit B;

	@Override
	@Before
	public void setUp() throws Exception {
		FileRepository init = createWorkRepository();
		FileBasedConfig cfg = init.getConfig();
		cfg.setInt("core"
		cfg.setString("extensions"
		cfg.save();

		repo = (FileRepository) new FileRepositoryBuilder()
				.setGitDir(init.getDirectory())
				.build();
		refdb = (RefTreeDatabase) repo.getRefDatabase();
		bootstrap = refdb.getBootstrap();
		addRepoToClose(repo);

		RefUpdate head = refdb.newUpdate(HEAD
		head.link(R_HEADS + MASTER);

		testRepo = new TestRepository<>(init);
		A = testRepo.commit().create();
		B = testRepo.commit(testRepo.getRevWalk().parseCommit(A));
	}

	@Test
	public void testHeadOrigHead() throws IOException {
		RefUpdate master = refdb.newUpdate(HEAD
		master.setExpectedOldObjectId(ObjectId.zeroId());
		master.setNewObjectId(A);
		assertEquals(RefUpdate.Result.NEW
		assertEquals(A

		RefUpdate orig = refdb.newUpdate(ORIG_HEAD
		orig.setNewObjectId(B);
		assertEquals(RefUpdate.Result.NEW

		File origFile = new File(repo.getDirectory()
		assertEquals(B.name() + '\n'
		assertEquals(B
		assertEquals(B
		assertFalse(refdb.getRefs(ALL).containsKey(ORIG_HEAD));

		List<Ref> addl = refdb.getAdditionalRefs();
		assertEquals(2
		assertEquals(ORIG_HEAD
		assertEquals(B
	}
}
