package org.eclipse.jgit.internal.storage.dfs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Ref.Storage;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTag;
import org.junit.Test;

public class InMemoryRepositoryTest {

	@Test
	public void keepUpdateIndexPeelingTag() throws Exception {
		InMemoryRepository repo = new InMemoryRepository(
				new DfsRepositoryDescription());
		try (TestRepository<InMemoryRepository> git = new TestRepository<>(
				repo)) {
			RevCommit commit = git.branch("master").commit()
					.message("first commit").create();
			RevTag tag = git.tag("v0.1"
			git.update("refs/tags/v0.1"

			Ref unpeeledTag = new ObjectIdRef.Unpeeled(Storage.LOOSE
					"refs/tags/v0.1"

			Ref peeledTag = repo.getRefDatabase().peel(unpeeledTag);
			assertTrue(peeledTag instanceof ObjectIdRef.PeeledTag);
			assertEquals(1000
		}
	}

	@Test
	public void keepUpdateIndexPeelingNonTag() throws Exception {
		InMemoryRepository repo = new InMemoryRepository(
				new DfsRepositoryDescription());
		try (TestRepository<InMemoryRepository> git = new TestRepository<>(
				repo)) {
			RevCommit commit = git.branch("master").commit()
					.message("first commit").create();

			Ref unpeeledRef = new ObjectIdRef.Unpeeled(Storage.LOOSE
					"refs/heads/master"
			Ref peeledRef = repo.getRefDatabase().peel(unpeeledRef);
			assertTrue(peeledRef instanceof ObjectIdRef.PeeledNonTag);
			assertEquals(1000
		}
	}
}
