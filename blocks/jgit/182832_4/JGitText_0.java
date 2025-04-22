
package org.eclipse.jgit.internal.storage.commitgraph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
import org.junit.Before;
import org.junit.Test;

public class CommitGraphWriterTest extends RepositoryTestCase {

	private TestRepository<FileRepository> tr;

	private CommitGraphConfig config;

	private ByteArrayOutputStream os;

	private CommitGraphWriter writer;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		os = new ByteArrayOutputStream();
		config = new CommitGraphConfig(db);
		tr = new TestRepository<>(db
	}

	@Test
	public void testConstructor() {
		writer = new CommitGraphWriter(config
		assertTrue(config.isComputeGeneration());
		assertTrue(writer.isComputeGeneration());
		assertEquals(0
	}

	@Test
	public void testModifySettings() {
		config.setComputeGeneration(false);
		assertFalse(config.isComputeGeneration());

		writer = new CommitGraphWriter(config
		assertFalse(writer.isComputeGeneration());
		writer.setComputeGeneration(true);
		assertTrue(writer.isComputeGeneration());
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
		writer = new CommitGraphWriter(config
		writer.prepareCommitGraph(m

		assertTrue(writer.willWriteExtraEdgeList());
		assertEquals(5

		writer.writeCommitGraph(m
		byte[] data = os.toByteArray();
		assertTrue(data.length > 0);
	}

	@Test
	public void testWriterWithoutExtraEdgeList() throws Exception {
		RevCommit root = commit();
		RevCommit a = commit(root);
		RevCommit b = commit(root);
		RevCommit tip = commit(a

		Set<ObjectId> wants = Collections.singleton(tip);
		NullProgressMonitor m = NullProgressMonitor.INSTANCE;
		writer = new CommitGraphWriter(config
		writer.prepareCommitGraph(m

		assertFalse(writer.willWriteExtraEdgeList());
		assertEquals(4

		writer.writeCommitGraph(m
		byte[] data = os.toByteArray();
		assertTrue(data.length > 0);
	}

	RevCommit commit(RevCommit... parents) throws Exception {
		return tr.commit(parents);
	}
}
