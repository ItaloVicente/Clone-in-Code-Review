
package org.eclipse.jgit.transport;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Before;
import org.junit.Test;


public class PacketLineOutTest {
	private ByteArrayOutputStream rawOut;

	private PacketLineOut out;

	@Before
	public void setUp() throws Exception {
		rawOut = new ByteArrayOutputStream();
		out = new PacketLineOut(rawOut);
	}


	@Test
	public void testWriteString1() throws IOException {
		out.writeString("a");
		out.writeString("bc");
		assertBuffer("0005a0006bc");
	}

	@Test
	public void testWriteString2() throws IOException {
		out.writeString("a\n");
		out.writeString("bc\n");
		assertBuffer("0006a\n0007bc\n");
	}

	@Test
	public void testWriteString3() throws IOException {
		out.writeString("");
		assertBuffer("0004");
	}


	@Test
	public void testWriteEnd() throws IOException {
		final int[] flushCnt = new int[1];
		final OutputStream mockout = new OutputStream() {
			@Override
			public void write(int arg0) throws IOException {
				rawOut.write(arg0);
			}

			@Override
			public void flush() throws IOException {
				flushCnt[0]++;
			}
		};

		new PacketLineOut(mockout).end();
		assertBuffer("0000");
		assertEquals(1
	}

	@Test
	public void testWriteDelim() throws IOException {
		out.writeDelim();
		assertBuffer("0001");
	}


	@Test
	public void testWritePacket1() throws IOException {
		out.writePacket(new byte[] { 'a' });
		assertBuffer("0005a");
	}

	@Test
	public void testWritePacket2() throws IOException {
		out.writePacket(new byte[] { 'a'
		assertBuffer("0008abcd");
	}

	@Test
	public void testWritePacket3() throws IOException {
		final int buflen = SideBandOutputStream.MAX_BUF - 5;
		final byte[] buf = new byte[buflen];
		for (int i = 0; i < buf.length; i++) {
			buf[i] = (byte) i;
		}
		out.writePacket(buf);
		out.flush();

		final byte[] act = rawOut.toByteArray();
		final String explen = Integer.toString(buf.length + 4
		assertEquals(4 + buf.length
		assertEquals(new String(act
		for (int i = 0
			assertEquals(buf[i]
		}
	}


	@Test
	public void testFlush() throws IOException {
		final int[] flushCnt = new int[1];
		final OutputStream mockout = new OutputStream() {
			@Override
			public void write(int arg0) throws IOException {
				fail("should not write");
			}

			@Override
			public void flush() throws IOException {
				flushCnt[0]++;
			}
		};

		new PacketLineOut(mockout).flush();
		assertEquals(1
	}

	private void assertBuffer(String exp) {
		assertEquals(exp
				UTF_8));
	}
}
