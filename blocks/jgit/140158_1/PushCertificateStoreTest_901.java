
package org.eclipse.jgit.transport;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

import org.eclipse.jgit.errors.PackProtocolException;
import org.eclipse.jgit.internal.storage.dfs.DfsRepositoryDescription;
import org.eclipse.jgit.internal.storage.dfs.InMemoryRepository;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.PushCertificate.NonceStatus;
import org.junit.Before;
import org.junit.Test;

public class PushCertificateParserTest {
	private static final String INPUT = "001ccertificate version 0.1\n"
			+ "0041pusher Dave Borowitz <dborowitz@google.com> 1433954361 -0700\n"
			+ "002anonce 1433954361-bde756572d665bba81d8\n"
			+ "0005\n"
			+ "00680000000000000000000000000000000000000000"
			+ " 6c2b981a177396fb47345b7df3e4d3f854c6bea7"
			+ " refs/heads/master\n"
			+ "0022-----BEGIN PGP SIGNATURE-----\n"
			+ "0016Version: GnuPG v1\n"
			+ "0005\n"
			+ "0045iQEcBAABAgAGBQJVeGg5AAoJEPfTicJkUdPkUggH/RKAeI9/i/LduuiqrL/SSdIa\n"
			+ "00459tYaSqJKLbXz63M/AW4Sp+4u+dVCQvnAt/a35CVEnpZz6hN4Kn/tiswOWVJf4CO7\n"
			+ "0045htNubGs5ZMwvD6sLYqKAnrM3WxV/2TbbjzjZW6Jkidz3jz/WRT4SmjGYiEO7aA+V\n"
			+ "00454ZdIS9f7sW5VsHHYlNThCA7vH8Uu48bUovFXyQlPTX0pToSgrWV3JnTxDNxfn3iG\n"
			+ "0045IL0zTY/qwVCdXgFownLcs6J050xrrBWIKqfcWr3u4D2aCLyR0v+S/KArr7ulZygY\n"
			+ "0045+SOklImn8TAZiNxhWtA6ens66IiammUkZYFv7SSzoPLFZT4dC84SmGPWgf94NoQ=\n"
			+ "000a=XFeC\n"
			+ "0020-----END PGP SIGNATURE-----\n"
			+ "0012push-cert-end\n";

	private static final String INPUT_NO_NEWLINES = "001bcertificate version 0.1"
			+ "0040pusher Dave Borowitz <dborowitz@google.com> 1433954361 -0700"
			+ "0029nonce 1433954361-bde756572d665bba81d8"
			+ "0004"
			+ "00670000000000000000000000000000000000000000"
			+ " 6c2b981a177396fb47345b7df3e4d3f854c6bea7"
			+ " refs/heads/master"
			+ "0021-----BEGIN PGP SIGNATURE-----"
			+ "0015Version: GnuPG v1"
			+ "0004"
			+ "0044iQEcBAABAgAGBQJVeGg5AAoJEPfTicJkUdPkUggH/RKAeI9/i/LduuiqrL/SSdIa"
			+ "00449tYaSqJKLbXz63M/AW4Sp+4u+dVCQvnAt/a35CVEnpZz6hN4Kn/tiswOWVJf4CO7"
			+ "0044htNubGs5ZMwvD6sLYqKAnrM3WxV/2TbbjzjZW6Jkidz3jz/WRT4SmjGYiEO7aA+V"
			+ "00444ZdIS9f7sW5VsHHYlNThCA7vH8Uu48bUovFXyQlPTX0pToSgrWV3JnTxDNxfn3iG"
			+ "0044IL0zTY/qwVCdXgFownLcs6J050xrrBWIKqfcWr3u4D2aCLyR0v+S/KArr7ulZygY"
			+ "0044+SOklImn8TAZiNxhWtA6ens66IiammUkZYFv7SSzoPLFZT4dC84SmGPWgf94NoQ="
			+ "0009=XFeC"
			+ "001f-----END PGP SIGNATURE-----"
			+ "0011push-cert-end";

	private Repository db;

	@Before
	public void setUp() {
		db = new InMemoryRepository(new DfsRepositoryDescription("repo"));
	}

