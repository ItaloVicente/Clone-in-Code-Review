
package org.eclipse.jgit.internal.storage.commitgraph;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.Set;

import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.util.NB;
import org.junit.Before;
import org.junit.Test;

public class CommitGraphWriterTest extends RepositoryTestCase {

	private TestRepository<FileRepository> tr;

	private ByteArrayOutputStream os;

	private CommitGraphWriter writer;

	private RevWalk walk;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		os = new ByteArrayOutputStream();
		tr = new TestRepository<>(db
		walk = new RevWalk(db);
	}

	@Test
	public void testConstructor() throws Exception {
		writer = new CommitGraphWriter(GraphCommits.fromWalk(
				NullProgressMonitor.INSTANCE
		assertEquals(0
	}

	@Test
	public void testWriteInEmptyRepo() throws Exception {
		NullProgressMonitor m = NullProgressMonitor.INSTANCE;
		writer = new CommitGraphWriter(
				GraphCommits.fromWalk(m
		writer.write(m
		assertEquals(0
	}

	@Test
	public void testWriterWithExtraEdgeList() throws Exception {
		RevCommit root = commit();
		RevCommit a = commit(root);
		RevCommit b = commit(root);
		RevCommit c = commit(root);
		RevCommit tip = commit(a

		Set<ObjectId> wants = Collections.singleton(tip);
		NullProgressMonitor m = NullProgressMonitor.INSTANCE;
		writer = new CommitGraphWriter(
				GraphCommits.fromWalk(m
		writer.write(m

		assertEquals(5
		byte[] data = os.toByteArray();
		assertTrue(data.length > 0);
		byte[] headers = new byte[8];
		System.arraycopy(data
		assertArrayEquals(new byte[] {'C'
		assertEquals(CommitGraphConstants.CHUNK_ID_OID_FANOUT
		assertEquals(CommitGraphConstants.CHUNK_ID_OID_LOOKUP
		assertEquals(CommitGraphConstants.CHUNK_ID_COMMIT_DATA
		assertEquals(CommitGraphConstants.CHUNK_ID_EXTRA_EDGE_LIST
	}

	@Test
	public void testWriterWithoutExtraEdgeList() throws Exception {
		RevCommit root = commit();
		RevCommit a = commit(root);
		RevCommit b = commit(root);
		RevCommit tip = commit(a

		Set<ObjectId> wants = Collections.singleton(tip);
		NullProgressMonitor m = NullProgressMonitor.INSTANCE;
		writer = new CommitGraphWriter(
				GraphCommits.fromWalk(m
		writer.write(m

		assertEquals(4
		byte[] data = os.toByteArray();
		assertTrue(data.length > 0);
		byte[] headers = new byte[8];
		System.arraycopy(data
		assertArrayEquals(new byte[] {'C'
		assertEquals(CommitGraphConstants.CHUNK_ID_OID_FANOUT
		assertEquals(CommitGraphConstants.CHUNK_ID_OID_LOOKUP
		assertEquals(CommitGraphConstants.CHUNK_ID_COMMIT_DATA
	}

	RevCommit commit(RevCommit... parents) throws Exception {
		return tr.commit(parents);
	}
}
