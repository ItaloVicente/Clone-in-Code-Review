
package org.eclipse.jgit.lib;

import static org.eclipse.jgit.lib.Constants.OBJ_BLOB;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import junit.framework.TestCase;

import org.eclipse.jgit.errors.LargeObjectException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.junit.TestRng;

public class ObjectLoaderTest extends TestCase {
	private TestRng rng;

	protected void setUp() throws Exception {
		super.setUp();
		rng = new TestRng(getName());
	}

	public void testSmallObjectLoader() throws MissingObjectException
			IOException {
		final byte[] act = rng.nextBytes(512);
		final ObjectLoader ldr = new ObjectLoader.SmallObject(OBJ_BLOB

		assertEquals(OBJ_BLOB
		assertEquals(act.length
		assertFalse("not is large"
		assertSame(act
		assertSame(act
		assertSame(act

		byte[] copy = ldr.getBytes();
		assertNotSame(act
		assertTrue("same content"

		copy = ldr.getBytes(1);
		assertNotSame(act
		assertTrue("same content"

		copy = ldr.getBytes(Integer.MAX_VALUE);
		assertNotSame(act
		assertTrue("same content"

		ObjectStream in = ldr.openStream();
		assertNotNull("has stream"
		assertTrue("is small stream"
		assertEquals(OBJ_BLOB
		assertEquals(act.length
		assertEquals(act.length
		assertTrue("mark supported"
		copy = new byte[act.length];
		assertEquals(act.length
		assertEquals(0
		assertEquals(-1
		assertTrue("same content"

		ByteArrayOutputStream tmp = new ByteArrayOutputStream();
		ldr.copyTo(tmp);
		assertTrue("same content"
	}

	public void testLargeObjectLoader() throws MissingObjectException
			IOException {
		final byte[] act = rng.nextBytes(512);
		final ObjectLoader ldr = new ObjectLoader() {
			@Override
			public byte[] getCachedBytes() throws LargeObjectException {
				throw new LargeObjectException();
			}

			@Override
			public long getSize() {
				return act.length;
			}

			@Override
			public int getType() {
				return OBJ_BLOB;
			}

			@Override
			public ObjectStream openStream() throws MissingObjectException
					IOException {
				return new ObjectStream.Filter(getType()
						new ByteArrayInputStream(act));
			}
		};

		assertEquals(OBJ_BLOB
		assertEquals(act.length
		assertTrue("is large"

		try {
			ldr.getCachedBytes();
			fail("did not throw on getCachedBytes()");
		} catch (LargeObjectException tooBig) {
		}

		try {
			ldr.getBytes();
			fail("did not throw on getBytes()");
		} catch (LargeObjectException tooBig) {
		}

		try {
			ldr.getCachedBytes(64);
			fail("did not throw on getCachedBytes(64)");
		} catch (LargeObjectException tooBig) {
		}

		byte[] copy = ldr.getCachedBytes(1024);
		assertNotSame(act
		assertTrue("same content"

		ObjectStream in = ldr.openStream();
		assertNotNull("has stream"
		assertEquals(OBJ_BLOB
		assertEquals(act.length
		assertEquals(act.length
		assertTrue("mark supported"
		copy = new byte[act.length];
		assertEquals(act.length
		assertEquals(0
		assertEquals(-1
		assertTrue("same content"

		ByteArrayOutputStream tmp = new ByteArrayOutputStream();
		ldr.copyTo(tmp);
		assertTrue("same content"
	}

	public void testLimitedGetCachedBytes() throws LargeObjectException
			MissingObjectException
		byte[] act = rng.nextBytes(512);
		ObjectLoader ldr = new ObjectLoader.SmallObject(OBJ_BLOB
			@Override
			public boolean isLarge() {
				return true;
			}
		};
		assertTrue("is large"

		try {
			ldr.getCachedBytes(10);
			fail("Did not throw LargeObjectException");
		} catch (LargeObjectException tooBig) {
		}

		byte[] copy = ldr.getCachedBytes(512);
		assertNotSame(act
		assertTrue("same content"

		copy = ldr.getCachedBytes(1024);
		assertNotSame(act
		assertTrue("same content"
	}

	public void testLimitedGetCachedBytesExceedsJavaLimits()
			throws LargeObjectException
		ObjectLoader ldr = new ObjectLoader() {
			@Override
			public boolean isLarge() {
				return true;
			}

			@Override
			public byte[] getCachedBytes() throws LargeObjectException {
				throw new LargeObjectException();
			}

			@Override
			public long getSize() {
				return Long.MAX_VALUE;
			}

			@Override
			public int getType() {
				return OBJ_BLOB;
			}

			@Override
			public ObjectStream openStream() throws MissingObjectException
					IOException {
				return new ObjectStream() {
					@Override
					public long getSize() {
						return Long.MAX_VALUE;
					}

					@Override
					public int getType() {
						return OBJ_BLOB;
					}

					@Override
					public int read() throws IOException {
						fail("never should have reached read");
						return -1;
					}
				};
			}
		};
		assertTrue("is large"

		try {
			ldr.getCachedBytes(10);
			fail("Did not throw LargeObjectException");
		} catch (LargeObjectException tooBig) {
		}

		try {
			ldr.getCachedBytes(Integer.MAX_VALUE);
			fail("Did not throw LargeObjectException");
		} catch (LargeObjectException tooBig) {
		}
	}
}
