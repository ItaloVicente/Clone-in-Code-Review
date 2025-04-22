
package org.eclipse.jgit.internal.storage.file;

import java.io.IOException;

import org.eclipse.jgit.internal.storage.file.GC.RepoStatistics;
import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.junit.TestRepository.CommitBuilder;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.After;
import org.junit.Before;

public abstract class GcTestCase extends LocalDiskRepositoryTestCase {
	protected TestRepository<FileRepository> tr;
	protected FileRepository repo;
	protected GC gc;
	protected RepoStatistics stats;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		repo = createWorkRepository();
		tr = new TestRepository<FileRepository>((repo));
		gc = new GC(repo);
	}

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

	protected long lastModified(AnyObjectId objectId) {
		return repo.getObjectDatabase().fileFor(objectId).lastModified();
	}

	protected static void fsTick() throws InterruptedException
		RepositoryTestCase.fsTick(null);
	}
}
