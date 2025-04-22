package org.eclipse.jgit.transport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

import org.junit.Before;
import org.junit.Test;

public class SideBandInputStreamTest {

	private StringWriter messages;

	private SideBandInputStream sideband;

	@Before
	public void setup() {
		messages = new StringWriter();
	}

	@Test
	public void progressSingleCR() throws IOException {
		init(packet("message\r"));
		assertTrue(sideband.read() < 0);
		assertEquals("message\r"
	}

	@Test
	public void progressSingleLF() throws IOException {
		init(packet("message\n"));
		assertTrue(sideband.read() < 0);
		assertEquals("message\n"
	}

	@Test
	public void progressSingleCRLF() throws IOException {
		init(packet("message\r\n"));
		assertTrue(sideband.read() < 0);
		assertEquals("message\r\n"
	}

	@Test
	public void progressMultiCR() throws IOException {
		init(packet("message   0%\rmessage 100%\r"));
		assertTrue(sideband.read() < 0);
		assertEquals("message   0%\rmessage 100%\r"
	}

	@Test
	public void progressMultiLF() throws IOException {
		init(packet("message   0%\nmessage 100%\n"));
		assertTrue(sideband.read() < 0);
		assertEquals("message   0%\nmessage 100%\n"
	}

	@Test
	public void progressMultiCRLF() throws IOException {
		init(packet("message   0%\r\nmessage 100%\r\n"));
		assertTrue(sideband.read() < 0);
		assertEquals("message   0%\r\nmessage 100%\r\n"
	}

	@Test
	public void progressPartial() throws IOException {
		init(packet("message"));
		assertTrue(sideband.read() < 0);
		assertEquals(""
		sideband.drainMessages();
		assertEquals("message\n"
	}

	@Test
	public void progressPartialTwoCR() throws IOException {
		init(packet("message") + packet("message\r"));
		assertTrue(sideband.read() < 0);
		assertEquals("messagemessage\r"
	}

	@Test
	public void progressPartialTwoLF() throws IOException {
		init(packet("message") + packet("message\n"));
		assertTrue(sideband.read() < 0);
		assertEquals("messagemessage\n"
	}

	@Test
	public void progressPartialTwoCRLF() throws IOException {
		init(packet("message") + packet("message\r\n"));
		assertTrue(sideband.read() < 0);
		assertEquals("messagemessage\r\n"
	}

	@Test
	public void progressPartialThreeCR() throws IOException {
		init(packet("message") + packet("message") + packet("message\r"));
		assertTrue(sideband.read() < 0);
		assertEquals("messagemessagemessage\r"
	}

	@Test
	public void progressPartialThreeLF() throws IOException {
		init(packet("message") + packet("message") + packet("message\n"));
		assertTrue(sideband.read() < 0);
		assertEquals("messagemessagemessage\n"
	}

	@Test
	public void progressPartialThreeCRLF() throws IOException {
		init(packet("message") + packet("message") + packet("message\r\n"));
		assertTrue(sideband.read() < 0);
		assertEquals("messagemessagemessage\r\n"
	}

	@Test
	public void progressPartialCR() throws IOException {
		init(packet("message   0%\rmessage 100%"));
		assertTrue(sideband.read() < 0);
		assertEquals("message   0%\r"
		sideband.drainMessages();
		assertEquals("message   0%\rmessage 100%\n"
	}

	@Test
	public void progressPartialLF() throws IOException {
		init(packet("message   0%\nmessage 100%"));
		assertTrue(sideband.read() < 0);
		assertEquals("message   0%\n"
		sideband.drainMessages();
		assertEquals("message   0%\nmessage 100%\n"
	}

	@Test
	public void progressPartialCRLF() throws IOException {
		init(packet("message   0%\r\nmessage 100%"));
		assertTrue(sideband.read() < 0);
		assertEquals("message   0%\r\n"
		sideband.drainMessages();
		assertEquals("message   0%\r\nmessage 100%\n"
	}

	@Test
	public void progressPartialSplitCR() throws IOException {
		init(packet("message") + "0006\001a" + packet("   0%\rmessa")
				+ packet("ge 100%"));
		assertEquals('a'
		assertEquals(""
		assertTrue(sideband.read() < 0);
		assertEquals("message   0%\r"
		sideband.drainMessages();
		assertEquals("message   0%\rmessage 100%\n"
	}

	@Test
	public void progressPartialSplitLF() throws IOException {
		init(packet("message") + "0006\001a" + packet("   0%\nmessa")
				+ packet("ge 100%"));
		assertEquals('a'
		assertEquals(""
		assertTrue(sideband.read() < 0);
		assertEquals("message   0%\n"
		sideband.drainMessages();
		assertEquals("message   0%\nmessage 100%\n"
	}

	@Test
	public void progressPartialSplitCRLF() throws IOException {
		init(packet("message") + "0006\001a" + packet("   0%\r\nmessa")
				+ packet("ge 100%"));
		assertEquals('a'
		assertEquals(""
		assertTrue(sideband.read() < 0);
		assertEquals("message   0%\r\n"
		sideband.drainMessages();
		assertEquals("message   0%\r\nmessage 100%\n"
	}

	@Test
	public void progressInterleaved() throws IOException {
		init(packet("message   0%\r") + "0006\001a" + packet("message  10%")
				+ "0006\001b" + packet("\rmessage 100%\n"));
		assertEquals('a'
		assertEquals("message   0%\r"
		assertEquals('b'
		assertEquals("message   0%\r"
		assertTrue(sideband.read() < 0);
		assertEquals("message   0%\rmessage  10%\rmessage 100%\n"
				messages.toString());
	}

	@Test
	public void progressInterleavedPartial() throws IOException {
		init(packet("message   0%\r") + "0006\001a" + packet("message  10%")
				+ "0006\001b" + packet("\rmessage 100%"));
		assertEquals('a'
		assertEquals("message   0%\r"
		assertEquals('b'
		assertEquals("message   0%\r"
		assertTrue(sideband.read() < 0);
		assertEquals("message   0%\rmessage  10%\r"
		sideband.drainMessages();
		assertEquals("message   0%\rmessage  10%\rmessage 100%\n"
				messages.toString());
	}

	private String packet(String data) {
		return String.format("%04x\002%s"
				data);
	}

	private void init(String packets) {
		InputStream rawIn = new ByteArrayInputStream(
				(packets + "0000").getBytes(StandardCharsets.UTF_8));
		sideband = new SideBandInputStream(rawIn
	}
}
