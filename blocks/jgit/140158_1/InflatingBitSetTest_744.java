
package org.eclipse.jgit.internal.storage.file;

import java.io.IOException;

import org.eclipse.jgit.internal.storage.file.GC.RepoStatistics;
import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.junit.TestRepository.CommitBuilder;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.junit.After;
import org.junit.Before;

public abstract class GcTestCase extends LocalDiskRepositoryTestCase {
	protected TestRepository<FileRepository> tr;
	protected FileRepository repo;
	protected GC gc;
	protected RepoStatistics stats;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		repo = createWorkRepository();
		tr = new TestRepository<>(repo
				mockSystemReader);
		gc = new GC(repo);
	}

	@Override
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	protected RevCommit commitChain(int depth) throws Exception {
		if (depth <= 0)
			throw new IllegalArgumentException("Chain depth must be > 0");
		CommitBuilder cb = tr.commit();
		RevCommit tip;
		do {
			--depth;
			tip = cb.add("a"
			cb = cb.child();
		} while (depth > 0);
		return tip;
	}

	protected RevCommit commitChain(int depth
		if (depth <= 0) {
			throw new IllegalArgumentException("Chain depth must be > 0");
		}
		if (width <= 0) {
			throw new IllegalArgumentException("Number of files per commit must be > 0");
		}
		CommitBuilder cb = tr.commit();
		RevCommit tip = null;
		do {
			--depth;
			for (int i=0; i < width; i++) {
				String id = depth + "-" + i;
				cb.add("a" + id
			}
			tip = cb.create();
			cb = cb.child();
		} while (depth > 0);
		return tip;
	}

	protected long lastModified(AnyObjectId objectId) throws IOException {
		return repo.getFS().lastModified(
				repo.getObjectDatabase().fileFor(objectId));
	}

	protected static void fsTick() throws InterruptedException
		RepositoryTestCase.fsTick(null);
	}
}
