
package org.eclipse.jgit.internal.storage.dfs;

import static org.eclipse.jgit.junit.JGitTestUtil.concat;
import static org.eclipse.jgit.lib.Constants.OBJECT_ID_LENGTH;
import static org.eclipse.jgit.lib.Constants.encodeASCII;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.eclipse.jgit.internal.fsck.FsckError;
import org.eclipse.jgit.internal.fsck.FsckError.CorruptObject;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectChecker.ErrorType;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Before;
import org.junit.Test;

public class DfsFsckTest {
	private TestRepository<InMemoryRepository> git;

	private InMemoryRepository repo;

	private ObjectInserter ins;

	@Before
	public void setUp() throws IOException {
		DfsRepositoryDescription desc = new DfsRepositoryDescription("test");
		git = new TestRepository<>(new InMemoryRepository(desc));
		repo = git.getRepository();
		ins = repo.newObjectInserter();
	}

	@Test
	public void testHealthyRepo() throws Exception {
		RevCommit commit0 = git.commit().message("0").create();
		RevCommit commit1 = git.commit().message("1").parent(commit0).create();
		git.update("master"

		DfsFsck fsck = new DfsFsck(repo);
		FsckError errors = fsck.check(null);

		assertEquals(errors.getCorruptObjects().size()
		assertEquals(errors.getMissingObjects().size()
		assertEquals(errors.getCorruptIndices().size()
	}

	@Test
	public void testCommitWithCorruptAuthor() throws Exception {
		StringBuilder b = new StringBuilder();
		b.append("tree be9bfa841874ccc9f2ef7c48d0c76226f89b7189\n");
		b.append("author b <b@c> <b@c> 0 +0000\n");
		b.append("committer <> 0 +0000\n");
		byte[] data = encodeASCII(b.toString());
		ObjectId id = ins.insert(Constants.OBJ_COMMIT
		ins.flush();

		DfsFsck fsck = new DfsFsck(repo);
		FsckError errors = fsck.check(null);

		assertEquals(errors.getCorruptObjects().size()
		CorruptObject o = errors.getCorruptObjects().iterator().next();
		assertTrue(o.getId().equals(id));
		assertEquals(o.getErrorType()
	}

	@Test
	public void testCommitWithoutTree() throws Exception {
		StringBuilder b = new StringBuilder();
		b.append("parent ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');
		byte[] data = encodeASCII(b.toString());
		ObjectId id = ins.insert(Constants.OBJ_COMMIT
		ins.flush();

		DfsFsck fsck = new DfsFsck(repo);
		FsckError errors = fsck.check(null);

		assertEquals(errors.getCorruptObjects().size()
		CorruptObject o = errors.getCorruptObjects().iterator().next();
		assertTrue(o.getId().equals(id));
		assertEquals(o.getErrorType()
	}

	@Test
	public void testTagWithoutObject() throws Exception {
		StringBuilder b = new StringBuilder();
		b.append("type commit\n");
		b.append("tag test-tag\n");
		b.append("tagger A. U. Thor <author@localhost> 1 +0000\n");
		byte[] data = encodeASCII(b.toString());
		ObjectId id = ins.insert(Constants.OBJ_TAG
		ins.flush();

		DfsFsck fsck = new DfsFsck(repo);
		FsckError errors = fsck.check(null);

		assertEquals(errors.getCorruptObjects().size()
		CorruptObject o = errors.getCorruptObjects().iterator().next();
		assertTrue(o.getId().equals(id));
		assertEquals(o.getErrorType()
	}

	@Test
	public void testTreeWithNullSha() throws Exception {
		byte[] data = concat(encodeASCII("100644 A")
				new byte[OBJECT_ID_LENGTH]);
		ObjectId id = ins.insert(Constants.OBJ_TREE
		ins.flush();

		DfsFsck fsck = new DfsFsck(repo);
		FsckError errors = fsck.check(null);

		assertEquals(errors.getCorruptObjects().size()
		CorruptObject o = errors.getCorruptObjects().iterator().next();
		assertTrue(o.getId().equals(id));
		assertEquals(o.getErrorType()
	}

	@Test
	public void testMultipleInvalidObjects() throws Exception {
		StringBuilder b = new StringBuilder();
		b.append("tree ");
		b.append("be9bfa841874ccc9f2ef7c48d0c76226f89b7189");
		b.append('\n');
		b.append("parent ");
		b.append("\n");
		byte[] data = encodeASCII(b.toString());
		ObjectId id1 = ins.insert(Constants.OBJ_COMMIT

		b = new StringBuilder();
		b.append("100644");
		data = encodeASCII(b.toString());
		ObjectId id2 = ins.insert(Constants.OBJ_TREE

		ins.flush();

		DfsFsck fsck = new DfsFsck(repo);
		FsckError errors = fsck.check(null);

		assertEquals(errors.getCorruptObjects().size()
		for (CorruptObject o : errors.getCorruptObjects()) {
			if (o.getId().equals(id1)) {
				assertEquals(o.getErrorType()
			} else if (o.getId().equals(id2)) {
				assertNull(o.getErrorType());
			} else {
				fail();
			}
		}
	}
}
