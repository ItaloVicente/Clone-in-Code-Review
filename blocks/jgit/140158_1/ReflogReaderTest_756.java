
package org.eclipse.jgit.internal.storage.file;

import static org.eclipse.jgit.junit.Assert.assertEquals;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.lib.Constants.LOCK_SUFFIX;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefRename;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.RefUpdate.Result;
import org.eclipse.jgit.lib.ReflogEntry;
import org.eclipse.jgit.lib.ReflogReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.test.resources.SampleDataRepositoryTestCase;
import org.junit.Test;

public class RefUpdateTest extends SampleDataRepositoryTestCase {

	private void writeSymref(String src
		RefUpdate u = db.updateRef(src);
		switch (u.link(dst)) {
		case NEW:
		case FORCED:
		case NO_CHANGE:
			break;
		default:
			fail("link " + src + " to " + dst);
		}
	}

	private RefUpdate updateRef(String name) throws IOException {
		final RefUpdate ref = db.updateRef(name);
		ref.setNewObjectId(db.resolve(Constants.HEAD));
		return ref;
	}

	private void delete(RefUpdate ref
			throws IOException {
		delete(ref
	}

	private void delete(final RefUpdate ref
			final boolean exists
		delete(db
	}

	private void delete(Repository repo
			final Result expected
			throws IOException {
		assertEquals(exists
		assertEquals(expected
		assertEquals(!removed
	}

	private Optional<Ref> getRef(Repository repo
			throws IOException {
		return getRef(repo.getRefDatabase().getRefs()
	}

	private Optional<Ref> getRef(List<Ref> refs
		return refs.stream().filter(r -> r.getName().equals(name)).findAny();
	}

	@Test
	public void testNoCacheObjectIdSubclass() throws IOException {
		final String newRef = "refs/heads/abc";
		final RefUpdate ru = updateRef(newRef);
		final SubclassedId newid = new SubclassedId(ru.getNewObjectId());
		ru.setNewObjectId(newid);
		Result update = ru.update();
		assertEquals(Result.NEW
		final Ref r = getRef(db
		assertEquals(newRef
		assertNotNull(r.getObjectId());
		assertNotSame(newid
		assertSame(ObjectId.class
		assertEquals(newid
		List<ReflogEntry> reverseEntries1 = db
				.getReflogReader("refs/heads/abc").getReverseEntries();
		ReflogEntry entry1 = reverseEntries1.get(0);
		assertEquals(1
		assertEquals(ObjectId.zeroId()
		assertEquals(r.getObjectId()
		assertEquals(new PersonIdent(db).toString()
		assertEquals(""
		List<ReflogEntry> reverseEntries2 = db.getReflogReader("HEAD")
				.getReverseEntries();
		assertEquals(0
	}

	@Test
	public void testNewNamespaceConflictWithLoosePrefixNameExists()
			throws IOException {
		final String newRef = "refs/heads/z";
		final RefUpdate ru = updateRef(newRef);
		Result update = ru.update();
		assertEquals(Result.NEW
		final String newRef2 = "refs/heads/z/a";
		final RefUpdate ru2 = updateRef(newRef2);
		Result update2 = ru2.update();
		assertEquals(Result.LOCK_FAILURE
		assertEquals(1
		assertEquals(0
	}

	@Test
	public void testNewNamespaceConflictWithPackedPrefixNameExists()
			throws IOException {
		final String newRef = "refs/heads/master/x";
		final RefUpdate ru = updateRef(newRef);
		Result update = ru.update();
		assertEquals(Result.LOCK_FAILURE
		assertNull(db.getReflogReader("refs/heads/master/x"));
		assertEquals(0
	}

	@Test
	public void testNewNamespaceConflictWithLoosePrefixOfExisting()
			throws IOException {
		final String newRef = "refs/heads/z/a";
		final RefUpdate ru = updateRef(newRef);
		Result update = ru.update();
		assertEquals(Result.NEW
		final String newRef2 = "refs/heads/z";
		final RefUpdate ru2 = updateRef(newRef2);
		Result update2 = ru2.update();
		assertEquals(Result.LOCK_FAILURE
		assertEquals(1
		assertNull(db.getReflogReader("refs/heads/z"));
		assertEquals(0
	}

	@Test
	public void testNewNamespaceConflictWithPackedPrefixOfExisting()
			throws IOException {
		final String newRef = "refs/heads/prefix";
		final RefUpdate ru = updateRef(newRef);
		Result update = ru.update();
		assertEquals(Result.LOCK_FAILURE
		assertNull(db.getReflogReader("refs/heads/prefix"));
		assertEquals(0
	}

	@Test
	public void testDeleteHEADreferencedRef() throws IOException {
		ObjectId pid = db.resolve("refs/heads/master^");
		RefUpdate updateRef = db.updateRef("refs/heads/master");
		updateRef.setNewObjectId(pid);
		updateRef.setForceUpdate(true);
		Result update = updateRef.update();
		assertEquals(Result.FORCED

		RefUpdate updateRef2 = db.updateRef("refs/heads/master");
		Result delete = updateRef2.delete();
		assertEquals(Result.REJECTED_CURRENT_BRANCH
		assertEquals(pid
		assertEquals(1
		assertEquals(0
	}

	@Test
	public void testLooseDelete() throws IOException {
		final String newRef = "refs/heads/abc";
		RefUpdate ref = updateRef(newRef);
		delete(ref
		assertNull(db.getReflogReader("refs/heads/abc"));
	}

	@Test
	public void testDeleteHead() throws IOException {
		final RefUpdate ref = updateRef(Constants.HEAD);
		delete(ref
		assertEquals(0
		assertEquals(0
	}

	@Test
	public void testDeleteHeadInBareRepo() throws IOException {
		Repository bareRepo = createBareRepository();
		String master = "refs/heads/master";
		Ref head = bareRepo.exactRef(Constants.HEAD);
		assertNotNull(head);
		assertTrue(head.isSymbolic());
		assertEquals(master
		assertNull(head.getObjectId());
		assertNull(bareRepo.exactRef(master));

		ObjectId blobId;
		try (ObjectInserter ins = bareRepo.newObjectInserter()) {
			blobId = ins.insert(Constants.OBJ_BLOB
			ins.flush();
		}

		RefUpdate ref = bareRepo.updateRef(Constants.HEAD);
		ref.setNewObjectId(blobId);
		assertEquals(Result.NEW

		head = bareRepo.exactRef(Constants.HEAD);
		assertTrue(head.isSymbolic());
		assertEquals(master
		assertEquals(blobId
		assertEquals(blobId

		ref = bareRepo.updateRef(Constants.HEAD);
		ref.setExpectedOldObjectId(blobId);
		ref.setForceUpdate(true);
		delete(bareRepo

		head = bareRepo.exactRef(Constants.HEAD);
		assertNotNull(head);
		assertTrue(head.isSymbolic());
		assertEquals(master
		assertNull(head.getObjectId());
		assertNull(bareRepo.exactRef(master));
	}

	@Test
	public void testDeleteSymref() throws IOException {
		RefUpdate dst = updateRef("refs/heads/abc");
		assertEquals(Result.NEW
		ObjectId id = dst.getNewObjectId();

		RefUpdate u = db.updateRef("refs/symref");
		assertEquals(Result.NEW

		Ref ref = db.exactRef(u.getName());
		assertNotNull(ref);
		assertTrue(ref.isSymbolic());
		assertEquals(dst.getName()
		assertEquals(id

		u = db.updateRef(u.getName());
		u.setDetachingSymbolicRef();
		u.setForceUpdate(true);
		assertEquals(Result.FORCED

		assertNull(db.exactRef(u.getName()));
		ref = db.exactRef(dst.getName());
		assertNotNull(ref);
		assertFalse(ref.isSymbolic());
		assertEquals(id
	}

	@Test
	public void testDeleteLooseAndItsDirectory() throws IOException {
		ObjectId pid = db.resolve("refs/heads/c^");
		RefUpdate updateRef = db.updateRef("refs/heads/z/c");
		updateRef.setNewObjectId(pid);
		updateRef.setForceUpdate(true);
		updateRef.setRefLogMessage("new test ref"
		Result update = updateRef.update();
		assertEquals(Result.NEW
		assertTrue(new File(db.getDirectory()
				.exists());
		assertTrue(new File(db.getDirectory()

		RefUpdate updateRef2 = db.updateRef("refs/heads/z/c");
		updateRef2.setForceUpdate(true);
		Result delete = updateRef2.delete();
		assertEquals(Result.FORCED
		assertNull(db.resolve("refs/heads/z/c"));
		assertFalse(new File(db.getDirectory()
				.exists());
		assertFalse(new File(db.getDirectory()
	}

	@Test
	public void testDeleteNotFound() throws IOException {
		final RefUpdate ref = updateRef("refs/heads/xyz");
		delete(ref
	}

	@Test
	public void testDeleteFastForward() throws IOException {
		final RefUpdate ref = updateRef("refs/heads/a");
		delete(ref
	}

	@Test
	public void testDeleteForce() throws IOException {
		final RefUpdate ref = db.updateRef("refs/heads/b");
		ref.setNewObjectId(db.resolve("refs/heads/a"));
		delete(ref
		ref.setForceUpdate(true);
		delete(ref
	}

	@Test
	public void testDeleteWithoutHead() throws IOException {
		RefUpdate refUpdate = db.updateRef(Constants.HEAD
		refUpdate.setForceUpdate(true);
		refUpdate.setNewObjectId(ObjectId.zeroId());
		Result updateResult = refUpdate.update();
		assertEquals(Result.FORCED
		Result deleteHeadResult = db.updateRef(Constants.HEAD).delete();
		assertEquals(Result.NO_CHANGE

		db.updateRef(Constants.R_HEADS + "master").delete();
	}

	@Test
	public void testRefKeySameAsName() {
		@SuppressWarnings("deprecation")
		Map<String
		for (Entry<String
			assertEquals(e.getKey()
		}
	}

	@Test
	public void testUpdateRefForward() throws IOException {
		ObjectId ppid = db.resolve("refs/heads/master^");
		ObjectId pid = db.resolve("refs/heads/master");

		RefUpdate updateRef = db.updateRef("refs/heads/master");
		updateRef.setNewObjectId(ppid);
		updateRef.setForceUpdate(true);
		Result update = updateRef.update();
		assertEquals(Result.FORCED
		assertEquals(ppid

		RefUpdate updateRef2 = db.updateRef("refs/heads/master");
		updateRef2.setNewObjectId(pid);
		Result update2 = updateRef2.update();
		assertEquals(Result.FAST_FORWARD
		assertEquals(pid
	}

	@Test
	public void testUpdateRefDetached() throws Exception {
		ObjectId pid = db.resolve("refs/heads/master");
		ObjectId ppid = db.resolve("refs/heads/master^");
		RefUpdate updateRef = db.updateRef("HEAD"
		updateRef.setForceUpdate(true);
		updateRef.setNewObjectId(ppid);
		Result update = updateRef.update();
		assertEquals(Result.FORCED
		assertEquals(ppid
		Ref ref = db.exactRef("HEAD");
		assertEquals("HEAD"
		assertTrue("is detached"

		assertEquals(pid
		ReflogReader reflogReader = db.getReflogReader("HEAD");
		ReflogEntry e = reflogReader.getReverseEntries().get(0);
		assertEquals(pid
		assertEquals(ppid
		assertEquals("GIT_COMMITTER_EMAIL"
		assertEquals("GIT_COMMITTER_NAME"
		assertEquals(1250379778000L
	}

	@Test
	public void testUpdateRefDetachedUnbornHead() throws Exception {
		ObjectId ppid = db.resolve("refs/heads/master^");
		writeSymref("HEAD"
		RefUpdate updateRef = db.updateRef("HEAD"
		updateRef.setForceUpdate(true);
		updateRef.setNewObjectId(ppid);
		Result update = updateRef.update();
		assertEquals(Result.NEW
		assertEquals(ppid
		Ref ref = db.exactRef("HEAD");
		assertEquals("HEAD"
		assertTrue("is detached"

		assertNull(db.resolve("refs/heads/unborn"));
		ReflogReader reflogReader = db.getReflogReader("HEAD");
		ReflogEntry e = reflogReader.getReverseEntries().get(0);
		assertEquals(ObjectId.zeroId()
		assertEquals(ppid
		assertEquals("GIT_COMMITTER_EMAIL"
		assertEquals("GIT_COMMITTER_NAME"
		assertEquals(1250379778000L
	}

	@Test
	public void testDeleteLoosePacked() throws IOException {
		ObjectId pid = db.resolve("refs/heads/c^");
		RefUpdate updateRef = db.updateRef("refs/heads/c");
		updateRef.setNewObjectId(pid);
		updateRef.setForceUpdate(true);
		Result update = updateRef.update();
		assertEquals(Result.FORCED

		RefUpdate updateRef2 = db.updateRef("refs/heads/c");
		updateRef2.setForceUpdate(true);
		Result delete = updateRef2.delete();
		assertEquals(Result.FORCED
		assertNull(db.resolve("refs/heads/c"));
	}

	@Test
	public void testUpdateRefNoChange() throws IOException {
		ObjectId pid = db.resolve("refs/heads/master");
		RefUpdate updateRef = db.updateRef("refs/heads/master");
		updateRef.setNewObjectId(pid);
		Result update = updateRef.update();
		assertEquals(Result.NO_CHANGE
		assertEquals(pid
	}

	@Test
	public void testRefsCacheAfterUpdate() throws Exception {
		List<Ref> allRefs = db.getRefDatabase().getRefs();
		ObjectId oldValue = db.resolve("HEAD");
		ObjectId newValue = db.resolve("HEAD^");
		RefUpdate updateRef = db.updateRef(Constants.HEAD);
		updateRef.setForceUpdate(true);
		updateRef.setNewObjectId(newValue);
		Result update = updateRef.update();
		assertEquals(Result.FORCED

		updateRef = db.updateRef(Constants.HEAD);
		updateRef.setNewObjectId(oldValue);
		update = updateRef.update();
		assertEquals(Result.FAST_FORWARD

		allRefs = db.getRefDatabase().getRefs();
		Ref master = getRef(allRefs
		Ref head = getRef(allRefs
		assertEquals("refs/heads/master"
		assertEquals("HEAD"
		assertTrue("is symbolic reference"
		assertSame(master
	}

	@Test
	public void testRefsCacheAfterUpdateLooseOnly() throws Exception {
		List<Ref> allRefs = db.getRefDatabase().getRefs();
		ObjectId oldValue = db.resolve("HEAD");
		writeSymref(Constants.HEAD
		RefUpdate updateRef = db.updateRef(Constants.HEAD);
		updateRef.setForceUpdate(true);
		updateRef.setNewObjectId(oldValue);
		Result update = updateRef.update();
		assertEquals(Result.NEW

		allRefs = db.getRefDatabase().getRefs();
		Ref head = getRef(allRefs
		Ref newref = getRef(allRefs
		assertEquals("refs/heads/newref"
		assertEquals("HEAD"
		assertTrue("is symbolic reference"
		assertSame(newref
	}

	@Test
	public void testUpdateRefLockFailureWrongOldValue() throws IOException {
		ObjectId pid = db.resolve("refs/heads/master");
		RefUpdate updateRef = db.updateRef("refs/heads/master");
		updateRef.setNewObjectId(pid);
		updateRef.setExpectedOldObjectId(db.resolve("refs/heads/master^"));
		Result update = updateRef.update();
		assertEquals(Result.LOCK_FAILURE
		assertEquals(pid
	}

	@Test
	public void testUpdateRefForwardWithCheck1() throws IOException {
		ObjectId ppid = db.resolve("refs/heads/master^");
		ObjectId pid = db.resolve("refs/heads/master");

		RefUpdate updateRef = db.updateRef("refs/heads/master");
		updateRef.setNewObjectId(ppid);
		updateRef.setForceUpdate(true);
		Result update = updateRef.update();
		assertEquals(Result.FORCED
		assertEquals(ppid

		RefUpdate updateRef2 = db.updateRef("refs/heads/master");
		updateRef2.setExpectedOldObjectId(ppid);
		updateRef2.setNewObjectId(pid);
		Result update2 = updateRef2.update();
		assertEquals(Result.FAST_FORWARD
		assertEquals(pid
	}

	@Test
	public void testUpdateRefForwardWithCheck2() throws IOException {
		ObjectId ppid = db.resolve("refs/heads/master^");
		ObjectId pid = db.resolve("refs/heads/master");

		RefUpdate updateRef = db.updateRef("refs/heads/master");
		updateRef.setNewObjectId(ppid);
		updateRef.setForceUpdate(true);
		Result update = updateRef.update();
		assertEquals(Result.FORCED
		assertEquals(ppid

		try (RevWalk rw = new RevWalk(db)) {
			RevCommit old = rw.parseCommit(ppid);
			RefUpdate updateRef2 = db.updateRef("refs/heads/master");
			updateRef2.setExpectedOldObjectId(old);
			updateRef2.setNewObjectId(pid);
			Result update2 = updateRef2.update();
			assertEquals(Result.FAST_FORWARD
			assertEquals(pid
		}
	}

	@Test
	public void testUpdateRefLockFailureLocked() throws IOException {
		ObjectId opid = db.resolve("refs/heads/master");
		ObjectId pid = db.resolve("refs/heads/master^");
		RefUpdate updateRef = db.updateRef("refs/heads/master");
		updateRef.setNewObjectId(pid);
		LockFile lockFile1 = new LockFile(new File(db.getDirectory()
				"refs/heads/master"));
		try {
			Result update = updateRef.update();
			assertEquals(Result.LOCK_FAILURE
			assertEquals(opid
			LockFile lockFile2 = new LockFile(new File(db.getDirectory()
		} finally {
			lockFile1.unlock();
		}
	}

	@Test
	public void testDeleteLoosePackedRejected() throws IOException {
		ObjectId pid = db.resolve("refs/heads/c^");
		ObjectId oldpid = db.resolve("refs/heads/c");
		RefUpdate updateRef = db.updateRef("refs/heads/c");
		updateRef.setNewObjectId(pid);
		Result update = updateRef.update();
		assertEquals(Result.REJECTED
		assertEquals(oldpid
	}

	@Test
	public void testRenameBranchNoPreviousLog() throws IOException {
		assertFalse("precondition
				.getDirectory()
		ObjectId rb = db.resolve("refs/heads/b");
		ObjectId oldHead = db.resolve(Constants.HEAD);
		RefRename renameRef = db.renameRef("refs/heads/b"
				"refs/heads/new/name");
		Result result = renameRef.rename();
		assertEquals(Result.RENAMED
		assertEquals(rb
		assertNull(db.resolve("refs/heads/b"));
		assertEquals(1
		assertEquals("Branch: renamed b to new/name"
				.getLastEntry().getComment());
		assertFalse(new File(db.getDirectory()
		assertEquals(oldHead
	}

	@Test
	public void testRenameBranchHasPreviousLog() throws IOException {
		ObjectId rb = db.resolve("refs/heads/b");
		ObjectId oldHead = db.resolve(Constants.HEAD);
		assertFalse("precondition for this test
				.equals(oldHead));
		writeReflog(db
		assertTrue("log on old branch"
				"logs/refs/heads/b").exists());
		RefRename renameRef = db.renameRef("refs/heads/b"
				"refs/heads/new/name");
		Result result = renameRef.rename();
		assertEquals(Result.RENAMED
		assertEquals(rb
		assertNull(db.resolve("refs/heads/b"));
		assertEquals(2
		assertEquals("Branch: renamed b to new/name"
				.getLastEntry().getComment());
		assertEquals("Just a message"
				.getReverseEntries().get(1).getComment());
		assertFalse(new File(db.getDirectory()
		assertEquals(oldHead
	}

	@Test
	public void testRenameCurrentBranch() throws IOException {
		ObjectId rb = db.resolve("refs/heads/b");
		writeSymref(Constants.HEAD
		ObjectId oldHead = db.resolve(Constants.HEAD);
		assertEquals("internal test condition
		writeReflog(db
		assertTrue("log on old branch"
				"logs/refs/heads/b").exists());
		RefRename renameRef = db.renameRef("refs/heads/b"
				"refs/heads/new/name");
		Result result = renameRef.rename();
		assertEquals(Result.RENAMED
		assertEquals(rb
		assertNull(db.resolve("refs/heads/b"));
		assertEquals("Branch: renamed b to new/name"
				"new/name").getLastEntry().getComment());
		assertFalse(new File(db.getDirectory()
		assertEquals(rb
		assertEquals(2
		assertEquals("Branch: renamed b to new/name"
		assertEquals("Just a message"
	}

	@Test
	public void testRenameBranchAlsoInPack() throws IOException {
		ObjectId rb = db.resolve("refs/heads/b");
		ObjectId rb2 = db.resolve("refs/heads/b~1");
		assertEquals(Ref.Storage.PACKED
		RefUpdate updateRef = db.updateRef("refs/heads/b");
		updateRef.setNewObjectId(rb2);
		updateRef.setForceUpdate(true);
		Result update = updateRef.update();
		assertEquals("internal check new ref is loose"
		assertEquals(Ref.Storage.LOOSE
		writeReflog(db
		assertTrue("log on old branch"
				"logs/refs/heads/b").exists());
		RefRename renameRef = db.renameRef("refs/heads/b"
				"refs/heads/new/name");
		Result result = renameRef.rename();
		assertEquals(Result.RENAMED
		assertEquals(rb2
		assertNull(db.resolve("refs/heads/b"));
		assertEquals("Branch: renamed b to new/name"
				"new/name").getLastEntry().getComment());
		assertEquals(3
		assertEquals("Branch: renamed b to new/name"
		assertEquals(0
		assertFalse(new File(db.getDirectory()

		try (Repository ndb = new FileRepository(db.getDirectory())) {
			assertEquals(rb2
			assertNull(ndb.resolve("refs/heads/b"));
		}
	}

	public void tryRenameWhenLocked(String toLock
			String toName
		writeSymref(Constants.HEAD
		ObjectId oldfromId = db.resolve(fromName);
		ObjectId oldHeadId = db.resolve(Constants.HEAD);
		writeReflog(db
		List<ReflogEntry> oldFromLog = db
				.getReflogReader(fromName).getReverseEntries();
		List<ReflogEntry> oldHeadLog = oldHeadId != null ? db
				.getReflogReader(Constants.HEAD).getReverseEntries() : null;

		assertTrue("internal check
				"logs/" + fromName).exists());

		LockFile lockFile = new LockFile(new File(db.getDirectory()
		try {
			assertTrue(lockFile.lock());

			RefRename renameRef = db.renameRef(fromName
			Result result = renameRef.rename();
			assertEquals(Result.LOCK_FAILURE

			assertExists(false
			if (!toLock.equals(toName))
				assertExists(false
			assertExists(true
			if (!toLock.equals(fromName))
				assertExists(false
			assertExists(false
			assertEquals(oldHeadId
			assertEquals(oldfromId
			assertNull(db.resolve(toName));
			assertEquals(oldFromLog.toString()
					.getReverseEntries().toString());
			if (oldHeadId != null && oldHeadLog != null)
				assertEquals(oldHeadLog.toString()
						Constants.HEAD).getReverseEntries().toString());
		} finally {
			lockFile.unlock();
		}
	}

	private void assertExists(boolean positive
		assertEquals(toName + (positive ? " " : " does not ") + "exist"
				positive
	}

	@Test
	public void testRenameBranchCannotLockAFileHEADisFromLockHEAD()
			throws IOException {
		tryRenameWhenLocked("HEAD"
				"refs/heads/b");
	}

	@Test
	public void testRenameBranchCannotLockAFileHEADisFromLockFrom()
			throws IOException {
		tryRenameWhenLocked("refs/heads/b"
				"refs/heads/new/name"
	}

	@Test
	public void testRenameBranchCannotLockAFileHEADisFromLockTo()
			throws IOException {
		tryRenameWhenLocked("refs/heads/new/name"
				"refs/heads/new/name"
	}

	@Test
	public void testRenameBranchCannotLockAFileHEADisToLockFrom()
			throws IOException {
		tryRenameWhenLocked("refs/heads/b"
				"refs/heads/new/name"
	}

	@Test
	public void testRenameBranchCannotLockAFileHEADisToLockTo()
			throws IOException {
		tryRenameWhenLocked("refs/heads/new/name"
				"refs/heads/new/name"
	}

	@Test
	public void testRenameBranchCannotLockAFileHEADisOtherLockFrom()
			throws IOException {
		tryRenameWhenLocked("refs/heads/b"
				"refs/heads/new/name"
	}

	@Test
	public void testRenameBranchCannotLockAFileHEADisOtherLockTo()
			throws IOException {
		tryRenameWhenLocked("refs/heads/new/name"
				"refs/heads/new/name"
	}

	@Test
	public void testRenameRefNameColission1avoided() throws IOException {
		ObjectId rb = db.resolve("refs/heads/b");
		writeSymref(Constants.HEAD
		RefUpdate updateRef = db.updateRef("refs/heads/a");
		updateRef.setNewObjectId(rb);
		updateRef.setRefLogMessage("Setup"
		assertEquals(Result.FAST_FORWARD
		ObjectId oldHead = db.resolve(Constants.HEAD);
		assertEquals(oldHead
		writeReflog(db
		assertTrue("internal check
				"logs/refs/heads/a").exists());

		RefRename renameRef = db.renameRef("refs/heads/a"
		Result result = renameRef.rename();
		assertEquals(Result.RENAMED
		assertNull(db.resolve("refs/heads/a"));
		assertEquals(rb
		assertEquals(3
		assertEquals("Branch: renamed a to a/b"
				.getReverseEntries().get(0).getComment());
		assertEquals("Just a message"
				.getReverseEntries().get(1).getComment());
		assertEquals("Setup"
				.get(2).getComment());
		assertEquals("Branch: renamed a to a/b"
				.getReverseEntries().get(0).getComment());
	}

	@Test
	public void testRenameRefNameColission2avoided() throws IOException {
		ObjectId rb = db.resolve("refs/heads/b");
		writeSymref(Constants.HEAD
		RefUpdate updateRef = db.updateRef("refs/heads/prefix/a");
		updateRef.setNewObjectId(rb);
		updateRef.setRefLogMessage("Setup"
		updateRef.setForceUpdate(true);
		assertEquals(Result.FORCED
		ObjectId oldHead = db.resolve(Constants.HEAD);
		assertEquals(oldHead
		writeReflog(db
		assertTrue("internal check
				"logs/refs/heads/prefix/a").exists());

		RefRename renameRef = db.renameRef("refs/heads/prefix/a"
				"refs/heads/prefix");
		Result result = renameRef.rename();
		assertEquals(Result.RENAMED

		assertNull(db.resolve("refs/heads/prefix/a"));
		assertEquals(rb
		assertEquals(3
		assertEquals("Branch: renamed prefix/a to prefix"
				"prefix").getReverseEntries().get(0).getComment());
		assertEquals("Just a message"
				.getReverseEntries().get(1).getComment());
		assertEquals("Setup"
				.get(2).getComment());
		assertEquals("Branch: renamed prefix/a to prefix"
				"HEAD").getReverseEntries().get(0).getComment());
	}

	@Test
	public void testCreateMissingObject() throws IOException {
		String name = "refs/heads/abc";
		ObjectId bad =
				ObjectId.fromString("deadbeefdeadbeefdeadbeefdeadbeefdeadbeef");
		RefUpdate ru = db.updateRef(name);
		ru.setNewObjectId(bad);
		Result update = ru.update();
		assertEquals(Result.REJECTED_MISSING_OBJECT

		Ref ref = db.exactRef(name);
		assertNull(ref);
	}

	@Test
	public void testUpdateMissingObject() throws IOException {
		String name = "refs/heads/abc";
		RefUpdate ru = updateRef(name);
		Result update = ru.update();
		assertEquals(Result.NEW
		ObjectId oldId = ru.getNewObjectId();

		ObjectId bad =
				ObjectId.fromString("deadbeefdeadbeefdeadbeefdeadbeefdeadbeef");
		ru = db.updateRef(name);
		ru.setNewObjectId(bad);
		update = ru.update();
		assertEquals(Result.REJECTED_MISSING_OBJECT

		Ref ref = db.exactRef(name);
		assertNotNull(ref);
		assertEquals(oldId
	}

	@Test
	public void testForceUpdateMissingObject() throws IOException {
		String name = "refs/heads/abc";
		RefUpdate ru = updateRef(name);
		Result update = ru.update();
		assertEquals(Result.NEW
		ObjectId oldId = ru.getNewObjectId();

		ObjectId bad =
				ObjectId.fromString("deadbeefdeadbeefdeadbeefdeadbeefdeadbeef");
		ru = db.updateRef(name);
		ru.setNewObjectId(bad);
		update = ru.forceUpdate();
		assertEquals(Result.REJECTED_MISSING_OBJECT

		Ref ref = db.exactRef(name);
		assertNotNull(ref);
		assertEquals(oldId
	}

	private static void writeReflog(Repository db
			String refName) throws IOException {
		RefDirectory refs = (RefDirectory) db.getRefDatabase();
		RefDirectoryUpdate update = refs.newUpdate(refName
		update.setNewObjectId(newId);
		refs.log(false
	}

	private static class SubclassedId extends ObjectId {
		SubclassedId(AnyObjectId src) {
			super(src);
		}
	}
}
