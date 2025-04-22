
package org.eclipse.jgit.transport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;

import org.eclipse.jgit.internal.storage.dfs.DfsRepositoryDescription;
import org.eclipse.jgit.internal.storage.dfs.InMemoryRepository;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.BaseReceivePack.ReceiveConfig;
import org.junit.Test;

public class PushCertificateParserTest {
	@Test
	public void parseCertFromPktLine() throws Exception {
		String input = "001ccertificate version 0.1\n"
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

		PacketLineIn pckIn = newPacketLineIn(input);
		Config cfg = new Config();
		cfg.setString("receive"
		Repository db = new InMemoryRepository(
				new DfsRepositoryDescription("repo"));

		PushCertificateParser parser = new PushCertificateParser(
				db
		parser.receiveHeader(pckIn
		parser.addCommand(BaseReceivePack.parseCommand(pckIn.readStringRaw()));
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

		assertEquals(concatPacketLines(input

		String signature = concatPacketLines(input
		assertFalse(signature.contains(PushCertificateParser.BEGIN_SIGNATURE));
		assertFalse(signature.contains(PushCertificateParser.END_SIGNATURE));
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

	private static String concatPacketLines(String input
			throws IOException {
		StringBuilder result = new StringBuilder();
		int i = 0;
		PacketLineIn pckIn = newPacketLineIn(input);
		while (i < end) {
			String line;
			try {
				line = pckIn.readStringRaw();
			} catch (EOFException e) {
				break;
			}
			if (++i > begin) {
				result.append(line);
			}
		}
		return result.toString();
	}

	private static PacketLineIn newPacketLineIn(String input) {
		return new PacketLineIn(new ByteArrayInputStream(Constants.encode(input)));
	}
}
