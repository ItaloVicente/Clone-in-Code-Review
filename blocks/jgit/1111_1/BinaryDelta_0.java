
package org.eclipse.jgit.storage.pack;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import junit.framework.TestCase;

import org.eclipse.jgit.junit.TestRng;
import org.eclipse.jgit.lib.Constants;

public class DeltaIndexTest extends TestCase {
	private TestRng rng;

	private ByteArrayOutputStream actDeltaBuf;

	private ByteArrayOutputStream expDeltaBuf;

	private DeltaEncoder expDeltaEnc;

	private byte[] src;

	private byte[] dst;

	private ByteArrayOutputStream dstBuf;

	protected void setUp() throws Exception {
		super.setUp();
		rng = new TestRng(getName());
		actDeltaBuf = new ByteArrayOutputStream();
		expDeltaBuf = new ByteArrayOutputStream();
		expDeltaEnc = new DeltaEncoder(expDeltaBuf
		dstBuf = new ByteArrayOutputStream();
	}

	public void testInsertWholeObject_Length12() throws IOException {
		src = rng.nextBytes(12);
		insert(src);
		doTest();
	}

	public void testCopyWholeObject_Length128() throws IOException {
		src = rng.nextBytes(128);
		copy(0
		doTest();
	}

	public void testCopyWholeObject_Length123() throws IOException {
		src = rng.nextBytes(123);
		copy(0
		doTest();
	}

	public void testCopyZeros_Length128() throws IOException {
		src = new byte[2048];
		copy(0
		doTest();

		assertEquals(2636
	}

	public void testShuffleSegments() throws IOException {
		src = rng.nextBytes(128);
		copy(64
		copy(0
		doTest();
	}

	public void testInsertHeadMiddle() throws IOException {
		src = rng.nextBytes(1024);
		insert("foo");
		copy(0
		insert("yet more fooery");
		copy(0
		doTest();
	}

	public void testInsertTail() throws IOException {
		src = rng.nextBytes(1024);
		copy(0
		insert("bar");
		doTest();
	}

	public void testIndexSize() {
		src = rng.nextBytes(1024);
		DeltaIndex di = new DeltaIndex(src);
		assertEquals(1860
		assertEquals("DeltaIndex[2 KiB]"
	}

	public void testLimitObjectSize_Length12InsertFails() throws IOException {
		src = rng.nextBytes(12);
		dst = src;

		DeltaIndex di = new DeltaIndex(src);
		assertFalse(di.encode(actDeltaBuf
	}

	public void testLimitObjectSize_Length130InsertFails() throws IOException {
		src = rng.nextBytes(130);
		dst = rng.nextBytes(130);

		DeltaIndex di = new DeltaIndex(src);
		assertFalse(di.encode(actDeltaBuf
	}

	public void testLimitObjectSize_Length130CopyOk() throws IOException {
		src = rng.nextBytes(130);
		copy(0
		dst = dstBuf.toByteArray();

		DeltaIndex di = new DeltaIndex(src);
		assertTrue(di.encode(actDeltaBuf

		byte[] actDelta = actDeltaBuf.toByteArray();
		byte[] expDelta = expDeltaBuf.toByteArray();

		assertEquals(BinaryDelta.format(expDelta
				BinaryDelta.format(actDelta
	}

	public void testLimitObjectSize_Length130CopyFails() throws IOException {
		src = rng.nextBytes(130);
		copy(0
		dst = dstBuf.toByteArray();

		DeltaIndex di = new DeltaIndex(src);
		assertFalse(di.encode(actDeltaBuf
		assertEquals(4
	}

	public void testLimitObjectSize_InsertFrontFails() throws IOException {
		src = rng.nextBytes(130);
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
		assertTrue(Arrays.equals(dst
	}
}
