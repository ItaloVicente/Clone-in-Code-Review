
package org.eclipse.jgit.storage.pack;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import junit.framework.TestCase;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.junit.TestRng;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.util.IO;

public class DeltaStreamTest extends TestCase {
	private TestRng rng;

	private ByteArrayOutputStream deltaBuf;

	private DeltaEncoder deltaEnc;

	private byte[] base;

	private byte[] data;

	private int dataPtr;

	private byte[] delta;

	protected void setUp() throws Exception {
		super.setUp();
		rng = new TestRng(getName());
		deltaBuf = new ByteArrayOutputStream();
	}

	public void testCopy_SingleOp() throws IOException {
		init((1 << 16) + 1
		copy(0
		assertValidState();
	}

	public void testCopy_MaxSize() throws IOException {
		int max = (0xff << 16) + (0xff << 8) + 0xff;
		init(1 + max
		copy(1
		assertValidState();
	}

	public void testCopy_64k() throws IOException {
		init(0x10000 + 2
		copy(1
		copy(0x10001
		assertValidState();
	}

	public void testCopy_Gap() throws IOException {
		init(256
		copy(4
		copy(128
		assertValidState();
	}

	public void testCopy_OutOfOrder() throws IOException {
		init((1 << 16) + 1
		copy(1 << 8
		copy(0
		assertValidState();
	}

	public void testInsert_SingleOp() throws IOException {
		init((1 << 16) + 1
		insert("hi");
		assertValidState();
	}

	public void testInsertAndCopy() throws IOException {
		init(8
		insert(new byte[127]);
		insert(new byte[127]);
		insert(new byte[127]);
		insert(new byte[125]);
		copy(2
		assertValidState();
	}

	public void testSkip() throws IOException {
		init(32
		copy(2
		insert("ab");
		insert("cd");
		copy(4
		copy(0
		insert("efg");
		assertValidState();

		for (int p = 0; p < data.length; p++) {
			byte[] act = new byte[data.length];
			System.arraycopy(data
			DeltaStream in = open();
			IO.skipFully(in
			assertEquals(data.length - p
			assertEquals(-1
			assertTrue("skipping " + p
		}

		DeltaStream in = open();
		IO.skipFully(in
		assertEquals(-1
		assertEquals(0

		final boolean[] opened = new boolean[1];
		in = new DeltaStream(new ByteArrayInputStream(delta)) {
			@Override
			protected long getBaseSize() throws IOException {
				return base.length;
			}

			@Override
			protected InputStream openBase() throws IOException {
				opened[0] = true;
				return new ByteArrayInputStream(base);
			}
		};
		IO.skipFully(in
		assertFalse("not yet open"
		assertEquals(data[7]
		assertTrue("now open"
	}

	public void testIncorrectBaseSize() throws IOException {
		init(4
		copy(0
		assertValidState();

		DeltaStream in = new DeltaStream(new ByteArrayInputStream(delta)) {
			@Override
			protected long getBaseSize() throws IOException {
				return 128;
			}

			@Override
			protected InputStream openBase() throws IOException {
				return new ByteArrayInputStream(base);
			}
		};
		try {
			in.read(new byte[4]);
			fail("did not throw an exception");
		} catch (CorruptObjectException e) {
			assertEquals(JGitText.get().baseLengthIncorrect
		}

		in = new DeltaStream(new ByteArrayInputStream(delta)) {
			@Override
			protected long getBaseSize() throws IOException {
				return 4;
			}

			@Override
			protected InputStream openBase() throws IOException {
				return new ByteArrayInputStream(new byte[0]);
			}
		};
		try {
			in.read(new byte[4]);
			fail("did not throw an exception");
		} catch (CorruptObjectException e) {
			assertEquals(JGitText.get().baseLengthIncorrect
		}
	}

	private void init(int baseSize
		base = rng.nextBytes(baseSize);
		data = new byte[dataSize];
		deltaEnc = new DeltaEncoder(deltaBuf
	}

	private void copy(int offset
		System.arraycopy(base
		deltaEnc.copy(offset
		assertEquals(deltaBuf.size()
		dataPtr += len;
	}

	private void insert(String text) throws IOException {
		insert(Constants.encode(text));
	}

	private void insert(byte[] text) throws IOException {
		System.arraycopy(text
		deltaEnc.insert(text);
		assertEquals(deltaBuf.size()
		dataPtr += text.length;
	}

	private void assertValidState() throws IOException {
		assertEquals("test filled example result"

		delta = deltaBuf.toByteArray();
		assertEquals(base.length
		assertEquals(data.length
		assertTrue(Arrays.equals(data

		byte[] act = new byte[data.length];
		DeltaStream in = open();
		assertEquals(data.length
		assertEquals(data.length
		assertEquals(-1
		assertTrue(Arrays.equals(data
	}

	private DeltaStream open() throws IOException {
		return new DeltaStream(new ByteArrayInputStream(delta)) {
			@Override
			protected long getBaseSize() throws IOException {
				return base.length;
			}

			@Override
			protected InputStream openBase() throws IOException {
				return new ByteArrayInputStream(base);
			}
		};
	}
}
