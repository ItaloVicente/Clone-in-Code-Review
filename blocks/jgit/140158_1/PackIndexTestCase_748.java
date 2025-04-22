
package org.eclipse.jgit.internal.storage.file;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.zip.Deflater;

import org.eclipse.jgit.errors.LargeObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.pack.DeltaEncoder;
import org.eclipse.jgit.internal.storage.pack.PackExt;
import org.eclipse.jgit.junit.JGitTestUtil;
import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.junit.TestRng;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectStream;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.storage.file.WindowCacheConfig;
import org.eclipse.jgit.transport.PackParser;
import org.eclipse.jgit.transport.PackedObjectInfo;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.NB;
import org.eclipse.jgit.util.TemporaryBuffer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PackFileTest extends LocalDiskRepositoryTestCase {
	private int streamThreshold = 16 * 1024;

	private TestRng rng;

	private FileRepository repo;

	private TestRepository<Repository> tr;

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
		tr = new TestRepository<>(repo);
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
	public void testWhole_SmallObject() throws Exception {
		final int type = Constants.OBJ_BLOB;
		byte[] data = getRng().nextBytes(300);
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
	public void testWhole_LargeObject() throws Exception {
		final int type = Constants.OBJ_BLOB;
		byte[] data = getRng().nextBytes(streamThreshold + 5);
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
	public void testDelta_SmallObjectChain() throws Exception {
		try (ObjectInserter.Formatter fmt = new ObjectInserter.Formatter()) {
			byte[] data0 = new byte[512];
			Arrays.fill(data0
			ObjectId id0 = fmt.idFor(Constants.OBJ_BLOB

			TemporaryBuffer.Heap pack = new TemporaryBuffer.Heap(64 * 1024);
			packHeader(pack
			objectHeader(pack
			deflate(pack

			byte[] data1 = clone(0x01
			byte[] delta1 = delta(data0
			ObjectId id1 = fmt.idFor(Constants.OBJ_BLOB
			objectHeader(pack
			id0.copyRawTo(pack);
			deflate(pack

			byte[] data2 = clone(0x02
			byte[] delta2 = delta(data1
			ObjectId id2 = fmt.idFor(Constants.OBJ_BLOB
			objectHeader(pack
			id1.copyRawTo(pack);
			deflate(pack

			byte[] data3 = clone(0x03
			byte[] delta3 = delta(data2
			ObjectId id3 = fmt.idFor(Constants.OBJ_BLOB
			objectHeader(pack
			id2.copyRawTo(pack);
			deflate(pack

			digest(pack);
			PackParser ip = index(pack.toByteArray());
			ip.setAllowThin(true);
			ip.parse(NullProgressMonitor.INSTANCE);

			assertTrue("has blob"

			ObjectLoader ol = wc.open(id3);
			assertNotNull("created loader"
			assertEquals(Constants.OBJ_BLOB
			assertEquals(data3.length
			assertFalse("is large"
			assertNotNull(ol.getCachedBytes());
			assertArrayEquals(data3

			try (ObjectStream in = ol.openStream()) {
				assertNotNull("have stream"
				assertEquals(Constants.OBJ_BLOB
				assertEquals(data3.length
				byte[] act = new byte[data3.length];
				IO.readFully(in
				assertTrue("same content"
				assertEquals("stream at EOF"
			}
		}
	}

	@Test
	public void testDelta_FailsOver2GiB() throws Exception {
		try (ObjectInserter.Formatter fmt = new ObjectInserter.Formatter()) {
			byte[] base = new byte[] { 'a' };
			ObjectId idA = fmt.idFor(Constants.OBJ_BLOB
			ObjectId idB = fmt.idFor(Constants.OBJ_BLOB

			PackedObjectInfo a = new PackedObjectInfo(idA);
			PackedObjectInfo b = new PackedObjectInfo(idB);

			TemporaryBuffer.Heap pack = new TemporaryBuffer.Heap(64 * 1024);
			packHeader(pack
			a.setOffset(pack.length());
			objectHeader(pack
			deflate(pack

			ByteArrayOutputStream tmp = new ByteArrayOutputStream();
			DeltaEncoder de = new DeltaEncoder(tmp
			de.copy(0
			byte[] delta = tmp.toByteArray();
			b.setOffset(pack.length());
			objectHeader(pack
			idA.copyRawTo(pack);
			deflate(pack
			byte[] footer = digest(pack);

			File dir = new File(repo.getObjectDatabase().getDirectory()
					"pack");
			File packName = new File(dir
			File idxName = new File(dir

			try (FileOutputStream f = new FileOutputStream(packName)) {
				f.write(pack.toByteArray());
			}

			try (FileOutputStream f = new FileOutputStream(idxName)) {
				List<PackedObjectInfo> list = new ArrayList<>();
				list.add(a);
				list.add(b);
				Collections.sort(list);
				new PackIndexWriterV1(f).write(list
			}

			PackFile packFile = new PackFile(packName
			try {
				packFile.get(wc
				fail("expected LargeObjectException.ExceedsByteArrayLimit");
			} catch (LargeObjectException.ExceedsByteArrayLimit bad) {
				assertNull(bad.getObjectId());
			} finally {
				packFile.close();
			}
		}
	}

	@Test
	public void testConfigurableStreamFileThreshold() throws Exception {
		byte[] data = getRng().nextBytes(300);
		RevBlob id = tr.blob(data);
		tr.branch("master").commit().add("A"
		tr.packAndPrune();
		assertTrue("has blob"

		ObjectLoader ol = wc.open(id);
		try (ObjectStream in = ol.openStream()) {
			assertTrue(in instanceof ObjectStream.SmallStream);
			assertEquals(300
		}

		wc.setStreamFileThreshold(299);
		ol = wc.open(id);
		try (ObjectStream in = ol.openStream()) {
			assertTrue(in instanceof ObjectStream.Filter);
			assertEquals(1
		}
	}

	private static byte[] clone(int first
		byte[] r = new byte[base.length];
		System.arraycopy(base
		r[0] = (byte) first;
		return r;
	}

	private static byte[] delta(byte[] base
		ByteArrayOutputStream tmp = new ByteArrayOutputStream();
		DeltaEncoder de = new DeltaEncoder(tmp
		de.insert(dest
		de.copy(1
		return tmp.toByteArray();
	}

	private static void packHeader(TemporaryBuffer.Heap pack
			throws IOException {
		final byte[] hdr = new byte[8];
		NB.encodeInt32(hdr
		NB.encodeInt32(hdr
		pack.write(Constants.PACK_SIGNATURE);
		pack.write(hdr
	}

	private static void objectHeader(TemporaryBuffer.Heap pack
			throws IOException {
		byte[] buf = new byte[8];
		int nextLength = sz >>> 4;
		buf[0] = (byte) ((nextLength > 0 ? 0x80 : 0x00) | (type << 4) | (sz & 0x0F));
		sz = nextLength;
		int n = 1;
		while (sz > 0) {
			nextLength >>>= 7;
			buf[n++] = (byte) ((nextLength > 0 ? 0x80 : 0x00) | (sz & 0x7F));
			sz = nextLength;
		}
		pack.write(buf
	}

	private static void deflate(TemporaryBuffer.Heap pack
			throws IOException {
		final Deflater deflater = new Deflater();
		final byte[] buf = new byte[128];
		deflater.setInput(content
		deflater.finish();
		do {
			final int n = deflater.deflate(buf
			if (n > 0)
				pack.write(buf
		} while (!deflater.finished());
		deflater.end();
	}

	private static byte[] digest(TemporaryBuffer.Heap buf)
			throws IOException {
		MessageDigest md = Constants.newMessageDigest();
		md.update(buf.toByteArray());
		byte[] footer = md.digest();
		buf.write(footer);
		return footer;
	}

	private ObjectInserter inserter;

	@After
	public void release() {
		if (inserter != null) {
			inserter.close();
		}
	}

	private PackParser index(byte[] raw) throws IOException {
		if (inserter == null)
			inserter = repo.newObjectInserter();
		return inserter.newPackParser(new ByteArrayInputStream(raw));
	}
}
