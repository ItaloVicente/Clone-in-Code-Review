
package org.eclipse.jgit.internal.storage.pack;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.eclipse.jgit.junit.JGitTestUtil;
import org.eclipse.jgit.junit.TestRng;
import org.eclipse.jgit.lib.Constants;
import org.junit.Before;
import org.junit.Test;

public class DeltaIndexTest {
	private TestRng rng;

	private ByteArrayOutputStream actDeltaBuf;

	private ByteArrayOutputStream expDeltaBuf;

	private DeltaEncoder expDeltaEnc;

	private byte[] src;

	private byte[] dst;

	private ByteArrayOutputStream dstBuf;

	private TestRng getRng() {
		if (rng == null)
			rng = new TestRng(JGitTestUtil.getName());
		return rng;
	}

	@Before
	public void setUp() throws Exception {
		actDeltaBuf = new ByteArrayOutputStream();
		expDeltaBuf = new ByteArrayOutputStream();
		expDeltaEnc = new DeltaEncoder(expDeltaBuf
		dstBuf = new ByteArrayOutputStream();
	}

	@Test
	public void testInsertWholeObject_Length12() throws IOException {
		src = getRng().nextBytes(12);
		insert(src);
		doTest();
	}

	@Test
	public void testCopyWholeObject_Length128() throws IOException {
		src = getRng().nextBytes(128);
		copy(0
		doTest();
	}

	@Test
	public void testCopyWholeObject_Length123() throws IOException {
		src = getRng().nextBytes(123);
		copy(0
		doTest();
	}

	@Test
	public void testCopyZeros_Length128() throws IOException {
		src = new byte[2048];
		copy(0
		doTest();

		assertEquals(2636
	}

	@Test
	public void testShuffleSegments() throws IOException {
		src = getRng().nextBytes(128);
		copy(64
		copy(0
		doTest();
	}

	@Test
	public void testInsertHeadMiddle() throws IOException {
		src = getRng().nextBytes(1024);
		insert("foo");
		copy(0
		insert("yet more fooery");
		copy(0
		doTest();
	}

	@Test
	public void testInsertTail() throws IOException {
		src = getRng().nextBytes(1024);
		copy(0
		insert("bar");
		doTest();
	}

	@Test
	public void testIndexSize() {
		src = getRng().nextBytes(1024);
		DeltaIndex di = new DeltaIndex(src);
		assertEquals(1860
		assertEquals("DeltaIndex[2 KiB]"
	}

	@Test
	public void testLimitObjectSize_Length12InsertFails() throws IOException {
		src = getRng().nextBytes(12);
		dst = src;

		DeltaIndex di = new DeltaIndex(src);
		assertFalse(di.encode(actDeltaBuf
	}

	@Test
	public void testLimitObjectSize_Length130InsertFails() throws IOException {
		src = getRng().nextBytes(130);
		dst = getRng().nextBytes(130);

		DeltaIndex di = new DeltaIndex(src);
		assertFalse(di.encode(actDeltaBuf
	}

	@Test
	public void testLimitObjectSize_Length130CopyOk() throws IOException {
		src = getRng().nextBytes(130);
		copy(0
		dst = dstBuf.toByteArray();

		DeltaIndex di = new DeltaIndex(src);
		assertTrue(di.encode(actDeltaBuf

		byte[] actDelta = actDeltaBuf.toByteArray();
		byte[] expDelta = expDeltaBuf.toByteArray();

		assertEquals(BinaryDelta.format(expDelta
				BinaryDelta.format(actDelta
	}

	@Test
	public void testLimitObjectSize_Length130CopyFails() throws IOException {
		src = getRng().nextBytes(130);
		copy(0
		dst = dstBuf.toByteArray();

		DeltaIndex di = new DeltaIndex(src);
		assertFalse(di.encode(actDeltaBuf
		assertEquals(4
	}

	@Test
	public void testLimitObjectSize_InsertFrontFails() throws IOException {
		src = getRng().nextBytes(130);
		insert("eight");
		copy(0
		dst = dstBuf.toByteArray();

		DeltaIndex di = new DeltaIndex(src);
		assertFalse(di.encode(actDeltaBuf
		assertEquals(4
	}

	private void copy(int offset
		dstBuf.write(src
		expDeltaEnc.copy(offset
	}

	private void insert(String text) throws IOException {
		insert(Constants.encode(text));
	}

	private void insert(byte[] text) throws IOException {
		dstBuf.write(text);
		expDeltaEnc.insert(text);
	}

	private void doTest() throws IOException {
		dst = dstBuf.toByteArray();

		DeltaIndex di = new DeltaIndex(src);
		di.encode(actDeltaBuf

		byte[] actDelta = actDeltaBuf.toByteArray();
		byte[] expDelta = expDeltaBuf.toByteArray();

		assertEquals(BinaryDelta.format(expDelta
				BinaryDelta.format(actDelta

		assertTrue("delta is not empty"
		assertEquals(src.length
		assertEquals(dst.length
		assertArrayEquals(dst
	}
}
