
package org.eclipse.jgit.storage.file;

import java.util.Arrays;

import org.eclipse.jgit.errors.LargeObjectException;
import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.junit.TestRng;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectStream;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.util.IO;

public class PackFileTest extends LocalDiskRepositoryTestCase {
	private TestRng rng;

	private FileRepository repo;

	private TestRepository<FileRepository> tr;

	private WindowCursor wc;

	protected void setUp() throws Exception {
		super.setUp();
		rng = new TestRng(getName());
		repo = createBareRepository();
		tr = new TestRepository<FileRepository>(repo);
		wc = (WindowCursor) repo.newObjectReader();
	}

	protected void tearDown() throws Exception {
		if (wc != null)
			wc.release();
		super.tearDown();
	}

	public void testWhole_SmallObject() throws Exception {
		final int type = Constants.OBJ_BLOB;
		byte[] data = rng.nextBytes(300);
		RevBlob id = tr.blob(data);
		tr.branch("master").commit().add("A"
		tr.packAndPrune();
		assertTrue("has blob"

		ObjectLoader ol = wc.open(id);
		assertNotNull("created loader"
		assertEquals(type
		assertEquals(data.length
		assertFalse("is not large"
		assertTrue("same content"

		ObjectStream in = ol.openStream();
		assertNotNull("have stream"
		assertEquals(type
		assertEquals(data.length
		byte[] data2 = new byte[data.length];
		IO.readFully(in
		assertTrue("same content"
		assertEquals("stream at EOF"
		in.close();
	}

	public void testWhole_LargeObject() throws Exception {
		final int type = Constants.OBJ_BLOB;
		byte[] data = rng.nextBytes(UnpackedObject.LARGE_OBJECT + 5);
		RevBlob id = tr.blob(data);
		tr.branch("master").commit().add("A"
		tr.packAndPrune();
		assertTrue("has blob"

		ObjectLoader ol = wc.open(id);
		assertNotNull("created loader"
		assertEquals(type
		assertEquals(data.length
		assertTrue("is large"
		try {
			ol.getCachedBytes();
			fail("Should have thrown LargeObjectException");
		} catch (LargeObjectException tooBig) {
			assertEquals(id.name()
		}

		ObjectStream in = ol.openStream();
		assertNotNull("have stream"
		assertEquals(type
		assertEquals(data.length
		byte[] data2 = new byte[data.length];
		IO.readFully(in
		assertTrue("same content"
		assertEquals("stream at EOF"
		in.close();
	}
}
