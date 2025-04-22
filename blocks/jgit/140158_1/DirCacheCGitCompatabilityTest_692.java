
package org.eclipse.jgit.dircache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import org.eclipse.jgit.events.IndexChangedEvent;
import org.eclipse.jgit.events.IndexChangedListener;
import org.eclipse.jgit.events.ListenerList;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.junit.Test;

public class DirCacheBuilderTest extends RepositoryTestCase {
	@Test
	public void testBuildEmpty() throws Exception {
		{
			final DirCache dc = db.lockDirCache();
			final DirCacheBuilder b = dc.builder();
			assertNotNull(b);
			b.finish();
			dc.write();
			assertTrue(dc.commit());
		}
		{
			final DirCache dc = db.readDirCache();
			assertEquals(0
		}
	}

	@Test
	public void testBuildRejectsUnsetFileMode() throws Exception {
		final DirCache dc = DirCache.newInCore();
		final DirCacheBuilder b = dc.builder();
		assertNotNull(b);

		final DirCacheEntry e = new DirCacheEntry("a");
		assertEquals(0
		try {
			b.add(e);
			fail("did not reject unset file mode");
		} catch (IllegalArgumentException err) {
			assertEquals("FileMode not set for path a"
		}
	}

	@Test
	public void testBuildOneFile_FinishWriteCommit() throws Exception {
		final String path = "a-file-path";
		final FileMode mode = FileMode.REGULAR_FILE;
		final long lastModified = 1218123387057L;
		final int length = 1342;
		final DirCacheEntry entOrig;
		{
			final DirCache dc = db.lockDirCache();
			final DirCacheBuilder b = dc.builder();
			assertNotNull(b);

			entOrig = new DirCacheEntry(path);
			entOrig.setFileMode(mode);
			entOrig.setLastModified(lastModified);
			entOrig.setLength(length);

			assertNotSame(path
			assertEquals(path
			assertEquals(ObjectId.zeroId()
			assertEquals(mode.getBits()
			assertEquals(0
			assertEquals(lastModified
			assertEquals(length
			assertFalse(entOrig.isAssumeValid());
			b.add(entOrig);

			b.finish();
			assertEquals(1
			assertSame(entOrig

			dc.write();
			assertTrue(dc.commit());
		}
		{
			final DirCache dc = db.readDirCache();
			assertEquals(1

			final DirCacheEntry entRead = dc.getEntry(0);
			assertNotSame(entOrig
			assertEquals(path
			assertEquals(ObjectId.zeroId()
			assertEquals(mode.getBits()
			assertEquals(0
			assertEquals(lastModified
			assertEquals(length
			assertFalse(entOrig.isAssumeValid());
		}
	}

	@Test
	public void testBuildOneFile_Commit() throws Exception {
		final String path = "a-file-path";
		final FileMode mode = FileMode.REGULAR_FILE;
		final long lastModified = 1218123387057L;
		final int length = 1342;
		final DirCacheEntry entOrig;
		{
			final DirCache dc = db.lockDirCache();
			final DirCacheBuilder b = dc.builder();
			assertNotNull(b);

			entOrig = new DirCacheEntry(path);
			entOrig.setFileMode(mode);
			entOrig.setLastModified(lastModified);
			entOrig.setLength(length);

			assertNotSame(path
			assertEquals(path
			assertEquals(ObjectId.zeroId()
			assertEquals(mode.getBits()
			assertEquals(0
			assertEquals(lastModified
			assertEquals(length
			assertFalse(entOrig.isAssumeValid());
			b.add(entOrig);

			assertTrue(b.commit());
			assertEquals(1
			assertSame(entOrig
			assertFalse(new File(db.getDirectory()
		}
		{
			final DirCache dc = db.readDirCache();
			assertEquals(1

			final DirCacheEntry entRead = dc.getEntry(0);
			assertNotSame(entOrig
			assertEquals(path
			assertEquals(ObjectId.zeroId()
			assertEquals(mode.getBits()
			assertEquals(0
			assertEquals(lastModified
			assertEquals(length
			assertFalse(entOrig.isAssumeValid());
		}
	}

	@Test
	public void testBuildOneFile_Commit_IndexChangedEvent()
			throws Exception {
		final class ReceivedEventMarkerException extends RuntimeException {
			private static final long serialVersionUID = 1L;
		}

		final String path = "a-file-path";
		final FileMode mode = FileMode.REGULAR_FILE;
		final long lastModified = 1218123387057L;
		final int length = 1342;
		DirCacheEntry entOrig;
		boolean receivedEvent = false;

		DirCache dc = db.lockDirCache();
		IndexChangedListener listener = new IndexChangedListener() {

			@Override
			public void onIndexChanged(IndexChangedEvent event) {
				throw new ReceivedEventMarkerException();
			}
		};

		ListenerList l = db.getListenerList();
		l.addIndexChangedListener(listener);
		DirCacheBuilder b = dc.builder();

		entOrig = new DirCacheEntry(path);
		entOrig.setFileMode(mode);
		entOrig.setLastModified(lastModified);
		entOrig.setLength(length);
		b.add(entOrig);
		try {
			b.commit();
		} catch (ReceivedEventMarkerException e) {
			receivedEvent = true;
		}
		if (!receivedEvent)
			fail("did not receive IndexChangedEvent");

		dc = db.lockDirCache();
		listener = new IndexChangedListener() {

			@Override
			public void onIndexChanged(IndexChangedEvent event) {
				throw new ReceivedEventMarkerException();
			}
		};

		l = db.getListenerList();
		l.addIndexChangedListener(listener);
		b = dc.builder();

		entOrig = new DirCacheEntry(path);
		entOrig.setFileMode(mode);
		entOrig.setLastModified(lastModified);
		entOrig.setLength(length);
		b.add(entOrig);
		try {
			b.commit();
		} catch (ReceivedEventMarkerException e) {
			fail("unexpected IndexChangedEvent");
		}
	}

	@Test
	public void testFindSingleFile() throws Exception {
		final String path = "a-file-path";
		final DirCache dc = db.readDirCache();
		final DirCacheBuilder b = dc.builder();
		assertNotNull(b);

		final DirCacheEntry entOrig = new DirCacheEntry(path);
		entOrig.setFileMode(FileMode.REGULAR_FILE);
		assertNotSame(path
		assertEquals(path
		b.add(entOrig);
		b.finish();

		assertEquals(1
		assertSame(entOrig
		assertEquals(0

		assertEquals(-1
		assertEquals(0

		assertEquals(-2
		assertEquals(1

		assertSame(entOrig
	}

	@Test
	public void testAdd_InGitSortOrder() throws Exception {
		final DirCache dc = db.readDirCache();

		final String[] paths = { "a-"
		final DirCacheEntry[] ents = new DirCacheEntry[paths.length];
		for (int i = 0; i < paths.length; i++) {
			ents[i] = new DirCacheEntry(paths[i]);
			ents[i].setFileMode(FileMode.REGULAR_FILE);
		}

		final DirCacheBuilder b = dc.builder();
            for (DirCacheEntry ent : ents) {
                b.add(ent);
            }
		b.finish();

		assertEquals(paths.length
		for (int i = 0; i < paths.length; i++) {
			assertSame(ents[i]
			assertEquals(paths[i]
			assertEquals(i
			assertSame(ents[i]
		}
	}

	@Test
	public void testAdd_ReverseGitSortOrder() throws Exception {
		final DirCache dc = db.readDirCache();

		final String[] paths = { "a-"
		final DirCacheEntry[] ents = new DirCacheEntry[paths.length];
		for (int i = 0; i < paths.length; i++) {
			ents[i] = new DirCacheEntry(paths[i]);
			ents[i].setFileMode(FileMode.REGULAR_FILE);
		}

		final DirCacheBuilder b = dc.builder();
		for (int i = ents.length - 1; i >= 0; i--)
			b.add(ents[i]);
		b.finish();

		assertEquals(paths.length
		for (int i = 0; i < paths.length; i++) {
			assertSame(ents[i]
			assertEquals(paths[i]
			assertEquals(i
			assertSame(ents[i]
		}
	}

	@Test
	public void testBuilderClear() throws Exception {
		final DirCache dc = db.readDirCache();

		final String[] paths = { "a-"
		final DirCacheEntry[] ents = new DirCacheEntry[paths.length];
		for (int i = 0; i < paths.length; i++) {
			ents[i] = new DirCacheEntry(paths[i]);
			ents[i].setFileMode(FileMode.REGULAR_FILE);
		}
		{
			final DirCacheBuilder b = dc.builder();
                    for (DirCacheEntry ent : ents) {
                        b.add(ent);
                    }
			b.finish();
		}
		assertEquals(paths.length
		{
			final DirCacheBuilder b = dc.builder();
			b.finish();
		}
		assertEquals(0
	}

	private static int real(int eIdx) {
		if (eIdx < 0)
			eIdx = -(eIdx + 1);
		return eIdx;
	}
}
