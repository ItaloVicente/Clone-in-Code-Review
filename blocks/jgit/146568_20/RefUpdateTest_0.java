
package org.eclipse.jgit.internal.storage.file;

import org.eclipse.jgit.junit.Assert;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefRename;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.ReflogEntry;
import org.eclipse.jgit.lib.ReflogReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.test.resources.SampleDataRepositoryTestCase;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.eclipse.jgit.lib.RefUpdate.Result.FAST_FORWARD;
import static org.eclipse.jgit.lib.RefUpdate.Result.FORCED;
import static org.eclipse.jgit.lib.RefUpdate.Result.IO_FAILURE;
import static org.eclipse.jgit.lib.RefUpdate.Result.LOCK_FAILURE;
import static org.eclipse.jgit.lib.RefUpdate.Result.RENAMED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class FileReftableTest extends SampleDataRepositoryTestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();
		db.convertToReftable();
	}

	@Test
	public void testConvert() throws Exception {
		Ref h = db.exactRef("HEAD");
		assertTrue(h.isSymbolic());
		assertEquals("refs/heads/master"

		Ref b = db.exactRef("refs/heads/b");
		assertFalse(b.isSymbolic());
		assertTrue(b.isPeeled());
		assertEquals("7f822839a2fe9760f386cbbbcb3f92c5fe81def7"
				b.getObjectId().name());
	}

	@Test
	public void testFastforwardStatus() throws Exception {
		ObjectId cur = db.resolve("master");
		ObjectId prev = db.resolve("master^");
		RefUpdate u = db.updateRef("refs/heads/master");

		u.setNewObjectId(prev);
		u.setForceUpdate(true);
		assertEquals(FORCED

		RefUpdate u2 = db.updateRef("refs/heads/master");

		u2.setNewObjectId(cur);
		assertEquals(FAST_FORWARD
	}

	@Test
	public void testOldValue() throws Exception {
		ObjectId cur = db.resolve("master");
		ObjectId prev = db.resolve("master^");
		RefUpdate u1 = db.updateRef("refs/heads/master");
		RefUpdate u2 = db.updateRef("refs/heads/master");

		u1.setExpectedOldObjectId(cur);
		u1.setNewObjectId(prev);
		u1.setForceUpdate(true);

		u2.setExpectedOldObjectId(cur);
		u2.setNewObjectId(prev);
		u2.setForceUpdate(true);

		assertEquals(FORCED
		assertEquals(LOCK_FAILURE
	}

	@Test
	public void testWritesymref() throws Exception {
		writeSymref(Constants.HEAD
		assertNotNull(db.exactRef("refs/heads/b"));
	}

	@Test
	public void testFastforwardStatus2() throws Exception {
		writeSymref(Constants.HEAD
		ObjectId bId = db.exactRef("refs/heads/b").getObjectId();
		RefUpdate u = db.updateRef("refs/heads/a");
		u.setNewObjectId(bId);
		u.setRefLogMessage("Setup"
		assertEquals(FAST_FORWARD
	}

	@Test
	public void symRef() throws Exception {
		Ref r = db.exactRef("refs/heads/a");
		ObjectId want = ObjectId
				.fromString("6db9c2ebf75590eef973081736730a9ea169a0c4");
		assertEquals(want
	}

	@Test
	public void testDelete() throws Exception {
		RefUpdate up = db.getRefDatabase().newUpdate("refs/heads/a"
		up.setForceUpdate(true);
		RefUpdate.Result res = up.delete();
		assertEquals(res
		assertEquals(null
	}

	@Test
	public void testDeleteWithoutHead() throws IOException {
		RefUpdate refUpdate = db.updateRef(Constants.HEAD
		refUpdate.setForceUpdate(true);
		refUpdate.setNewObjectId(ObjectId.zeroId());

		RefUpdate.Result updateResult = refUpdate.update();
		assertEquals(FORCED

		Ref r = db.exactRef("HEAD");
		assertEquals(ObjectId.zeroId()
		RefUpdate.Result deleteHeadResult = db.updateRef(Constants.HEAD)
				.delete();

		assertEquals(RefUpdate.Result.NO_CHANGE

		db.updateRef(Constants.R_HEADS + "master").delete();
	}

	@Test
	public void testUpdateRefDetached() throws Exception {
		ObjectId pid = db.resolve("refs/heads/master");
		ObjectId ppid = db.resolve("refs/heads/master^");
		RefUpdate updateRef = db.updateRef("HEAD"
		updateRef.setForceUpdate(true);
		updateRef.setNewObjectId(ppid);
		RefUpdate.Result update = updateRef.update();
		assertEquals(FORCED
		assertEquals(ppid
		Ref ref = db.exactRef("HEAD");
		assertEquals("HEAD"
		assertTrue("is detached"

		assertEquals(pid
		ReflogReader reflogReader = db.getReflogReader("HEAD");
		ReflogEntry e = reflogReader.getReverseEntries().get(0);
		assertEquals(ppid
		assertEquals("GIT_COMMITTER_EMAIL"
		assertEquals("GIT_COMMITTER_NAME"
		assertEquals(1250379778000L
		assertEquals(pid
	}

	@Test
	public void testWriteReflog() throws Exception {
		ObjectId pid = db.resolve("refs/heads/master^");
		RefUpdate updateRef = db.updateRef("refs/heads/master");
		updateRef.setNewObjectId(pid);
		String msg = "REFLOG!";
		updateRef.setRefLogMessage(msg
		PersonIdent person = new PersonIdent("name"
		updateRef.setRefLogIdent(person);
		updateRef.setForceUpdate(true);
		RefUpdate.Result update = updateRef.update();
		assertEquals(FORCED
		ReflogReader r = db.getReflogReader("refs/heads/master");

		ReflogEntry e = r.getLastEntry();
		assertEquals(e.getNewId()
		assertEquals(e.getComment()
		assertEquals(e.getWho()
	}

	@Test
	public void testLooseDelete() throws IOException {
		final String newRef = "refs/heads/abc";
		assertNull(db.exactRef(newRef));

		RefUpdate ref = db.updateRef(newRef);
		ObjectId nonZero = db.resolve(Constants.HEAD);
		assertNotEquals(nonZero
		ref.setNewObjectId(nonZero);
		assertEquals(RefUpdate.Result.NEW

		ref = db.updateRef(newRef);
		ref.setNewObjectId(db.resolve(Constants.HEAD));

		assertEquals(ref.delete()

		ReflogReader reader = db.getReflogReader("refs/heads/abc");
		assertEquals(ObjectId.zeroId()
		assertEquals(nonZero
		assertEquals(nonZero
		assertEquals(ObjectId.zeroId()
	}

	private static class SubclassedId extends ObjectId {
		SubclassedId(AnyObjectId src) {
			super(src);
		}
	}

	@Test
	public void testNoCacheObjectIdSubclass() throws IOException {
		final String newRef = "refs/heads/abc";
		final RefUpdate ru = updateRef(newRef);
		final SubclassedId newid = new SubclassedId(ru.getNewObjectId());
		ru.setNewObjectId(newid);
		RefUpdate.Result update = ru.update();
		assertEquals(RefUpdate.Result.NEW
		final Ref r = getRef(db
		assertEquals(newRef
		assertNotNull(r.getObjectId());
		assertNotSame(newid
		assertSame(ObjectId.class
		assertEquals(newid
		List<ReflogEntry> reverseEntries1 = db.getReflogReader("refs/heads/abc")
				.getReverseEntries();
		ReflogEntry entry1 = reverseEntries1.get(0);
		assertEquals(1
		assertEquals(ObjectId.zeroId()
		assertEquals(r.getObjectId()

		assertEquals(new PersonIdent(db).toString()
				entry1.getWho().toString());
		assertEquals(""
		List<ReflogEntry> reverseEntries2 = db.getReflogReader("HEAD")
				.getReverseEntries();
		assertEquals(0
	}

	@Test
	public void testDeleteSymref() throws IOException {
		RefUpdate dst = updateRef("refs/heads/abc");
		assertEquals(RefUpdate.Result.NEW
		ObjectId id = dst.getNewObjectId();

		RefUpdate u = db.updateRef("refs/symref");

		assertEquals(RefUpdate.Result.NEW

		Ref ref = db.exactRef(u.getName());
		assertNotNull(ref);
		assertTrue(ref.isSymbolic());
		assertEquals(dst.getName()
		assertEquals(id

		u = db.updateRef(u.getName());
		u.setDetachingSymbolicRef();
		u.setForceUpdate(true);
		assertEquals(FORCED

		assertNull(db.exactRef(u.getName()));
		ref = db.exactRef(dst.getName());
		assertNotNull(ref);
		assertFalse(ref.isSymbolic());
		assertEquals(id
	}

	@Test
	public void writeUnbornHead() throws Exception {
		RefUpdate.Result r = db.updateRef("HEAD").link("refs/heads/unborn");
		assertEquals(FORCED

		Ref head = db.exactRef("HEAD");
		assertTrue(head.isSymbolic());
		assertEquals(head.getTarget().getName()
	}

	@Test
	public void testUpdateRefDetachedUnbornHead() throws Exception {
		ObjectId ppid = db.resolve("refs/heads/master^");
		writeSymref("HEAD"
		RefUpdate updateRef = db.updateRef("HEAD"
		updateRef.setForceUpdate(true);
		updateRef.setNewObjectId(ppid);
		RefUpdate.Result update = updateRef.update();
		assertEquals(RefUpdate.Result.NEW
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
	public void testDeleteNotFound() throws IOException {
		RefUpdate ref = updateRef("refs/heads/doesnotexist");
		delete(ref
	}

	@Test
	public void testRefsCacheAfterUpdate() throws Exception {
	}

	@Test
	public void testUpdateRefLockFailureLocked() throws IOException {
		ObjectId opid = db.resolve("refs/heads/master");
		ObjectId pid = db.resolve("refs/heads/master^");
		RefUpdate updateRef = db.updateRef("refs/heads/master");
		updateRef.setNewObjectId(pid);
		LockFile lockFile1 = new LockFile(
				new File(db.getDirectory()
	}

	@Test
	public void testRenameSymref() throws IOException {
		ObjectId id = db.resolve("HEAD");
		RefRename r = db.renameRef("HEAD"
		assertEquals(IO_FAILURE
	}

	@Test
	public void testRenameCurrentBranch() throws IOException {
		ObjectId rb = db.resolve("refs/heads/b");
		writeSymref(Constants.HEAD
		ObjectId oldHead = db.resolve(Constants.HEAD);
		assertEquals("internal test condition
		RefRename renameRef = db.renameRef("refs/heads/b"
				"refs/heads/new/name");
		RefUpdate.Result result = renameRef.rename();
		assertEquals(RefUpdate.Result.RENAMED
		assertEquals(rb
		assertNull(db.resolve("refs/heads/b"));
		assertEquals(rb

		List<String> names = new ArrayList<>();
		names.add("HEAD");
		names.add("refs/heads/b");
		names.add("refs/heads/new/name");

		for (String nm : names) {
			ReflogReader rd = db.getReflogReader(nm);
			assertNotNull(rd);
			ReflogEntry last = rd.getLastEntry();
			ObjectId id = last.getNewId();
			assertTrue(ObjectId.zeroId().equals(id) || rb.equals(id));

			id = last.getNewId();
			assertTrue(ObjectId.zeroId().equals(id) || rb.equals(id));

			String want = "Branch: renamed b to new/name";
			assertEquals(want
		}
	}

	@Test
	public void testRenameDestExists() throws IOException {
		ObjectId rb = db.resolve("refs/heads/b");
		writeSymref(Constants.HEAD
		ObjectId oldHead = db.resolve(Constants.HEAD);
		assertEquals("internal test condition
		RefRename renameRef = db.renameRef("refs/heads/b"
		RefUpdate.Result result = renameRef.rename();
		assertEquals(RefUpdate.Result.LOCK_FAILURE
	}

	@Test
	public void testRenameAtomic() throws IOException {
		ObjectId prevId = db.resolve("refs/heads/master^");

		RefRename rename = db.renameRef("refs/heads/master"
				"refs/heads/newmaster");

		RefUpdate updateRef = db.updateRef("refs/heads/master");
		updateRef.setNewObjectId(prevId);
		updateRef.setForceUpdate(true);
		assertEquals(FORCED
		assertEquals(RefUpdate.Result.LOCK_FAILURE
	}

	@Test
	public void reftableRefsStorageClass() throws IOException {
		Ref b = db.exactRef("refs/heads/b");
		assertEquals(Ref.Storage.PACKED
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

	private void delete(final RefUpdate ref
			final boolean exists
		delete(db
	}

	private void delete(Repository repo
			final RefUpdate.Result expected
			final boolean removed) throws IOException {
		Assert.assertEquals(exists
		assertEquals(expected
		Assert.assertEquals(!removed
	}

	private Optional<Ref> getRef(Repository repo
			throws IOException {
		return getRef(repo.getRefDatabase().getRefs()
	}

	private Optional<Ref> getRef(List<Ref> refs
		return refs.stream().filter(r -> r.getName().equals(name)).findAny();
	}
}
