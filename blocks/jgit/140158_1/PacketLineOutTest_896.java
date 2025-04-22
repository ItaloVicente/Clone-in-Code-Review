
package org.eclipse.jgit.transport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.eclipse.jgit.errors.PackProtocolException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.MutableObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.junit.Test;


public class PacketLineInTest {
	private ByteArrayInputStream rawIn;

	private PacketLineIn in;


	@Test
	public void testReadString1() throws IOException {
		init("0006a\n0007bc\n");
		assertEquals("a"
		assertEquals("bc"
		assertEOF();
	}

	@Test
	public void testReadString2() throws IOException {
		init("0032want fcfcfb1fd94829c1a1704f894fc111d14770d34e\n");
		final String act = in.readString();
		assertEquals("want fcfcfb1fd94829c1a1704f894fc111d14770d34e"
		assertEOF();
	}

	@Test
	public void testReadString4() throws IOException {
		init("0005a0006bc");
		assertEquals("a"
		assertEquals("bc"
		assertEOF();
	}

	@Test
	public void testReadString5() throws IOException {
		init("000Fhi i am a s");
		assertEquals("hi i am a s"
		assertEOF();

		init("000fhi i am a s");
		assertEquals("hi i am a s"
		assertEOF();
	}

	@Test
	public void testReadString_LenHELO() {
		init("HELO");
		try {
			in.readString();
			fail("incorrectly accepted invalid packet header");
		} catch (IOException e) {
			assertEquals("Invalid packet line header: HELO"
		}
	}

	@Test
	public void testReadString_Len0002() {
		init("0002");
		try {
			in.readString();
			fail("incorrectly accepted invalid packet header");
		} catch (IOException e) {
			assertEquals("Invalid packet line header: 0002"
		}
	}

	@Test
	public void testReadString_Len0003() {
		init("0003");
		try {
			in.readString();
			fail("incorrectly accepted invalid packet header");
		} catch (IOException e) {
			assertEquals("Invalid packet line header: 0003"
		}
	}

	@Test
	public void testReadString_Len0004() throws IOException {
		init("0004");
		final String act = in.readString();
		assertEquals(""
		assertNotSame(PacketLineIn.END
		assertEOF();
	}

	@Test
	public void testReadString_End() throws IOException {
		init("0000");
		assertSame(PacketLineIn.END
		assertEOF();
	}

	@Test
	public void testReadString_Delim() throws IOException {
		init("0001");
		assertSame(PacketLineIn.DELIM
		assertEOF();
	}


	@Test
	public void testReadStringRaw1() throws IOException {
		init("0005a0006bc");
		assertEquals("a"
		assertEquals("bc"
		assertEOF();
	}

	@Test
	public void testReadStringRaw2() throws IOException {
		init("0031want fcfcfb1fd94829c1a1704f894fc111d14770d34e");
		final String act = in.readStringRaw();
		assertEquals("want fcfcfb1fd94829c1a1704f894fc111d14770d34e"
		assertEOF();
	}

	@Test
	public void testReadStringRaw3() throws IOException {
		init("0004");
		final String act = in.readStringRaw();
		assertEquals(""
		assertNotSame(PacketLineIn.END
		assertEOF();
	}

	@Test
	public void testReadStringRaw_End() throws IOException {
		init("0000");
		assertSame(PacketLineIn.END
		assertEOF();
	}

	@Test
	public void testReadStringRaw4() {
		init("HELO");
		try {
			in.readStringRaw();
			fail("incorrectly accepted invalid packet header");
		} catch (IOException e) {
			assertEquals("Invalid packet line header: HELO"
		}
	}


	@Test
	public void testReadACK_NAK() throws IOException {
		final ObjectId expid = ObjectId
				.fromString("fcfcfb1fd94829c1a1704f894fc111d14770d34e");
		final MutableObjectId actid = new MutableObjectId();
		actid.fromString(expid.name());

		init("0008NAK\n");
		assertSame(PacketLineIn.AckNackResult.NAK
		assertEquals(expid
		assertEOF();
	}

	@Test
	public void testReadACK_ACK1() throws IOException {
		final ObjectId expid = ObjectId
				.fromString("fcfcfb1fd94829c1a1704f894fc111d14770d34e");
		final MutableObjectId actid = new MutableObjectId();

		init("0031ACK fcfcfb1fd94829c1a1704f894fc111d14770d34e\n");
		assertSame(PacketLineIn.AckNackResult.ACK
		assertEquals(expid
		assertEOF();
	}

	@Test
	public void testReadACK_ACKcontinue1() throws IOException {
		final ObjectId expid = ObjectId
				.fromString("fcfcfb1fd94829c1a1704f894fc111d14770d34e");
		final MutableObjectId actid = new MutableObjectId();

		init("003aACK fcfcfb1fd94829c1a1704f894fc111d14770d34e continue\n");
		assertSame(PacketLineIn.AckNackResult.ACK_CONTINUE
		assertEquals(expid
		assertEOF();
	}

	@Test
	public void testReadACK_ACKcommon1() throws IOException {
		final ObjectId expid = ObjectId
				.fromString("fcfcfb1fd94829c1a1704f894fc111d14770d34e");
		final MutableObjectId actid = new MutableObjectId();

		init("0038ACK fcfcfb1fd94829c1a1704f894fc111d14770d34e common\n");
		assertSame(PacketLineIn.AckNackResult.ACK_COMMON
		assertEquals(expid
		assertEOF();
	}

	@Test
	public void testReadACK_ACKready1() throws IOException {
		final ObjectId expid = ObjectId
				.fromString("fcfcfb1fd94829c1a1704f894fc111d14770d34e");
		final MutableObjectId actid = new MutableObjectId();

		init("0037ACK fcfcfb1fd94829c1a1704f894fc111d14770d34e ready\n");
		assertSame(PacketLineIn.AckNackResult.ACK_READY
		assertEquals(expid
		assertEOF();
	}

	@Test
	public void testReadACK_Invalid1() {
		init("HELO");
		try {
			in.readACK(new MutableObjectId());
			fail("incorrectly accepted invalid packet header");
		} catch (IOException e) {
			assertEquals("Invalid packet line header: HELO"
		}
	}

	@Test
	public void testReadACK_Invalid2() {
		init("0009HELO\n");
		try {
			in.readACK(new MutableObjectId());
			fail("incorrectly accepted invalid ACK/NAK");
		} catch (IOException e) {
			assertEquals("Expected ACK/NAK
		}
	}

	@Test
	public void testReadACK_Invalid3() {
		String s = "ACK fcfcfb1fd94829c1a1704f894fc111d14770d34e neverhappen";
		init("003d" + s + "\n");
		try {
			in.readACK(new MutableObjectId());
			fail("incorrectly accepted unsupported ACK status");
		} catch (IOException e) {
			assertEquals("Expected ACK/NAK
		}
	}

	@Test
	public void testReadACK_Invalid4() {
		init("0000");
		try {
			in.readACK(new MutableObjectId());
			fail("incorrectly accepted no ACK/NAK");
		} catch (IOException e) {
			assertEquals("Expected ACK/NAK
		}
	}

	@Test
	public void testReadACK_ERR() throws IOException {
		init("001aERR want is not valid\n");
		try {
			in.readACK(new MutableObjectId());
			fail("incorrectly accepted ERR");
		} catch (PackProtocolException e) {
			assertEquals("want is not valid"
		}
	}


	private void init(String msg) {
		rawIn = new ByteArrayInputStream(Constants.encodeASCII(msg));
		in = new PacketLineIn(rawIn);
	}

	private void assertEOF() {
		assertEquals(-1
	}
}
