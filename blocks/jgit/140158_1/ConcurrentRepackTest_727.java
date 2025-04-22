
package org.eclipse.jgit.internal.storage.file;

import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.eclipse.jgit.internal.storage.file.BatchRefUpdateTest.Result.LOCK_FAILURE;
import static org.eclipse.jgit.internal.storage.file.BatchRefUpdateTest.Result.OK;
import static org.eclipse.jgit.internal.storage.file.BatchRefUpdateTest.Result.REJECTED_MISSING_OBJECT;
import static org.eclipse.jgit.internal.storage.file.BatchRefUpdateTest.Result.REJECTED_NONFASTFORWARD;
import static org.eclipse.jgit.internal.storage.file.BatchRefUpdateTest.Result.TRANSACTION_ABORTED;
import static org.eclipse.jgit.lib.ObjectId.zeroId;
import static org.eclipse.jgit.transport.ReceiveCommand.Type.CREATE;
import static org.eclipse.jgit.transport.ReceiveCommand.Type.DELETE;
import static org.eclipse.jgit.transport.ReceiveCommand.Type.UPDATE;
import static org.eclipse.jgit.transport.ReceiveCommand.Type.UPDATE_NONFASTFORWARD;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

import org.eclipse.jgit.events.ListenerHandle;
import org.eclipse.jgit.events.RefsChangedListener;
import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;
import org.eclipse.jgit.junit.StrictWorkMonitor;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.BatchRefUpdate;
import org.eclipse.jgit.lib.CheckoutEntry;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.ReflogEntry;
import org.eclipse.jgit.lib.ReflogReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.ReceiveCommand;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@SuppressWarnings("boxing")
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

	private int refsChangedEvents;

	private ListenerHandle handle;

	private RefsChangedListener refsChangedListener = event -> {
		refsChangedEvents++;
	};

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();

		diskRepo = createBareRepository();
		setLogAllRefUpdates(true);

		refdir = (RefDirectory) diskRepo.getRefDatabase();
		refdir.setRetrySleepMs(Arrays.asList(0

		repo = new TestRepository<>(diskRepo);
		A = repo.commit().create();
		B = repo.commit(repo.getRevWalk().parseCommit(A));
		refsChangedEvents = 0;
		handle = diskRepo.getListenerList()
				.addRefsChangedListener(refsChangedListener);
	}

	@After
	public void removeListener() {
		handle.remove();
		refsChangedEvents = 0;
	}

	@Test
	public void simpleNoForce() throws IOException {
		writeLooseRef("refs/heads/master"
		writeLooseRef("refs/heads/masters"

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(B
		execute(newBatchUpdate(cmds));

		if (atomic) {
			assertResults(cmds
			assertRefs(
					"refs/heads/master"
					"refs/heads/masters"
			assertEquals(1
		} else {
			assertResults(cmds
			assertRefs(
					"refs/heads/master"
					"refs/heads/masters"
			assertEquals(2
		}
	}

	@Test
	public void simpleForce() throws IOException {
		writeLooseRef("refs/heads/master"
		writeLooseRef("refs/heads/masters"

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(B
		execute(newBatchUpdate(cmds).setAllowNonFastForwards(true));

		assertResults(cmds
		assertRefs(
				"refs/heads/master"
				"refs/heads/masters"
		assertEquals(atomic ? 2 : 3
	}

	@Test
	public void nonFastForwardDoesNotDoExpensiveMergeCheck() throws IOException {
		writeLooseRef("refs/heads/master"

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(B
		try (RevWalk rw = new RevWalk(diskRepo) {
					@Override
					public boolean isMergedInto(RevCommit base
						throw new AssertionError("isMergedInto() should not be called");
					}
				}) {
			newBatchUpdate(cmds)
					.setAllowNonFastForwards(true)
					.execute(rw
		}

		assertResults(cmds
		assertRefs("refs/heads/master"
		assertEquals(2
	}

	@Test
	public void fileDirectoryConflict() throws IOException {
		writeLooseRef("refs/heads/master"
		writeLooseRef("refs/heads/masters"

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(zeroId()
				new ReceiveCommand(zeroId()
		execute(newBatchUpdate(cmds).setAllowNonFastForwards(true)

		if (atomic) {
			assertResults(cmds
					LOCK_FAILURE
			assertRefs(
					"refs/heads/master"
					"refs/heads/masters"
			assertEquals(1
		} else {
			assertResults(cmds
			assertRefs(
					"refs/heads/master"
					"refs/heads/masters"
			assertEquals(2
		}
	}

	@Test
	public void conflictThanksToDelete() throws IOException {
		writeLooseRef("refs/heads/master"
		writeLooseRef("refs/heads/masters"

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(zeroId()
				new ReceiveCommand(B
		execute(newBatchUpdate(cmds).setAllowNonFastForwards(true));

		assertResults(cmds
		assertRefs(
				"refs/heads/master"
				"refs/heads/masters/x"
		if (atomic) {
			assertEquals(2
		} else {
			assertTrue(refsChangedEvents >= 4);
		}
	}

	@Test
	public void updateToMissingObject() throws IOException {
		writeLooseRef("refs/heads/master"

		ObjectId bad =
				ObjectId.fromString("deadbeefdeadbeefdeadbeefdeadbeefdeadbeef");
		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(zeroId()
		execute(newBatchUpdate(cmds).setAllowNonFastForwards(true)

		if (atomic) {
			assertResults(cmds
			assertRefs("refs/heads/master"
			assertEquals(1
		} else {
			assertResults(cmds
			assertRefs(
					"refs/heads/master"
					"refs/heads/foo2"
			assertEquals(2
		}
	}

	@Test
	public void addMissingObject() throws IOException {
		writeLooseRef("refs/heads/master"

		ObjectId bad =
				ObjectId.fromString("deadbeefdeadbeefdeadbeefdeadbeefdeadbeef");
		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(zeroId()
		execute(newBatchUpdate(cmds).setAllowNonFastForwards(true)

		if (atomic) {
			assertResults(cmds
			assertRefs("refs/heads/master"
			assertEquals(1
		} else {
			assertResults(cmds
			assertRefs("refs/heads/master"
			assertEquals(2
		}
	}

	@Test
	public void oneNonExistentRef() throws IOException {
		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(zeroId()
		execute(newBatchUpdate(cmds).setAllowNonFastForwards(true));

		if (atomic) {
			assertResults(cmds
			assertRefs();
			assertEquals(0
		} else {
			assertResults(cmds
			assertRefs("refs/heads/foo2"
			assertEquals(1
		}
	}

	@Test
	public void oneRefWrongOldValue() throws IOException {
		writeLooseRef("refs/heads/master"

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(B
				new ReceiveCommand(zeroId()
		execute(newBatchUpdate(cmds).setAllowNonFastForwards(true));

		if (atomic) {
			assertResults(cmds
			assertRefs("refs/heads/master"
			assertEquals(1
		} else {
			assertResults(cmds
			assertRefs(
					"refs/heads/master"
					"refs/heads/foo2"
			assertEquals(2
		}
	}

	@Test
	public void nonExistentRef() throws IOException {
		writeLooseRef("refs/heads/master"

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(A
		execute(newBatchUpdate(cmds).setAllowNonFastForwards(true));

		if (atomic) {
			assertResults(cmds
			assertRefs("refs/heads/master"
			assertEquals(1
		} else {
			assertResults(cmds
			assertRefs("refs/heads/master"
			assertEquals(2
		}
	}

	@Test
	public void noRefLog() throws IOException {
		writeRef("refs/heads/master"

		Map<String
				getLastReflogs("refs/heads/master"
		assertEquals(Collections.singleton("refs/heads/master")

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(zeroId()
		execute(newBatchUpdate(cmds).setAllowNonFastForwards(true));

		assertResults(cmds
		assertRefs(
				"refs/heads/master"
				"refs/heads/branch"
		assertEquals(atomic ? 2 : 3
		assertReflogUnchanged(oldLogs
		assertReflogUnchanged(oldLogs
	}

	@Test
	public void reflogDefaultIdent() throws IOException {
		writeRef("refs/heads/master"
		writeRef("refs/heads/branch2"

		Map<String
				"refs/heads/master"
		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(zeroId()
		execute(
				newBatchUpdate(cmds)
						.setAllowNonFastForwards(true)
						.setRefLogMessage("a reflog"

		assertResults(cmds
		assertRefs(
				"refs/heads/master"
				"refs/heads/branch1"
				"refs/heads/branch2"
		assertEquals(atomic ? 3 : 4
		assertReflogEquals(
				reflog(A
				getLastReflog("refs/heads/master"));
		assertReflogEquals(
				reflog(zeroId()
				getLastReflog("refs/heads/branch1"));
		assertReflogUnchanged(oldLogs
	}

	@Test
	public void reflogAppendStatusNoMessage() throws IOException {
		writeRef("refs/heads/master"
		writeRef("refs/heads/branch1"

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(B
				new ReceiveCommand(zeroId()
		execute(
				newBatchUpdate(cmds)
						.setAllowNonFastForwards(true)
						.setRefLogMessage(null

		assertResults(cmds
		assertRefs(
				"refs/heads/master"
				"refs/heads/branch1"
				"refs/heads/branch2"
		assertEquals(atomic ? 3 : 5
		assertReflogEquals(
				reflog(A
				getLastReflog("refs/heads/master"));
		assertReflogEquals(
				reflog(B
				getLastReflog("refs/heads/branch1"));
		assertReflogEquals(
				reflog(zeroId()
				getLastReflog("refs/heads/branch2"));
	}

	@Test
	public void reflogAppendStatusFastForward() throws IOException {
		writeRef("refs/heads/master"

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
		execute(newBatchUpdate(cmds).setRefLogMessage(null

		assertResults(cmds
		assertRefs("refs/heads/master"
		assertEquals(2
		assertReflogEquals(
				reflog(A
				getLastReflog("refs/heads/master"));
	}

	@Test
	public void reflogAppendStatusWithMessage() throws IOException {
		writeRef("refs/heads/master"

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(zeroId()
		execute(newBatchUpdate(cmds).setRefLogMessage("a reflog"

		assertResults(cmds
		assertRefs(
				"refs/heads/master"
				"refs/heads/branch"
		assertEquals(atomic ? 2 : 3
		assertReflogEquals(
				reflog(A
				getLastReflog("refs/heads/master"));
		assertReflogEquals(
				reflog(zeroId()
				getLastReflog("refs/heads/branch"));
	}

	@Test
	public void reflogCustomIdent() throws IOException {
		writeRef("refs/heads/master"

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(zeroId()
		PersonIdent ident = new PersonIdent("A Reflog User"
		execute(
				newBatchUpdate(cmds)
						.setRefLogMessage("a reflog"
						.setRefLogIdent(ident));

		assertResults(cmds
		assertEquals(atomic ? 2 : 3
		assertRefs(
				"refs/heads/master"
				"refs/heads/branch"
		assertReflogEquals(
				reflog(A
				getLastReflog("refs/heads/master")
				true);
		assertReflogEquals(
				reflog(zeroId()
				getLastReflog("refs/heads/branch")
				true);
	}

	@Test
	public void reflogDelete() throws IOException {
		writeRef("refs/heads/master"
		writeRef("refs/heads/branch"
		assertEquals(
				2

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(A
		execute(newBatchUpdate(cmds).setRefLogMessage("a reflog"

		assertResults(cmds
		assertRefs("refs/heads/branch"
		assertEquals(atomic ? 3 : 4
		assertNull(getLastReflog("refs/heads/master"));
		assertReflogEquals(
				reflog(A
				getLastReflog("refs/heads/branch"));
	}

	@Test
	public void reflogFileDirectoryConflict() throws IOException {
		writeRef("refs/heads/master"

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(zeroId()
		execute(newBatchUpdate(cmds).setRefLogMessage("a reflog"

		assertResults(cmds
		assertRefs("refs/heads/master/x"
		assertEquals(atomic ? 2 : 3
		assertNull(getLastReflog("refs/heads/master"));
		assertReflogEquals(
				reflog(zeroId()
				getLastReflog("refs/heads/master/x"));
	}

	@Test
	public void reflogOnLockFailure() throws IOException {
		writeRef("refs/heads/master"

		Map<String
				getLastReflogs("refs/heads/master"

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(A
		execute(newBatchUpdate(cmds).setRefLogMessage("a reflog"

		if (atomic) {
			assertResults(cmds
			assertEquals(1
			assertReflogUnchanged(oldLogs
			assertReflogUnchanged(oldLogs
		} else {
			assertResults(cmds
			assertEquals(2
			assertReflogEquals(
					reflog(A
					getLastReflog("refs/heads/master"));
			assertReflogUnchanged(oldLogs
		}
	}

	@Test
	public void overrideRefLogMessage() throws Exception {
		writeRef("refs/heads/master"

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(zeroId()
		cmds.get(0).setRefLogMessage("custom log"
		PersonIdent ident = new PersonIdent(diskRepo);
		execute(
				newBatchUpdate(cmds)
						.setRefLogIdent(ident)
						.setRefLogMessage("a reflog"

		assertResults(cmds
		assertEquals(atomic ? 2 : 3
		assertReflogEquals(
				reflog(A
				getLastReflog("refs/heads/master")
				true);
		assertReflogEquals(
				reflog(zeroId()
				getLastReflog("refs/heads/branch")
				true);
	}

	@Test
	public void overrideDisableRefLog() throws Exception {
		writeRef("refs/heads/master"

		Map<String
				getLastReflogs("refs/heads/master"

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(zeroId()
		cmds.get(0).disableRefLog();
		execute(newBatchUpdate(cmds).setRefLogMessage("a reflog"

		assertResults(cmds
		assertEquals(atomic ? 2 : 3
		assertReflogUnchanged(oldLogs
		assertReflogEquals(
				reflog(zeroId()
				getLastReflog("refs/heads/branch"));
	}

	@Test
	public void refLogNotWrittenWithoutConfigOption() throws Exception {
		setLogAllRefUpdates(false);
		writeRef("refs/heads/master"

		Map<String
				getLastReflogs("refs/heads/master"
		assertTrue(oldLogs.isEmpty());

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(zeroId()
		execute(newBatchUpdate(cmds).setRefLogMessage("a reflog"

		assertResults(cmds
		assertReflogUnchanged(oldLogs
		assertReflogUnchanged(oldLogs
	}

	@Test
	public void forceRefLogInUpdate() throws Exception {
		setLogAllRefUpdates(false);
		writeRef("refs/heads/master"
		assertTrue(
				getLastReflogs("refs/heads/master"

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(zeroId()
		execute(
				newBatchUpdate(cmds)
						.setRefLogMessage("a reflog"
						.setForceRefLog(true));

		assertResults(cmds
		assertReflogEquals(
				reflog(A
				getLastReflog("refs/heads/master"));
		assertReflogEquals(
				reflog(zeroId()
				getLastReflog("refs/heads/branch"));
	}

	@Test
	public void forceRefLogInCommand() throws Exception {
		setLogAllRefUpdates(false);
		writeRef("refs/heads/master"

		Map<String
				getLastReflogs("refs/heads/master"
		assertTrue(oldLogs.isEmpty());

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(zeroId()
		cmds.get(1).setForceRefLog(true);
		execute(newBatchUpdate(cmds).setRefLogMessage("a reflog"

		assertResults(cmds
		assertReflogUnchanged(oldLogs
		assertReflogEquals(
				reflog(zeroId()
				getLastReflog("refs/heads/branch"));
	}

	@Test
	public void packedRefsLockFailure() throws Exception {
		writeLooseRef("refs/heads/master"

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(zeroId()

		LockFile myLock = refdir.lockPackedRefs();
		try {
			execute(newBatchUpdate(cmds).setAllowNonFastForwards(true));

			assertFalse(getLockFile("refs/heads/master").exists());
			assertFalse(getLockFile("refs/heads/branch").exists());

			if (atomic) {
				assertResults(cmds
				assertRefs("refs/heads/master"
				assertEquals(1
			} else {
				assertResults(cmds
				assertRefs(
						"refs/heads/master"
						"refs/heads/branch"
				assertEquals(3
			}
		} finally {
			myLock.unlock();
		}
	}

	@Test
	public void oneRefLockFailure() throws Exception {
		writeLooseRef("refs/heads/master"

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(zeroId()
				new ReceiveCommand(A

		LockFile myLock = new LockFile(refdir.fileFor("refs/heads/master"));
		assertTrue(myLock.lock());
		try {
			execute(newBatchUpdate(cmds).setAllowNonFastForwards(true));

			assertFalse(LockFile.getLockFile(refdir.packedRefsFile).exists());
			assertFalse(getLockFile("refs/heads/branch").exists());

			if (atomic) {
				assertResults(cmds
				assertRefs("refs/heads/master"
				assertEquals(1
			} else {
				assertResults(cmds
				assertRefs(
						"refs/heads/branch"
						"refs/heads/master"
				assertEquals(2
			}
		} finally {
			myLock.unlock();
		}
	}

	@Test
	public void singleRefUpdateDoesNotRequirePackedRefsLock() throws Exception {
		writeLooseRef("refs/heads/master"

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A

		LockFile myLock = refdir.lockPackedRefs();
		try {
			execute(newBatchUpdate(cmds).setAllowNonFastForwards(true));

			assertFalse(getLockFile("refs/heads/master").exists());
			assertResults(cmds
			assertEquals(2
			assertRefs("refs/heads/master"
		} finally {
			myLock.unlock();
		}
	}

	@Test
	public void atomicUpdateRespectsInProcessLock() throws Exception {
		assumeTrue(atomic);

		writeLooseRef("refs/heads/master"

		List<ReceiveCommand> cmds = Arrays.asList(
				new ReceiveCommand(A
				new ReceiveCommand(zeroId()

		Thread t = new Thread(() -> {
			try {
				execute(newBatchUpdate(cmds).setAllowNonFastForwards(true));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});

		ReentrantLock l = refdir.inProcessPackedRefsLock;
		l.lock();
		try {
			t.start();
			long timeoutSecs = 10;
			long startNanos = System.nanoTime();

			while (l.getQueueLength() == 0) {
				long elapsedNanos = System.nanoTime() - startNanos;
				assertTrue(
						"timed out waiting for work thread to attempt to acquire lock"
						NANOSECONDS.toSeconds(elapsedNanos) < timeoutSecs);
				Thread.sleep(3);
			}

			l.unlock();
			t.join(SECONDS.toMillis(timeoutSecs));
			assertFalse(t.isAlive());
		} finally {
			if (l.isHeldByCurrentThread()) {
				l.unlock();
			}
		}

		assertResults(cmds
		assertEquals(2
		assertRefs(
				"refs/heads/master"
				"refs/heads/branch"
	}

	private void setLogAllRefUpdates(boolean enable) throws Exception {
		StoredConfig cfg = diskRepo.getConfig();
		cfg.load();
		cfg.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_LOGALLREFUPDATES
		cfg.save();
	}

	private void writeLooseRef(String name
		write(new File(diskRepo.getDirectory()
	}

	private void writeRef(String name
		RefUpdate u = diskRepo.updateRef(name);
		u.setRefLogMessage(getClass().getSimpleName()
		u.setForceUpdate(true);
		u.setNewObjectId(id);
		RefUpdate.Result r = u.update();
		switch (r) {
			case NEW:
			case FORCED:
				return;
			default:
				throw new IOException("Got " + r + " while updating " + name);
		}
	}

	private BatchRefUpdate newBatchUpdate(List<ReceiveCommand> cmds) {
		BatchRefUpdate u = refdir.newBatchUpdate();
		if (atomic) {
			assertTrue(u.isAtomic());
		} else {
			u.setAtomic(false);
		}
		u.addCommand(cmds);
		return u;
	}

	private void execute(BatchRefUpdate u) throws IOException {
		execute(u
	}

	private void execute(BatchRefUpdate u
		try (RevWalk rw = new RevWalk(diskRepo)) {
			u.execute(rw
					strictWork ? new StrictWorkMonitor() : NullProgressMonitor.INSTANCE);
		}
	}

	private void assertRefs(Object... args) throws IOException {
		if (args.length % 2 != 0) {
			throw new IllegalArgumentException(
					"expected even number of args: " + Arrays.toString(args));
		}

		Map<String
		for (int i = 0; i < args.length; i += 2) {
			expected.put((String) args[i]
		}

		Map<String
		Ref actualHead = refs.remove(Constants.HEAD);
		if (actualHead != null) {
			String actualLeafName = actualHead.getLeaf().getName();
			assertEquals(
					"expected HEAD to point to refs/heads/master
					"refs/heads/master"
			AnyObjectId expectedMaster = expected.get("refs/heads/master");
			assertNotNull("expected master ref since HEAD exists"
			assertEquals(expectedMaster
		}

		Map<String
		refs.forEach((n

		assertEquals(expected.keySet()
		actual.forEach((n
	}

	enum Result {
		OK(ReceiveCommand.Result.OK)
		LOCK_FAILURE(ReceiveCommand.Result.LOCK_FAILURE)
		REJECTED_NONFASTFORWARD(ReceiveCommand.Result.REJECTED_NONFASTFORWARD)
		REJECTED_MISSING_OBJECT(ReceiveCommand.Result.REJECTED_MISSING_OBJECT)
		TRANSACTION_ABORTED(ReceiveCommand::isTransactionAborted);

		final Predicate<? super ReceiveCommand> p;

		private Result(Predicate<? super ReceiveCommand> p) {
			this.p = p;
		}

		private Result(ReceiveCommand.Result result) {
			this(c -> c.getResult() == result);
		}
	}

	private void assertResults(
			List<ReceiveCommand> cmds
		if (expected.length != cmds.size()) {
			throw new IllegalArgumentException(
					"expected " + cmds.size() + " result args");
		}
		for (int i = 0; i < cmds.size(); i++) {
			ReceiveCommand c = cmds.get(i);
			Result r = expected[i];
			assertTrue(
					String.format(
							"result of command (%d) should be %s: %s %s%s"
							Integer.valueOf(i)
							c.getResult()
							c.getMessage() != null ? " (" + c.getMessage() + ")" : "")
					r.p.test(c));
		}
	}

	private Map<String
			throws IOException {
		Map<String
		for (String name : names) {
			ReflogEntry e = getLastReflog(name);
			if (e != null) {
				result.put(name
			}
		}
		return result;
	}

	private ReflogEntry getLastReflog(String name) throws IOException {
		ReflogReader r = diskRepo.getReflogReader(name);
		if (r == null) {
			return null;
		}
		return r.getLastEntry();
	}

	private File getLockFile(String refName) {
		return LockFile.getLockFile(refdir.fileFor(refName));
	}

	private void assertReflogUnchanged(
			Map<String
		assertReflogEquals(old.get(name)
	}

	private static void assertReflogEquals(
			ReflogEntry expected
		assertReflogEquals(expected
	}

	private static void assertReflogEquals(
			ReflogEntry expected
		if (expected == null) {
			assertNull(actual);
			return;
		}
		assertNotNull(actual);
		assertEquals(expected.getOldId()
		assertEquals(expected.getNewId()
		if (strictTime) {
			assertEquals(expected.getWho()
		} else {
			assertEquals(expected.getWho().getName()
			assertEquals(
					expected.getWho().getEmailAddress()
					actual.getWho().getEmailAddress());
		}
		assertEquals(expected.getComment()
	}

	private static ReflogEntry reflog(ObjectId oldId
			PersonIdent who
		return new ReflogEntry() {
			@Override
			public ObjectId getOldId() {
				return oldId;
			}

			@Override
			public ObjectId getNewId() {
				return newId;
			}

			@Override
			public PersonIdent getWho() {
				return who;
			}

			@Override
			public String getComment() {
				return comment;
			}

			@Override
			public CheckoutEntry parseCheckout() {
				throw new UnsupportedOperationException();
			}
		};
	}
}
