
package org.eclipse.jgit.revwalk;

import static org.junit.Assert.assertSame;

import java.util.Date;

import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.junit.TestRepository.CommitBuilder;
import org.eclipse.jgit.lib.Repository;

public abstract class RevWalkTestCase extends RepositoryTestCase {
	private TestRepository<Repository> util;

	protected RevWalk rw;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		util = new TestRepository<>(db
		rw = util.getRevWalk();
	}

	protected RevWalk createRevWalk() {
		return new RevWalk(db);
	}

	protected Date getDate() {
		return util.getDate();
	}

	protected void tick(int secDelta) {
		util.tick(secDelta);
	}

	protected RevBlob blob(String content) throws Exception {
		return util.blob(content);
	}

	protected DirCacheEntry file(String path
			throws Exception {
		return util.file(path
	}

	protected RevTree tree(DirCacheEntry... entries) throws Exception {
		return util.tree(entries);
	}

	protected RevObject get(RevTree tree
			throws Exception {
		return util.get(tree
	}

	protected RevCommit commit(RevCommit... parents) throws Exception {
		return util.commit(parents);
	}

	protected RevCommit commit(RevTree tree
			throws Exception {
		return util.commit(tree
	}

	protected RevCommit commit(int secDelta
			throws Exception {
		return util.commit(secDelta
	}

	protected RevCommit commit(final int secDelta
			final RevCommit... parents) throws Exception {
		return util.commit(secDelta
	}

	protected RevTag tag(String name
			throws Exception {
		return util.tag(name
	}

	protected CommitBuilder commitBuilder()
			throws Exception {
		return util.commit();
	}

	protected <T extends RevObject> T parseBody(T t) throws Exception {
		return util.parseBody(t);
	}

	protected void markStart(RevCommit commit) throws Exception {
		rw.markStart(commit);
	}

	protected void markUninteresting(RevCommit commit) throws Exception {
		rw.markUninteresting(commit);
	}

	protected void assertCommit(RevCommit exp
		assertSame(exp
	}
}
