
package org.eclipse.jgit.internal.storage.reftree;

import static org.eclipse.jgit.internal.storage.reftree.RefTreeDb.R_TXN_COMMITTED;
import static org.eclipse.jgit.lib.Constants.HEAD;
import static org.eclipse.jgit.lib.Constants.R_HEADS;
import static org.eclipse.jgit.lib.Constants.R_TAGS;
import static org.eclipse.jgit.lib.Ref.Storage.LOOSE;
import static org.eclipse.jgit.lib.Ref.Storage.PACKED;
import static org.eclipse.jgit.lib.RefDatabase.ALL;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.REJECTED_OTHER_REASON;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.dfs.DfsRepositoryDescription;
import org.eclipse.jgit.internal.storage.dfs.InMemoryRepository;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.BatchRefUpdate;
import org.eclipse.jgit.lib.CommitBuilder;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.SymbolicRef;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.ReceiveCommand;
import org.junit.Before;
import org.junit.Test;

public class RefTreeDbTest {
	private InMemoryRepository repo;
	private RefTreeDb refdb;
	private RefDatabase bootstrap;

	private TestRepository<InMemoryRepository> testRepo;
	private RevCommit A;
	private RevCommit B;
	private RevTag v1_0;

	@Before
	public void setUp() throws Exception {
		repo = new InMemoryRepository(new DfsRepositoryDescription("test")) {
			private final RefTreeDb rdb;

			{
				rdb = new RefTreeDb(this
						RefTreeDb.BootstrapBehavior.HIDDEN_REJECT);
			}

			public RefDatabase getRefDatabase() {
				return rdb;
			}
		};
		refdb = (RefTreeDb) repo.getRefDatabase();
		bootstrap = refdb.getBootstrap();

		testRepo = new TestRepository<>(repo);
		A = testRepo.commit().create();
		B = testRepo.commit(testRepo.getRevWalk().parseCommit(A));
		v1_0 = testRepo.tag("v1_0"
		testRepo.getRevWalk().parseBody(v1_0);
	}

	@Test
	public void testSupportsAtomic() {
		assertTrue(refdb.performsAtomicTransactions());
	}

	@Test
	public void testGetRefs_EmptyDatabase() throws IOException {
		assertTrue("no references"
		assertTrue("no references"
		assertTrue("no references"
	}

	@Test
	public void testGetRefs_HeadOnOneBranch() throws IOException {
		symref(HEAD
		update("refs/heads/master"

		Map<String
		assertEquals(2
		assertTrue("has HEAD"
		assertTrue("has master"

		Ref head = all.get(HEAD);
		Ref master = all.get("refs/heads/master");

		assertEquals(HEAD
		assertTrue(head.isSymbolic());
		assertSame(LOOSE
		assertSame("uses same ref as target"

		assertEquals("refs/heads/master"
		assertFalse(master.isSymbolic());
		assertSame(PACKED
		assertEquals(A
	}

	@Test
	public void testGetRefs_DetachedHead() throws IOException {
		update(HEAD

		Map<String
		assertEquals(1
		assertTrue("has HEAD"

		Ref head = all.get(HEAD);
		assertEquals(HEAD
		assertFalse(head.isSymbolic());
		assertSame(PACKED
		assertEquals(A
	}

	@Test
	public void testGetRefs_DeeplyNestedBranch() throws IOException {
		String name = "refs/heads/a/b/c/d/e/f/g/h/i/j/k";
		update(name

		Map<String
		assertEquals(1

		Ref r = all.get(name);
		assertEquals(name
		assertFalse(r.isSymbolic());
		assertSame(PACKED
		assertEquals(A
	}

	@Test
	public void testGetRefs_HeadBranchNotBorn() throws IOException {
		update("refs/heads/A"
		update("refs/heads/B"

		Map<String
		assertEquals(2
		assertFalse("no HEAD"

		Ref a = all.get("refs/heads/A");
		Ref b = all.get("refs/heads/B");

		assertEquals(A
		assertEquals(B

		assertEquals("refs/heads/A"
		assertEquals("refs/heads/B"
	}

	@Test
	public void testGetRefs_HeadsOnly() throws IOException {
		update("refs/heads/A"
		update("refs/heads/B"
		update("refs/tags/v1.0"

		Map<String
		assertEquals(2

		Ref a = heads.get("A");
		Ref b = heads.get("B");

		assertEquals("refs/heads/A"
		assertEquals("refs/heads/B"

		assertEquals(A
		assertEquals(B
	}

	@Test
	public void testGetRefs_TagsOnly() throws IOException {
		update("refs/heads/A"
		update("refs/heads/B"
		update("refs/tags/v1.0"

		Map<String
		assertEquals(1

		Ref a = tags.get("v1.0");
		assertEquals("refs/tags/v1.0"
		assertEquals(v1_0
		assertTrue(a.isPeeled());
		assertEquals(v1_0.getObject()
	}

	@Test
	public void testGetRefs_HeadsSymref() throws IOException {
		symref("refs/heads/other"
		update("refs/heads/master"

		Map<String
		assertEquals(2

		Ref master = heads.get("master");
		Ref other = heads.get("other");

		assertEquals("refs/heads/master"
		assertEquals(A

		assertEquals("refs/heads/other"
		assertEquals(A
		assertSame(master
	}

	@Test
	public void testGetRefs_InvalidPrefixes() throws IOException {
		update("refs/heads/A"

		assertTrue("empty refs/heads"
		assertTrue("empty objects"
		assertTrue("empty objects/"
	}

	@Test
	public void testGetRefs_DiscoversNew() throws IOException {
		update("refs/heads/master"
		Map<String

		update("refs/heads/next"
		Map<String

		assertEquals(1
		assertEquals(2

		assertFalse(orig.containsKey("refs/heads/next"));
		assertTrue(next.containsKey("refs/heads/next"));

		assertEquals(A
		assertEquals(B
	}

	@Test
	public void testGetRefs_DiscoversModified() throws IOException {
		symref(HEAD
		update("refs/heads/master"

		Map<String
		assertEquals(A

		update("refs/heads/master"
		all = refdb.getRefs(ALL);
		assertEquals(B
		assertEquals(B
	}

	@Test
	public void testGetRefs_CycleInSymbolicRef() throws IOException {
		symref("refs/1"
		symref("refs/2"
		symref("refs/3"
		symref("refs/4"
		symref("refs/5"
		update("refs/end"

		Map<String
		Ref r = all.get("refs/1");
		assertNotNull("has 1"

		assertEquals("refs/1"
		assertEquals(A
		assertTrue(r.isSymbolic());

		r = r.getTarget();
		assertEquals("refs/2"
		assertEquals(A
		assertTrue(r.isSymbolic());

		r = r.getTarget();
		assertEquals("refs/3"
		assertEquals(A
		assertTrue(r.isSymbolic());

		r = r.getTarget();
		assertEquals("refs/4"
		assertEquals(A
		assertTrue(r.isSymbolic());

		r = r.getTarget();
		assertEquals("refs/5"
		assertEquals(A
		assertTrue(r.isSymbolic());

		r = r.getTarget();
		assertEquals("refs/end"
		assertEquals(A
		assertFalse(r.isSymbolic());

		symref("refs/5"
		symref("refs/6"
		all = refdb.getRefs(ALL);
		assertNull("mising 1 due to cycle"
		assertEquals(A
		assertEquals(A
		assertEquals(A
		assertEquals(A
		assertEquals(A
		assertEquals(A
	}

	@Test
	public void testGetRef_NonExistingBranchConfig() throws IOException {
		assertNull("find branch config"
		assertNull("find branch config"
	}

	@Test
	public void testGetRef_FindBranchConfig() throws IOException {
		update("refs/heads/config"

		for (String t : new String[] { "config"
			Ref r = refdb.getRef(t);
			assertNotNull("find branch config (" + t + ")"
			assertEquals("for " + t
			assertEquals("for " + t
		}
	}

	@Test
	public void testFirstExactRef() throws IOException {
		update("refs/heads/A"
		update("refs/tags/v1.0"

		Ref a = refdb.firstExactRef("refs/heads/A"
		Ref one = refdb.firstExactRef("refs/tags/v1.0"

		assertEquals("refs/heads/A"
		assertEquals("refs/tags/v1.0"

		assertEquals(A
		assertEquals(v1_0
	}

	@Test
	public void testExactRef_DiscoversModified() throws IOException {
		symref(HEAD
		update("refs/heads/master"
		assertEquals(A

		update("refs/heads/master"
		assertEquals(B
	}

	@Test
	public void testIsNameConflicting() throws IOException {
		update("refs/heads/a/b"
		update("refs/heads/q"

		assertTrue(refdb.isNameConflicting("refs"));
		assertTrue(refdb.isNameConflicting("refs/heads"));
		assertTrue(refdb.isNameConflicting("refs/heads/a"));

		assertFalse(refdb.isNameConflicting("refs/heads/a/b"));

		assertFalse(refdb.isNameConflicting("refs/heads/a/d"));
		assertFalse(refdb.isNameConflicting("refs/heads/master"));

		assertTrue(refdb.isNameConflicting("refs/heads/a/b/c"));
		assertTrue(refdb.isNameConflicting("refs/heads/q/master"));
	}

	@Test
	public void testUpdate_RefusesRefsTxnNamespace() throws IOException {
		ObjectId txnId = txnCommitted();

		RefUpdate u = refdb.newUpdate("refs/txn/tmp"
		u.setNewObjectId(B);
		assertEquals(RefUpdate.Result.LOCK_FAILURE
		assertEquals(txnId

		ReceiveCommand cmd = command(null
		BatchRefUpdate batch = refdb.newBatchUpdate();
		batch.addCommand(cmd);
		batch.execute(new RevWalk(repo)

		assertEquals(REJECTED_OTHER_REASON
		assertEquals(MessageFormat.format(JGitText.get().invalidRefName
				"refs/txn/tmp")
		assertEquals(txnId
	}

	@Test
	public void testUpdate_RefusesDotLockInRefName() throws IOException {
		ObjectId txnId = txnCommitted();

		RefUpdate u = refdb.newUpdate("refs/heads/pu.lock"
		u.setNewObjectId(B);
		assertEquals(RefUpdate.Result.REJECTED
		assertEquals(txnId

		ReceiveCommand cmd = command(null
		BatchRefUpdate batch = refdb.newBatchUpdate();
		batch.addCommand(cmd);
		batch.execute(new RevWalk(repo)

		assertEquals(REJECTED_OTHER_REASON
		assertEquals(JGitText.get().funnyRefname
		assertEquals(txnId
	}

	@Test
	public void testBatchRefUpdate_NonFastForwardAborts() throws IOException {
		update("refs/heads/master"
		update("refs/heads/masters"
		ObjectId txnId = txnCommitted();

		List<ReceiveCommand> commands = Arrays.asList(
				command(A
				command(B
		BatchRefUpdate batchUpdate = refdb.newBatchUpdate();
		batchUpdate.addCommand(commands);
		batchUpdate.execute(new RevWalk(repo)
		assertEquals(txnId

		assertEquals(ReceiveCommand.Result.REJECTED_NONFASTFORWARD
				commands.get(1).getResult());
		assertEquals(ReceiveCommand.Result.REJECTED_OTHER_REASON
				commands.get(0).getResult());
		assertEquals(JGitText.get().transactionAborted
				commands.get(0).getMessage());
	}

	@Test
	public void testBatchRefUpdate_ForceUpdate() throws IOException {
		update("refs/heads/master"
		update("refs/heads/masters"
		ObjectId txnId = txnCommitted();

		List<ReceiveCommand> commands = Arrays.asList(
				command(A
				command(B
		BatchRefUpdate batchUpdate = refdb.newBatchUpdate();
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate.execute(new RevWalk(repo)
		assertNotEquals(txnId

		Map<String
		assertEquals(ReceiveCommand.Result.OK
		assertEquals(ReceiveCommand.Result.OK
		assertEquals(
				"[refs/heads/master
				refs.keySet().toString());
		assertEquals(B.getId()
		assertEquals(A.getId()
	}

	@Test
	public void testBatchRefUpdate_NonFastForwardDoesNotDoExpensiveMergeCheck()
			throws IOException {
		update("refs/heads/master"
		ObjectId txnId = txnCommitted();

		List<ReceiveCommand> commands = Arrays.asList(
				command(B
		BatchRefUpdate batchUpdate = refdb.newBatchUpdate();
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate.execute(new RevWalk(repo) {
			@Override
			public boolean isMergedInto(RevCommit base
				fail("isMergedInto() should not be called");
				return false;
			}
		}
		assertNotEquals(txnId

		Map<String
		assertEquals(ReceiveCommand.Result.OK
		assertEquals(A.getId()
	}

	@Test
	public void testBatchRefUpdate_ConflictCausesAbort() throws IOException {
		update("refs/heads/master"
		update("refs/heads/masters"
		ObjectId txnId = txnCommitted();

		List<ReceiveCommand> commands = Arrays.asList(
				command(A
				command(null
				command(null
		BatchRefUpdate batchUpdate = refdb.newBatchUpdate();
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate.execute(new RevWalk(repo)
		assertEquals(txnId

		assertEquals(ReceiveCommand.Result.LOCK_FAILURE
				commands.get(0).getResult());

		assertEquals(ReceiveCommand.Result.REJECTED_OTHER_REASON
				commands.get(1).getResult());
		assertEquals(JGitText.get().transactionAborted
				commands.get(1).getMessage());

		assertEquals(ReceiveCommand.Result.REJECTED_OTHER_REASON
				commands.get(2).getResult());
		assertEquals(JGitText.get().transactionAborted
				commands.get(2).getMessage());
	}

	@Test
	public void testBatchRefUpdate_NoConflictIfDeleted() throws IOException {
		update("refs/heads/master"
		update("refs/heads/masters"
		ObjectId txnId = txnCommitted();

		List<ReceiveCommand> commands = Arrays.asList(
				command(A
				command(null
				command(B
		BatchRefUpdate batchUpdate = refdb.newBatchUpdate();
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate.execute(new RevWalk(repo)
		assertNotEquals(txnId

		assertEquals(ReceiveCommand.Result.OK
		assertEquals(ReceiveCommand.Result.OK
		assertEquals(ReceiveCommand.Result.OK

		Map<String
		assertEquals(
				"[refs/heads/master
				refs.keySet().toString());
		assertEquals(A.getId()
	}

	private ObjectId txnCommitted() throws IOException {
		Ref r = bootstrap.exactRef(R_TXN_COMMITTED);
		if (r != null && r.getObjectId() != null) {
			return r.getObjectId();
		}
		return ObjectId.zeroId();
	}

	private static ReceiveCommand command(RevCommit a
			String name) {
		return new ReceiveCommand(
				a != null ? a.getId() : ObjectId.zeroId()
				b != null ? b.getId() : ObjectId.zeroId()
				name);
	}

	private void symref(final String name
			throws IOException {
		commit(new Function() {
			@Override
			public boolean apply(ObjectReader reader
					throws IOException {
				Ref old = tree.exactRef(reader
				Command n = new Command(
					old
					new SymbolicRef(
						name
						new ObjectIdRef.Unpeeled(Ref.Storage.NEW
				return tree.apply(Collections.singleton(n));
			}
		});
	}

	private void update(final String name
			throws IOException {
		commit(new Function() {
			@Override
			public boolean apply(ObjectReader reader
					throws IOException {
				Ref old = tree.exactRef(reader
				Command n;
				try (RevWalk rw = new RevWalk(repo)) {
					n = new Command(old
				}
				return tree.apply(Collections.singleton(n));
			}
		});
	}

	interface Function {
		boolean apply(ObjectReader reader
	}

	private void commit(Function fun) throws IOException {
		try (ObjectReader reader = repo.newObjectReader();
				ObjectInserter inserter = repo.newObjectInserter();
				RevWalk rw = new RevWalk(reader)) {
			RefUpdate u = bootstrap.newUpdate(R_TXN_COMMITTED
			CommitBuilder cb = new CommitBuilder();
			testRepo.setAuthorAndCommitter(cb);

			Ref ref = bootstrap.exactRef(R_TXN_COMMITTED);
			RefTree tree;
			if (ref != null && ref.getObjectId() != null) {
				tree = RefTree.read(reader
				cb.setParentId(ref.getObjectId());
				u.setExpectedOldObjectId(ref.getObjectId());
			} else {
				tree = RefTree.newEmptyTree();
				u.setExpectedOldObjectId(ObjectId.zeroId());
			}

			assertTrue(fun.apply(reader
			cb.setTreeId(tree.writeTree(inserter));
			u.setNewObjectId(inserter.insert(cb));
			inserter.flush();
			switch (u.update(rw)) {
			case NEW:
			case FAST_FORWARD:
				break;
			default:
				fail("Expected " + u.getName() + " to update");
			}
		}
	}
}
