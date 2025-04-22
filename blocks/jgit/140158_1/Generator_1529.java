
package org.eclipse.jgit.revwalk;

import java.nio.charset.Charset;

import org.eclipse.jgit.util.RawParseUtils;

public final class FooterLine {
	private final byte[] buffer;

	private final Charset enc;

	private final int keyStart;

	private final int keyEnd;

	private final int valStart;

	private final int valEnd;

	FooterLine(final byte[] b
			final int vs
		buffer = b;
		enc = e;
		keyStart = ks;
		keyEnd = ke;
		valStart = vs;
		valEnd = ve;
	}

	public boolean matches(FooterKey key) {
		final byte[] kRaw = key.raw;
		final int len = kRaw.length;
		int bPtr = keyStart;
		if (keyEnd - bPtr != len)
			return false;
		for (int kPtr = 0; kPtr < len;) {
			byte b = buffer[bPtr++];
			if ('A' <= b && b <= 'Z')
				b += 'a' - 'A';
			if (b != kRaw[kPtr++])
				return false;
		}
		return true;
	}

	public String getKey() {
		return RawParseUtils.decode(enc
	}

	public String getValue() {
		return RawParseUtils.decode(enc
	}

	public String getEmailAddress() {
		final int lt = RawParseUtils.nextLF(buffer
		if (valEnd <= lt) {
			final int at = RawParseUtils.nextLF(buffer
			if (valStart < at && at < valEnd)
				return getValue();
			return null;
		}
		final int gt = RawParseUtils.nextLF(buffer
		if (valEnd < gt)
			return null;
		return RawParseUtils.decode(enc
	}

	@Override
	public String toString() {
	}
}
