
package org.eclipse.jgit.internal.storage.file;

import static org.eclipse.jgit.lib.ObjectId.zeroId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;
import org.eclipse.jgit.junit.StrictWorkMonitor;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.BatchRefUpdate;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.ReceiveCommand;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class BatchRefUpdateTest extends LocalDiskRepositoryTestCase {
	@Parameter
	public boolean atomic;

	@Parameters(name = "atomic={0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][]{ {Boolean.FALSE}
	}

	private Repository diskRepo;
	private TestRepository<Repository> repo;
	private RefDirectory refdir;
	private RevCommit A;
	private RevCommit B;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();

		diskRepo = createBareRepository();
		refdir = (RefDirectory) diskRepo.getRefDatabase();

		repo = new TestRepository<>(diskRepo);
		A = repo.commit().create();
		B = repo.commit(repo.getRevWalk().parseCommit(A));
	}

	private BatchRefUpdate newBatchUpdate() {
		BatchRefUpdate u = refdir.newBatchUpdate();
		if (atomic) {
			assertTrue(u.isAtomic());
		} else {
			u.setAtomic(false);
		}
		return u;
	}

	@Test
	public void simpleNoForce() throws IOException {
		writeLooseRef("refs/heads/master"
		writeLooseRef("refs/heads/masters"
		List<ReceiveCommand> commands = Arrays.asList(
				new ReceiveCommand(A
						ReceiveCommand.Type.UPDATE)
				new ReceiveCommand(B
						ReceiveCommand.Type.UPDATE_NONFASTFORWARD));
		BatchRefUpdate batchUpdate = newBatchUpdate();
		batchUpdate.addCommand(commands);
		batchUpdate.execute(new RevWalk(diskRepo)
		Map<String
		assertEquals(ReceiveCommand.Result.REJECTED_NONFASTFORWARD
				.get(1).getResult());
		if (atomic) {
			assertTrue(ReceiveCommand.isTransactionAborted(commands.get(0)));
			assertEquals("[HEAD
					.keySet().toString());
			assertEquals(A.getId()
			assertEquals(B.getId()
		} else {
			assertEquals(ReceiveCommand.Result.OK
			assertEquals("[HEAD
					.keySet().toString());
			assertEquals(B.getId()
			assertEquals(B.getId()
		}
	}

	@Test
	public void simpleForce() throws IOException {
		writeLooseRef("refs/heads/master"
		writeLooseRef("refs/heads/masters"
		List<ReceiveCommand> commands = Arrays.asList(
				new ReceiveCommand(A
						ReceiveCommand.Type.UPDATE)
				new ReceiveCommand(B
						ReceiveCommand.Type.UPDATE_NONFASTFORWARD));
		BatchRefUpdate batchUpdate = newBatchUpdate();
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate.execute(new RevWalk(diskRepo)
		Map<String
		assertEquals(ReceiveCommand.Result.OK
		assertEquals(ReceiveCommand.Result.OK
		assertEquals("[HEAD
				.keySet().toString());
		assertEquals(B.getId()
		assertEquals(A.getId()
	}

	@Test
	public void nonFastForwardDoesNotDoExpensiveMergeCheck() throws IOException {
		writeLooseRef("refs/heads/master"
		List<ReceiveCommand> commands = Arrays.asList(
				new ReceiveCommand(B
						ReceiveCommand.Type.UPDATE_NONFASTFORWARD));
		BatchRefUpdate batchUpdate = newBatchUpdate();
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate.execute(new RevWalk(diskRepo) {
			@Override
			public boolean isMergedInto(RevCommit base
				throw new AssertionError("isMergedInto() should not be called");
			}
		}
		Map<String
		assertEquals(ReceiveCommand.Result.OK
		assertEquals(A.getId()
	}

	@Test
	public void fileDirectoryConflict() throws IOException {
		writeLooseRef("refs/heads/master"
		writeLooseRef("refs/heads/masters"
		List<ReceiveCommand> commands = Arrays.asList(
				new ReceiveCommand(A
						ReceiveCommand.Type.UPDATE)
				new ReceiveCommand(zeroId()
						ReceiveCommand.Type.CREATE)
				new ReceiveCommand(zeroId()
						ReceiveCommand.Type.CREATE));
		BatchRefUpdate batchUpdate = newBatchUpdate();
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate
				.execute(new RevWalk(diskRepo)
		Map<String

		if (atomic) {
			assertEquals(ReceiveCommand.Result.LOCK_FAILURE
					commands.get(0).getResult());
			assertTrue(ReceiveCommand.isTransactionAborted(commands.get(1)));
			assertTrue(ReceiveCommand.isTransactionAborted(commands.get(2)));
			assertEquals("[HEAD
					.keySet().toString());
			assertEquals(A.getId()
			assertEquals(B.getId()
		} else {
			assertEquals(ReceiveCommand.Result.OK
			assertEquals(ReceiveCommand.Result.LOCK_FAILURE
					.getResult());
			assertEquals(ReceiveCommand.Result.LOCK_FAILURE
					.getResult());
			assertEquals("[HEAD
					.keySet().toString());
			assertEquals(B.getId()
			assertEquals(B.getId()
		}
	}

	@Test
	public void conflictThanksToDelete() throws IOException {
		writeLooseRef("refs/heads/master"
		writeLooseRef("refs/heads/masters"
		List<ReceiveCommand> commands = Arrays.asList(
				new ReceiveCommand(A
						ReceiveCommand.Type.UPDATE)
				new ReceiveCommand(zeroId()
						ReceiveCommand.Type.CREATE)
				new ReceiveCommand(B
						ReceiveCommand.Type.DELETE));
		BatchRefUpdate batchUpdate = newBatchUpdate();
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate.execute(new RevWalk(diskRepo)
		Map<String
		assertEquals(ReceiveCommand.Result.OK
		assertEquals(ReceiveCommand.Result.OK
		assertEquals(ReceiveCommand.Result.OK
		assertEquals("[HEAD
				.keySet().toString());
		assertEquals(A.getId()
	}

	@Test
	public void updateToMissingObject() throws IOException {
		writeLooseRef("refs/heads/master"
		ObjectId bad =
				ObjectId.fromString("deadbeefdeadbeefdeadbeefdeadbeefdeadbeef");
		List<ReceiveCommand> commands = Arrays.asList(
				new ReceiveCommand(A
						ReceiveCommand.Type.UPDATE)
				new ReceiveCommand(zeroId()
						ReceiveCommand.Type.CREATE));
		BatchRefUpdate batchUpdate = newBatchUpdate();
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate.execute(new RevWalk(diskRepo)
		Map<String
		assertEquals(ReceiveCommand.Result.REJECTED_MISSING_OBJECT
				commands.get(0).getResult());

		if (atomic) {
			assertTrue(ReceiveCommand.isTransactionAborted(commands.get(1)));
			assertEquals("[HEAD
					.toString());
			assertEquals(A.getId()
		} else {
			assertEquals(ReceiveCommand.Result.OK
			assertEquals("[HEAD
					.toString());
			assertEquals(A.getId()
			assertEquals(B.getId()
		}
	}

	@Test
	public void addMissingObject() throws IOException {
		writeLooseRef("refs/heads/master"
		ObjectId bad =
				ObjectId.fromString("deadbeefdeadbeefdeadbeefdeadbeefdeadbeef");
		List<ReceiveCommand> commands = Arrays.asList(
				new ReceiveCommand(A
						ReceiveCommand.Type.UPDATE)
				new ReceiveCommand(zeroId()
						ReceiveCommand.Type.CREATE));
		BatchRefUpdate batchUpdate = newBatchUpdate();
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate.execute(new RevWalk(diskRepo)
		Map<String
		assertEquals(ReceiveCommand.Result.REJECTED_MISSING_OBJECT
				commands.get(1).getResult());

		if (atomic) {
			assertTrue(ReceiveCommand.isTransactionAborted(commands.get(0)));
			assertEquals("[HEAD
			assertEquals(A.getId()
		} else {
			assertEquals(ReceiveCommand.Result.OK
			assertEquals("[HEAD
					.toString());
			assertEquals(B.getId()
		}
	}

	@Test
	public void oneNonExistentRef() throws IOException {
		List<ReceiveCommand> commands = Arrays.asList(
				new ReceiveCommand(A
						ReceiveCommand.Type.UPDATE)
				new ReceiveCommand(zeroId()
						ReceiveCommand.Type.CREATE));
		BatchRefUpdate batchUpdate = newBatchUpdate();
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate.execute(new RevWalk(diskRepo)
		Map<String
		assertEquals(ReceiveCommand.Result.LOCK_FAILURE
				commands.get(0).getResult());

		if (atomic) {
			assertTrue(ReceiveCommand.isTransactionAborted(commands.get(1)));
			assertEquals("[]"
		} else {
			assertEquals(ReceiveCommand.Result.OK
			assertEquals("[refs/heads/foo2]"
			assertEquals(B.getId()
		}
	}

	@Test
	public void oneRefWrongOldValue() throws IOException {
		writeLooseRef("refs/heads/master"
		List<ReceiveCommand> commands = Arrays.asList(
				new ReceiveCommand(B
						ReceiveCommand.Type.UPDATE)
				new ReceiveCommand(zeroId()
						ReceiveCommand.Type.CREATE));
		BatchRefUpdate batchUpdate = newBatchUpdate();
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate.execute(new RevWalk(diskRepo)
		Map<String
		assertEquals(ReceiveCommand.Result.LOCK_FAILURE
				commands.get(0).getResult());

		if (atomic) {
			assertTrue(ReceiveCommand.isTransactionAborted(commands.get(1)));
			assertEquals("[HEAD
			assertEquals(A.getId()
		} else {
			assertEquals(ReceiveCommand.Result.OK
			assertEquals("[HEAD
					.keySet().toString());
			assertEquals(A.getId()
			assertEquals(B.getId()
		}
	}

	@Test
	public void nonExistentRef() throws IOException {
		writeLooseRef("refs/heads/master"
		List<ReceiveCommand> commands = Arrays.asList(
				new ReceiveCommand(A
						ReceiveCommand.Type.UPDATE)
				new ReceiveCommand(A
						ReceiveCommand.Type.DELETE));
		BatchRefUpdate batchUpdate = newBatchUpdate();
		batchUpdate.setAllowNonFastForwards(true);
		batchUpdate.addCommand(commands);
		batchUpdate.execute(new RevWalk(diskRepo)
		Map<String
		assertEquals(ReceiveCommand.Result.LOCK_FAILURE
				commands.get(1).getResult());

		if (atomic) {
			assertTrue(ReceiveCommand.isTransactionAborted(commands.get(0)));
			assertEquals("[HEAD
			assertEquals(A.getId()
		} else {
			assertEquals(ReceiveCommand.Result.OK
			assertEquals("[HEAD
			assertEquals(B.getId()
		}
	}

	private void writeLooseRef(String name
		write(new File(diskRepo.getDirectory()
	}
}
