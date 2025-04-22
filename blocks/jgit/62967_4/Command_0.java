
package org.eclipse.jgit.internal.storage.reftree;

import static org.eclipse.jgit.lib.Constants.HEAD;
import static org.eclipse.jgit.lib.Constants.R_HEADS;
import static org.eclipse.jgit.lib.Constants.R_TAGS;
import static org.eclipse.jgit.lib.Ref.Storage.LOOSE;
import static org.eclipse.jgit.lib.Ref.Storage.NEW;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.LOCK_FAILURE;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.NOT_ATTEMPTED;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.REJECTED_OTHER_REASON;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.dfs.DfsRepositoryDescription;
import org.eclipse.jgit.internal.storage.dfs.InMemoryRepository;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.SymbolicRef;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.ReceiveCommand;
import org.junit.Before;
import org.junit.Test;

public class RefTreeTest {
	private static final String R_MASTER = R_HEADS + "master";
	private InMemoryRepository repo;
	private TestRepository<InMemoryRepository> git;

	@Before
	public void setUp() throws IOException {
		repo = new InMemoryRepository(new DfsRepositoryDescription("RefTree"));
		git = new TestRepository<>(repo);
	}

	@Test
	public void testEmptyTree() throws IOException {
		RefTree tree = RefTree.newEmptyTree();
		assertNull(HEAD
		assertNull("master"
	}

	@Test
	public void testApplyThenReadMaster() throws Exception {
		RefTree tree = RefTree.newEmptyTree();
		RevBlob id = git.blob("A");
		Command cmd = new Command(null
		assertTrue(tree.apply(Collections.singletonList(cmd)));
		assertSame(NOT_ATTEMPTED

		Ref m = tree.getRef(R_MASTER);
		assertNotNull(R_MASTER
		assertEquals(R_MASTER
		assertEquals(id
		assertTrue("peeled"
	}

	@Test
	public void testHeadSymref() throws Exception {
		RefTree tree = RefTree.newEmptyTree();
		RevBlob id = git.blob("A");
		Command cmd1 = new Command(null
		Command cmd2 = new Command(null
		assertTrue(tree.apply(Arrays.asList(new Command[] { cmd1
		assertSame(NOT_ATTEMPTED
		assertSame(NOT_ATTEMPTED

		Ref m = tree.getRef(HEAD);
		assertNotNull(HEAD
		assertEquals(HEAD
		assertTrue("symbolic"
		assertNotNull(m.getTarget());
		assertEquals(R_MASTER
		assertEquals(id

		tree = RefTree.readTree(repo.newObjectReader()
		m = tree.getRef(HEAD);
		assertEquals(R_MASTER
	}

	@Test
	public void testTagIsPeeled() throws Exception {
		String name = "v1.0";
		RefTree tree = RefTree.newEmptyTree();
		RevBlob id = git.blob("A");
		RevTag tag = git.tag(name

		String ref = R_TAGS + name;
		Command cmd = create(ref
		assertTrue(tree.apply(Collections.singletonList(cmd)));
		assertSame(NOT_ATTEMPTED

		Ref m = tree.getRef(ref);
		assertNotNull(ref
		assertEquals(ref
		assertEquals(tag
		assertTrue("peeled"
		assertEquals(id
	}

	@Test
	public void testApplyAlreadyExists() throws Exception {
		RefTree tree = RefTree.newEmptyTree();
		RevBlob a = git.blob("A");
		Command cmd = new Command(null
		assertTrue(tree.apply(Collections.singletonList(cmd)));
		ObjectId treeId = write(tree);

		RevBlob b = git.blob("B");
		Command cmd1 = create(R_MASTER
		Command cmd2 = create(R_MASTER
		assertFalse(tree.apply(Arrays.asList(new Command[] { cmd1
		assertSame(LOCK_FAILURE
		assertSame(REJECTED_OTHER_REASON
		assertEquals(JGitText.get().transactionAborted
		assertEquals(treeId
	}

	@Test
	public void testApplyWrongOldId() throws Exception {
		RefTree tree = RefTree.newEmptyTree();
		RevBlob a = git.blob("A");
		Command cmd = new Command(null
		assertTrue(tree.apply(Collections.singletonList(cmd)));
		ObjectId treeId = write(tree);

		RevBlob b = git.blob("B");
		RevBlob c = git.blob("C");
		Command cmd1 = update(R_MASTER
		Command cmd2 = create(R_MASTER
		assertFalse(tree.apply(Arrays.asList(new Command[] { cmd1
		assertSame(LOCK_FAILURE
		assertSame(REJECTED_OTHER_REASON
		assertEquals(JGitText.get().transactionAborted
		assertEquals(treeId
	}

	@Test
	public void testApplyCannotCreateSubdirectory() throws Exception {
		RefTree tree = RefTree.newEmptyTree();
		RevBlob a = git.blob("A");
		Command cmd = new Command(null
		assertTrue(tree.apply(Collections.singletonList(cmd)));
		ObjectId treeId = write(tree);

		RevBlob b = git.blob("B");
		Command cmd1 = create(R_MASTER + "/fail"
		assertFalse(tree.apply(Collections.singletonList(cmd1)));
		assertSame(LOCK_FAILURE
		assertEquals(treeId
	}

	@Test
	public void testApplyCannotCreateParentRef() throws Exception {
		RefTree tree = RefTree.newEmptyTree();
		RevBlob a = git.blob("A");
		Command cmd = new Command(null
		assertTrue(tree.apply(Collections.singletonList(cmd)));
		ObjectId treeId = write(tree);

		RevBlob b = git.blob("B");
		Command cmd1 = create("refs/heads"
		assertFalse(tree.apply(Collections.singletonList(cmd1)));
		assertSame(LOCK_FAILURE
		assertEquals(treeId
	}

	private static Ref ref(String name
		return new ObjectIdRef.PeeledNonTag(LOOSE
	}

	private static Ref symref(String name
		Ref d = new ObjectIdRef.PeeledNonTag(NEW
		return new SymbolicRef(name
	}

	private Command create(String name
			throws MissingObjectException
		return update(name
	}

	private Command update(String name
			throws MissingObjectException
		try (RevWalk rw = new RevWalk(repo)) {
			return new Command(rw
		}
	}

	private ObjectId write(RefTree tree) throws IOException {
		try (ObjectInserter ins = repo.newObjectInserter()) {
			ObjectId id = tree.writeTree(ins);
			ins.flush();
			return id;
		}
	}
}
