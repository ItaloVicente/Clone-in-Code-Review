
package org.eclipse.jgit.revwalk;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

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


	private void createShallowFile(ObjectId... shallowCommits)
			throws IOException {
		final File shallow = new File(rw.repository.getDirectory()
		final PrintWriter writer = new PrintWriter(shallow);
		try {
			for (ObjectId commit : shallowCommits)
				writer.println(commit.getName());
		} finally {
			writer.close();
		}
	}
}
