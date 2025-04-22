
package org.eclipse.jgit.transport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.transport.RefAdvertiser.PacketLineOutRefAdvertiser;
import org.eclipse.jgit.util.NB;
import org.junit.Test;

public class RefAdvertiserTest {
	@Test
	public void advertiser() throws IOException {
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		PacketLineOut pckOut = new PacketLineOut(buf);
		PacketLineOutRefAdvertiser adv = new PacketLineOutRefAdvertiser(pckOut);


		adv.advertiseCapability("test-1");
		adv.advertiseCapability("sideband");
		adv.advertiseId(id(1)
		adv.advertiseId(id(4)
		adv.advertiseId(id(2)
		adv.advertiseId(id(3)
		adv.end();
		assertFalse(adv.isEmpty());

		PacketLineIn pckIn = new PacketLineIn(
				new ByteArrayInputStream(buf.toByteArray()));
		String s = pckIn.readStringRaw();
		assertEquals(id(1).name() + " refs/heads/master\0 test-1 sideband\n"
				s);

		s = pckIn.readStringRaw();
		assertEquals(id(4).name() + " refs/" + padStart("F"

		s = pckIn.readStringRaw();
		assertEquals(id(2).name() + " refs/heads/next\n"

		s = pckIn.readStringRaw();
		assertEquals(id(3).name() + " refs/IÃ±tÃ«rnÃ¢tiÃ´nÃ lizÃ¦tiÃ¸nâð©\n"

		s = pckIn.readStringRaw();
		assertSame(PacketLineIn.END
	}

	private static ObjectId id(int i) {
		try (ObjectInserter.Formatter f = new ObjectInserter.Formatter()) {
			byte[] tmp = new byte[4];
			NB.encodeInt32(tmp
			return f.idFor(Constants.OBJ_BLOB
		}
	}

	private static String padStart(String s
		StringBuilder b = new StringBuilder(len);
		for (int i = s.length(); i < len; i++) {
			b.append((char) ('a' + (i % 26)));
		}
		return b.append(s).toString();
	}
}
