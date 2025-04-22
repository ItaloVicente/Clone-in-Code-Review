package org.eclipse.jgit.util.io;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.eclipse.jgit.lib.Constants;
import org.junit.Test;

public class TeeOutputStreamTest {

	@Test
	public void test() throws IOException {
		byte[] data = Constants.encode("Hello World");

		TestOutput first = new TestOutput();
		TestOutput second = new TestOutput();
		try (TeeOutputStream tee = new TeeOutputStream(first
			tee.write(data);
			assertArrayEquals("Stream output must match"
					second.toByteArray());

			tee.write(1);
			assertArrayEquals("Stream output must match"
					second.toByteArray());

			tee.write(data
			assertArrayEquals("Stream output must match"
					second.toByteArray());
		}
		assertTrue("First stream should be closed"
		assertTrue("Second stream should be closed"
	}

	@Test
	public void testCloseException() {
		TestOutput first = new TestOutput() {
			@Override
			public void close() throws IOException {
				throw new IOException();
			}

		};
		TestOutput second = new TestOutput();

		@SuppressWarnings("resource")
		TeeOutputStream tee = new TeeOutputStream(first
		try {
			tee.close();
		} catch (IOException ex) {
		}
		assertFalse("First stream should not be closed"
		assertTrue("Second stream should still be closed if first close failed"
				second.closed);
	}

	private static class TestOutput extends ByteArrayOutputStream {

		private boolean closed;

		@Override
		public void close() throws IOException {
			closed = true;
			super.close();
		}
	}

}
