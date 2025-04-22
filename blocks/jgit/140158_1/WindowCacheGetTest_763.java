
package org.eclipse.jgit.internal.storage.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.zip.DeflaterOutputStream;

import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.LargeObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.junit.JGitTestUtil;
import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;
import org.eclipse.jgit.junit.TestRng;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectStream;
import org.eclipse.jgit.storage.file.WindowCacheConfig;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.IO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UnpackedObjectTest extends LocalDiskRepositoryTestCase {
	private int streamThreshold = 16 * 1024;

	private TestRng rng;

	private FileRepository repo;

	private WindowCursor wc;

	private TestRng getRng() {
		if (rng == null)
			rng = new TestRng(JGitTestUtil.getName());
		return rng;
	}

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();

		WindowCacheConfig cfg = new WindowCacheConfig();
		cfg.setStreamFileThreshold(streamThreshold);
		cfg.install();

		repo = createBareRepository();
		wc = (WindowCursor) repo.newObjectReader();
	}

	@Override
	@After
	public void tearDown() throws Exception {
		if (wc != null)
			wc.close();
		new WindowCacheConfig().install();
		super.tearDown();
	}

	@Test
	public void testStandardFormat_SmallObject() throws Exception {
		final int type = Constants.OBJ_BLOB;
		byte[] data = getRng().nextBytes(300);
		byte[] gz = compressStandardFormat(type
		ObjectId id = ObjectId.zeroId();

		ObjectLoader ol = UnpackedObject.open(new ByteArrayInputStream(gz)
				path(id)
		assertNotNull("created loader"
		assertEquals(type
		assertEquals(data.length
		assertFalse("is not large"
		assertTrue("same content"

		try (ObjectStream in = ol.openStream()) {
			assertNotNull("have stream"
			assertEquals(type
			assertEquals(data.length
			byte[] data2 = new byte[data.length];
			IO.readFully(in
			assertTrue("same content"
			assertEquals("stream at EOF"
		}
	}

	@Test
	public void testStandardFormat_LargeObject() throws Exception {
		final int type = Constants.OBJ_BLOB;
		byte[] data = getRng().nextBytes(streamThreshold + 5);
		ObjectId id = getId(type
		write(id

		ObjectLoader ol;
		{
			try (FileInputStream fs = new FileInputStream(path(id))) {
				ol = UnpackedObject.open(fs
			}
		}

		assertNotNull("created loader"
		assertEquals(type
		assertEquals(data.length
		assertTrue("is large"
		try {
			ol.getCachedBytes();
			fail("Should have thrown LargeObjectException");
		} catch (LargeObjectException tooBig) {
			assertEquals(MessageFormat.format(
					JGitText.get().largeObjectException
					.getMessage());
		}

		try (ObjectStream in = ol.openStream()) {
			assertNotNull("have stream"
			assertEquals(type
			assertEquals(data.length
			byte[] data2 = new byte[data.length];
			IO.readFully(in
			assertTrue("same content"
			assertEquals("stream at EOF"
		}
	}

	@Test
	public void testStandardFormat_NegativeSize() throws Exception {
		ObjectId id = ObjectId.zeroId();
		byte[] data = getRng().nextBytes(300);

		try {
			byte[] gz = compressStandardFormat("blob"
			UnpackedObject.open(new ByteArrayInputStream(gz)
			fail("Did not throw CorruptObjectException");
		} catch (CorruptObjectException coe) {
			assertEquals(MessageFormat.format(JGitText.get().objectIsCorrupt
					id.name()
					.getMessage());
		}
	}

	@Test
	public void testStandardFormat_InvalidType() throws Exception {
		ObjectId id = ObjectId.zeroId();
		byte[] data = getRng().nextBytes(300);

		try {
			byte[] gz = compressStandardFormat("not.a.type"
			UnpackedObject.open(new ByteArrayInputStream(gz)
			fail("Did not throw CorruptObjectException");
		} catch (CorruptObjectException coe) {
			assertEquals(MessageFormat.format(JGitText.get().objectIsCorrupt
					id.name()
					.getMessage());
		}
	}

	@Test
	public void testStandardFormat_NoHeader() throws Exception {
		ObjectId id = ObjectId.zeroId();
		byte[] data = {};

		try {
			byte[] gz = compressStandardFormat(""
			UnpackedObject.open(new ByteArrayInputStream(gz)
			fail("Did not throw CorruptObjectException");
		} catch (CorruptObjectException coe) {
			assertEquals(MessageFormat.format(JGitText.get().objectIsCorrupt
					id.name()
					.getMessage());
		}
	}

	@Test
	public void testStandardFormat_GarbageAfterSize() throws Exception {
		ObjectId id = ObjectId.zeroId();
		byte[] data = getRng().nextBytes(300);

		try {
			byte[] gz = compressStandardFormat("blob"
			UnpackedObject.open(new ByteArrayInputStream(gz)
			fail("Did not throw CorruptObjectException");
		} catch (CorruptObjectException coe) {
			assertEquals(MessageFormat.format(JGitText.get().objectIsCorrupt
					id.name()
					coe.getMessage());
		}
	}

	@Test
	public void testStandardFormat_SmallObject_CorruptZLibStream()
			throws Exception {
		ObjectId id = ObjectId.zeroId();
		byte[] data = getRng().nextBytes(300);

		try {
			byte[] gz = compressStandardFormat(Constants.OBJ_BLOB
			for (int i = 5; i < gz.length; i++)
				gz[i] = 0;
			UnpackedObject.open(new ByteArrayInputStream(gz)
			fail("Did not throw CorruptObjectException");
		} catch (CorruptObjectException coe) {
			assertEquals(MessageFormat.format(JGitText.get().objectIsCorrupt
					id.name()
					.getMessage());
		}
	}

	@Test
	public void testStandardFormat_SmallObject_TruncatedZLibStream()
			throws Exception {
		ObjectId id = ObjectId.zeroId();
		byte[] data = getRng().nextBytes(300);

		try {
			byte[] gz = compressStandardFormat(Constants.OBJ_BLOB
			byte[] tr = new byte[gz.length - 1];
			System.arraycopy(gz
			UnpackedObject.open(new ByteArrayInputStream(tr)
			fail("Did not throw CorruptObjectException");
		} catch (CorruptObjectException coe) {
			assertEquals(MessageFormat.format(JGitText.get().objectIsCorrupt
					id.name()
					.getMessage());
		}
	}

	@Test
	public void testStandardFormat_SmallObject_TrailingGarbage()
			throws Exception {
		ObjectId id = ObjectId.zeroId();
		byte[] data = getRng().nextBytes(300);

		try {
			byte[] gz = compressStandardFormat(Constants.OBJ_BLOB
			byte[] tr = new byte[gz.length + 1];
			System.arraycopy(gz
			UnpackedObject.open(new ByteArrayInputStream(tr)
			fail("Did not throw CorruptObjectException");
		} catch (CorruptObjectException coe) {
			assertEquals(MessageFormat.format(JGitText.get().objectIsCorrupt
					id.name()
					.getMessage());
		}
	}

	@Test
	public void testStandardFormat_LargeObject_CorruptZLibStream()
			throws Exception {
		final int type = Constants.OBJ_BLOB;
		byte[] data = getRng().nextBytes(streamThreshold + 5);
		ObjectId id = getId(type
		byte[] gz = compressStandardFormat(type
		gz[gz.length - 1] = 0;
		gz[gz.length - 2] = 0;

		write(id

		ObjectLoader ol;
		try (FileInputStream fs = new FileInputStream(path(id))) {
			ol = UnpackedObject.open(fs
		}

		byte[] tmp = new byte[data.length];
		try (InputStream in = ol.openStream()) {
			IO.readFully(in
			fail("Did not throw CorruptObjectException");
		} catch (CorruptObjectException coe) {
			assertEquals(MessageFormat.format(JGitText.get().objectIsCorrupt
					id.name()
					.getMessage());
		}
	}

	@Test
	public void testStandardFormat_LargeObject_TruncatedZLibStream()
			throws Exception {
		final int type = Constants.OBJ_BLOB;
		byte[] data = getRng().nextBytes(streamThreshold + 5);
		ObjectId id = getId(type
		byte[] gz = compressStandardFormat(type
		byte[] tr = new byte[gz.length - 1];
		System.arraycopy(gz

		write(id

		ObjectLoader ol;
		try (FileInputStream fs = new FileInputStream(path(id))) {
			ol = UnpackedObject.open(fs
		}

		byte[] tmp = new byte[data.length];
		InputStream in = ol.openStream();
		IO.readFully(in
		try {
			in.close();
			fail("close did not throw CorruptObjectException");
		} catch (CorruptObjectException coe) {
			assertEquals(MessageFormat.format(JGitText.get().objectIsCorrupt
					id.name()
					.getMessage());
		}
	}

	@Test
	public void testStandardFormat_LargeObject_TrailingGarbage()
			throws Exception {
		final int type = Constants.OBJ_BLOB;
		byte[] data = getRng().nextBytes(streamThreshold + 5);
		ObjectId id = getId(type
		byte[] gz = compressStandardFormat(type
		byte[] tr = new byte[gz.length + 1];
		System.arraycopy(gz

		write(id

		ObjectLoader ol;
		try (FileInputStream fs = new FileInputStream(path(id))) {
			ol = UnpackedObject.open(fs
		}

		byte[] tmp = new byte[data.length];
		InputStream in = ol.openStream();
		IO.readFully(in
		try {
			in.close();
			fail("close did not throw CorruptObjectException");
		} catch (CorruptObjectException coe) {
			assertEquals(MessageFormat.format(JGitText.get().objectIsCorrupt
					id.name()
					.getMessage());
		}
	}

	@Test
	public void testPackFormat_SmallObject() throws Exception {
		final int type = Constants.OBJ_BLOB;
		byte[] data = getRng().nextBytes(300);
		byte[] gz = compressPackFormat(type
		ObjectId id = ObjectId.zeroId();

		ObjectLoader ol = UnpackedObject.open(new ByteArrayInputStream(gz)
				path(id)
		assertNotNull("created loader"
		assertEquals(type
		assertEquals(data.length
		assertFalse("is not large"
		assertTrue("same content"

		try (ObjectStream in = ol.openStream()) {
			assertNotNull("have stream"
			assertEquals(type
			assertEquals(data.length
			byte[] data2 = new byte[data.length];
			IO.readFully(in
			assertTrue("same content"
					Arrays.equals(data
		}
	}

	@Test
	public void testPackFormat_LargeObject() throws Exception {
		final int type = Constants.OBJ_BLOB;
		byte[] data = getRng().nextBytes(streamThreshold + 5);
		ObjectId id = getId(type
		write(id

		ObjectLoader ol;
		try (FileInputStream fs = new FileInputStream(path(id))) {
			ol = UnpackedObject.open(fs
		}

		assertNotNull("created loader"
		assertEquals(type
		assertEquals(data.length
		assertTrue("is large"
		try {
			ol.getCachedBytes();
			fail("Should have thrown LargeObjectException");
		} catch (LargeObjectException tooBig) {
			assertEquals(MessageFormat.format(
					JGitText.get().largeObjectException
					.getMessage());
		}

		try (ObjectStream in = ol.openStream()) {
			assertNotNull("have stream"
			assertEquals(type
			assertEquals(data.length
			byte[] data2 = new byte[data.length];
			IO.readFully(in
			assertTrue("same content"
			assertEquals("stream at EOF"
		}
	}

	@Test
	public void testPackFormat_DeltaNotAllowed() throws Exception {
		ObjectId id = ObjectId.zeroId();
		byte[] data = getRng().nextBytes(300);

		try {
			byte[] gz = compressPackFormat(Constants.OBJ_OFS_DELTA
			UnpackedObject.open(new ByteArrayInputStream(gz)
			fail("Did not throw CorruptObjectException");
		} catch (CorruptObjectException coe) {
			assertEquals(MessageFormat.format(JGitText.get().objectIsCorrupt
					id.name()
					.getMessage());
		}

		try {
			byte[] gz = compressPackFormat(Constants.OBJ_REF_DELTA
			UnpackedObject.open(new ByteArrayInputStream(gz)
			fail("Did not throw CorruptObjectException");
		} catch (CorruptObjectException coe) {
			assertEquals(MessageFormat.format(JGitText.get().objectIsCorrupt
					id.name()
					.getMessage());
		}

		try {
			byte[] gz = compressPackFormat(Constants.OBJ_TYPE_5
			UnpackedObject.open(new ByteArrayInputStream(gz)
			fail("Did not throw CorruptObjectException");
		} catch (CorruptObjectException coe) {
			assertEquals(MessageFormat.format(JGitText.get().objectIsCorrupt
					id.name()
					.getMessage());
		}

		try {
			byte[] gz = compressPackFormat(Constants.OBJ_EXT
			UnpackedObject.open(new ByteArrayInputStream(gz)
			fail("Did not throw CorruptObjectException");
		} catch (CorruptObjectException coe) {
			assertEquals(MessageFormat.format(JGitText.get().objectIsCorrupt
					id.name()
					.getMessage());
		}
	}

	private static byte[] compressStandardFormat(int type
			throws IOException {
		String typeString = Constants.typeString(type);
		String length = String.valueOf(data.length);
		return compressStandardFormat(typeString
	}

	private static byte[] compressStandardFormat(String type
			byte[] data) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DeflaterOutputStream d = new DeflaterOutputStream(out);
		d.write(Constants.encodeASCII(type));
		d.write(' ');
		d.write(Constants.encodeASCII(length));
		d.write(0);
		d.write(data);
		d.finish();
		return out.toByteArray();
	}

	private static byte[] compressPackFormat(int type
			throws IOException {
		byte[] hdr = new byte[64];
		int rawLength = data.length;
		int nextLength = rawLength >>> 4;
		hdr[0] = (byte) ((nextLength > 0 ? 0x80 : 0x00) | (type << 4) | (rawLength & 0x0F));
		rawLength = nextLength;
		int n = 1;
		while (rawLength > 0) {
			nextLength >>>= 7;
			hdr[n++] = (byte) ((nextLength > 0 ? 0x80 : 0x00) | (rawLength & 0x7F));
			rawLength = nextLength;
		}

		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		out.write(hdr

		DeflaterOutputStream d = new DeflaterOutputStream(out);
		d.write(data);
		d.finish();
		return out.toByteArray();
	}

	private File path(ObjectId id) {
		return repo.getObjectDatabase().fileFor(id);
	}

	private void write(ObjectId id
		File path = path(id);
		FileUtils.mkdirs(path.getParentFile());
		try (FileOutputStream out = new FileOutputStream(path)) {
			out.write(data);
		}
	}

	private ObjectId getId(int type
		try (ObjectInserter.Formatter formatter = new ObjectInserter.Formatter()) {
			return formatter.idFor(type
		}
	}
}