	private static SignedPushConfig newEnabledConfig() {
		Config cfg = new Config();
		cfg.setString("receive"
		return SignedPushConfig.KEY.parse(cfg);
	}

	private static SignedPushConfig newDisabledConfig() {
		return SignedPushConfig.KEY.parse(new Config());
	}

	@Test
	public void noCert() throws Exception {
		PushCertificateParser parser =
				new PushCertificateParser(db
		assertTrue(parser.enabled());
		assertNull(parser.build());

		ObjectId oldId = ObjectId.zeroId();
		ObjectId newId =
				ObjectId.fromString("deadbeefdeadbeefdeadbeefdeadbeefdeadbeef");
		String line = oldId.name() + " " + newId.name() + " refs/heads/master";
		ReceiveCommand cmd = BaseReceivePack.parseCommand(line);

		parser.addCommand(cmd);
		parser.addCommand(line);
		assertNull(parser.build());
	}

	@Test
	public void disabled() throws Exception {
		PacketLineIn pckIn = newPacketLineIn(INPUT);
		PushCertificateParser parser =
				new PushCertificateParser(db
		assertFalse(parser.enabled());
		assertNull(parser.build());

		parser.receiveHeader(pckIn
		parser.addCommand(pckIn.readString());
		assertEquals(PushCertificateParser.BEGIN_SIGNATURE
		parser.receiveSignature(pckIn);
		assertNull(parser.build());
	}

	@Test
	public void disabledParserStillRequiresCorrectSyntax() throws Exception {
		PacketLineIn pckIn = newPacketLineIn("001ccertificate version XYZ\n");
		PushCertificateParser parser =
				new PushCertificateParser(db
		assertFalse(parser.enabled());
		try {
			parser.receiveHeader(pckIn
			fail("Expected PackProtocolException");
		} catch (PackProtocolException e) {
			assertEquals(
					"Push certificate has missing or invalid value for certificate"
						+ " version: XYZ"
					e.getMessage());
		}
		assertNull(parser.build());
	}

	@Test
	public void parseCertFromPktLine() throws Exception {
		PacketLineIn pckIn = newPacketLineIn(INPUT);
		PushCertificateParser parser =
				new PushCertificateParser(db
		parser.receiveHeader(pckIn
		parser.addCommand(pckIn.readString());
		assertEquals(PushCertificateParser.BEGIN_SIGNATURE
		parser.receiveSignature(pckIn);

		PushCertificate cert = parser.build();
		assertEquals("0.1"
		assertEquals("Dave Borowitz"
		assertEquals("dborowitz@google.com"
				cert.getPusherIdent().getEmailAddress());
		assertEquals(1433954361000L
		assertEquals(-7 * 60
		assertEquals("1433954361-bde756572d665bba81d8"

		assertNotEquals(cert.getNonce()
		assertEquals(PushCertificate.NonceStatus.BAD

		assertEquals(1
		ReceiveCommand cmd = cert.getCommands().get(0);
		assertEquals("refs/heads/master"
		assertEquals(ObjectId.zeroId()
		assertEquals("6c2b981a177396fb47345b7df3e4d3f854c6bea7"
				cmd.getNewId().name());

		assertEquals(concatPacketLines(INPUT
		assertEquals(concatPacketLines(INPUT

		String signature = concatPacketLines(INPUT
		assertTrue(signature.startsWith(PushCertificateParser.BEGIN_SIGNATURE));
		assertTrue(signature.endsWith(PushCertificateParser.END_SIGNATURE + "\n"));
		assertEquals(signature
	}

	@Test
	public void parseCertFromPktLineNoNewlines() throws Exception {
		PacketLineIn pckIn = newPacketLineIn(INPUT_NO_NEWLINES);
		PushCertificateParser parser =
				new PushCertificateParser(db
		parser.receiveHeader(pckIn
		parser.addCommand(pckIn.readString());
		assertEquals(PushCertificateParser.BEGIN_SIGNATURE
		parser.receiveSignature(pckIn);

		PushCertificate cert = parser.build();
		assertEquals("0.1"
		assertEquals("Dave Borowitz"
		assertEquals("dborowitz@google.com"
				cert.getPusherIdent().getEmailAddress());
		assertEquals(1433954361000L
		assertEquals(-7 * 60
		assertEquals("1433954361-bde756572d665bba81d8"

		assertNotEquals(cert.getNonce()
		assertEquals(PushCertificate.NonceStatus.BAD

		assertEquals(1
		ReceiveCommand cmd = cert.getCommands().get(0);
		assertEquals("refs/heads/master"
		assertEquals(ObjectId.zeroId()
		assertEquals("6c2b981a177396fb47345b7df3e4d3f854c6bea7"
				cmd.getNewId().name());

		assertEquals(concatPacketLines(INPUT

		String signature = concatPacketLines(INPUT
		assertTrue(signature.startsWith(PushCertificateParser.BEGIN_SIGNATURE));
		assertTrue(signature.endsWith(PushCertificateParser.END_SIGNATURE + "\n"));
		assertEquals(signature
	}

	@Test
	public void testConcatPacketLines() throws Exception {
		String input = "000bline 1\n000bline 2\n000bline 3\n";
		assertEquals("line 1\n"
		assertEquals("line 1\nline 2\n"
		assertEquals("line 2\nline 3\n"
		assertEquals("line 2\nline 3\n"
	}

	@Test
	public void testConcatPacketLinesInsertsNewlines() throws Exception {
		String input = "000bline 1\n000aline 2000bline 3\n";
		assertEquals("line 1\n"
		assertEquals("line 1\nline 2\n"
		assertEquals("line 2\nline 3\n"
		assertEquals("line 2\nline 3\n"
	}

	@Test
	public void testParseReader() throws Exception {
		Reader reader = new StringReader(concatPacketLines(INPUT
		PushCertificate streamCert = PushCertificateParser.fromReader(reader);

		PacketLineIn pckIn = newPacketLineIn(INPUT);
		PushCertificateParser pckParser =
				new PushCertificateParser(db
		pckParser.receiveHeader(pckIn
		pckParser.addCommand(pckIn.readString());
		assertEquals(PushCertificateParser.BEGIN_SIGNATURE
		pckParser.receiveSignature(pckIn);
		PushCertificate pckCert = pckParser.build();

		assertEquals(NonceStatus.UNSOLICITED

		assertEquals(pckCert.getVersion()
		assertEquals(pckCert.getPusherIdent().getName()
				streamCert.getPusherIdent().getName());
		assertEquals(pckCert.getPusherIdent().getEmailAddress()
				streamCert.getPusherIdent().getEmailAddress());
		assertEquals(pckCert.getPusherIdent().getWhen().getTime()
				streamCert.getPusherIdent().getWhen().getTime());
		assertEquals(pckCert.getPusherIdent().getTimeZoneOffset()
				streamCert.getPusherIdent().getTimeZoneOffset());
		assertEquals(pckCert.getPushee()
		assertEquals(pckCert.getNonce()
		assertEquals(pckCert.getSignature()
		assertEquals(pckCert.toText()

		assertEquals(pckCert.getCommands().size()
		ReceiveCommand pckCmd = pckCert.getCommands().get(0);
		ReceiveCommand streamCmd = streamCert.getCommands().get(0);
		assertEquals(pckCmd.getRefName()
		assertEquals(pckCmd.getOldId()
		assertEquals(pckCmd.getNewId().name()
	}

	@Test
	public void testParseString() throws Exception {
		String str = concatPacketLines(INPUT
		assertEquals(
				PushCertificateParser.fromReader(new StringReader(str))
				PushCertificateParser.fromString(str));
	}

	@Test
	public void testParseMultipleFromStream() throws Exception {
		String input = concatPacketLines(INPUT
		assertFalse(input.contains(PushCertificateParser.END_CERT));
		input += input;
		Reader reader = new InputStreamReader(
				new ByteArrayInputStream(Constants.encode(input))

		assertNotNull(PushCertificateParser.fromReader(reader));
		assertNotNull(PushCertificateParser.fromReader(reader));
		assertEquals(-1
		assertNull(PushCertificateParser.fromReader(reader));
	}

	@Test
	public void testMissingPusheeField() throws Exception {
		assertFalse(input.contains(PushCertificateParser.PUSHEE));

		PacketLineIn pckIn = newPacketLineIn(input);
		PushCertificateParser parser =
				new PushCertificateParser(db
		parser.receiveHeader(pckIn
		parser.addCommand(pckIn.readString());
		assertEquals(PushCertificateParser.BEGIN_SIGNATURE
		parser.receiveSignature(pckIn);

		PushCertificate cert = parser.build();
		assertEquals("0.1"
		assertNull(cert.getPushee());
		assertFalse(cert.toText().contains(PushCertificateParser.PUSHEE));
	}

	private static String concatPacketLines(String input
			throws IOException {
		StringBuilder result = new StringBuilder();
		int i = 0;
		PacketLineIn pckIn = newPacketLineIn(input);
		while (i < end) {
			String line;
			try {
				line = pckIn.readString();
			} catch (EOFException e) {
				break;
			}
			if (++i > begin) {
				result.append(line).append('\n');
			}
		}
		return result.toString();
	}

	private static PacketLineIn newPacketLineIn(String input) {
		return new PacketLineIn(new ByteArrayInputStream(Constants.encode(input)));
	}
}
