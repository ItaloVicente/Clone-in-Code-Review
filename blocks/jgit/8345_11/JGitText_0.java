
package org.eclipse.jgit.revwalk;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.junit.JGitTestUtil;
import org.eclipse.jgit.lib.*;
import org.junit.*;

import static org.junit.Assert.*;

public class RevWalkShallowTest extends RevWalkTestCase {


	@Test
	public void testDepth1() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(b);
		final RevCommit d = commit(c);

		createShallowFile(d);

		rw.reset();
		markStart(d);
		assertCommit(d
		assertNull(rw.next());
	}

	@Test
	public void testDepth2() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(b);
		final RevCommit d = commit(c);

		createShallowFile(c);

		rw.reset();
		markStart(d);
		assertCommit(d
		assertCommit(c
		assertNull(rw.next());
	}

	@Test
	public void testDepth3() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(b);
		final RevCommit d = commit(c);

		createShallowFile(b);

		rw.reset();
		markStart(d);
		assertCommit(d
		assertCommit(c
		assertCommit(b
		assertNull(rw.next());
	}

	@Test
	public void testMergeCommitOneParentShallow() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(b);
		final RevCommit d = commit(b);
		final RevCommit e = commit(d);
		final RevCommit merge = commit(c

		createShallowFile(e);

		rw.reset();
		markStart(merge);
		assertCommit(merge
		assertCommit(e
		assertCommit(c
		assertCommit(b
		assertCommit(a
		assertNull(rw.next());
	}

	@Test
	public void testMergeCommitEntirelyShallow() throws Exception {
		final RevCommit a = commit();
		final RevCommit b = commit(a);
		final RevCommit c = commit(b);
		final RevCommit d = commit(b);
		final RevCommit e = commit(d);
		final RevCommit merge = commit(c

		createShallowFile(c

		rw.reset();
		markStart(merge);
		assertCommit(merge
		assertCommit(e
		assertCommit(c
		assertNull(rw.next());
	}

	@Test
	public void testObjectDirectorySnapshot() throws Exception {
		RevCommit a = commit();
		RevCommit b = commit(a);
		RevCommit c = commit(b);
		RevCommit d = commit(c);

		createShallowFile(d);

		rw.reset();
		markStart(d);
		assertCommit(d
		assertNull(rw.next());

		rw = createRevWalk();
		a = rw.lookupCommit(a);
		b = rw.lookupCommit(b);
		c = rw.lookupCommit(c);
		d = rw.lookupCommit(d);

		rw.reset();
		markStart(d);
		assertCommit(d
		assertNull(rw.next());

		createShallowFile(c);

		rw = createRevWalk();
		a = rw.lookupCommit(a);
		b = rw.lookupCommit(b);
		c = rw.lookupCommit(c);
		d = rw.lookupCommit(d);

		rw.reset();
		markStart(d);
		assertCommit(d
		assertCommit(c
		assertNull(rw.next());
	}

	private void createShallowFile(ObjectId... shallowCommits)
			throws IOException {
		final StringBuilder builder = new StringBuilder();
		for (ObjectId commit : shallowCommits)
			builder.append(commit.getName() + "\n");
		JGitTestUtil.write(new File(rw.repository.getDirectory()
				builder.toString());
	}
}
