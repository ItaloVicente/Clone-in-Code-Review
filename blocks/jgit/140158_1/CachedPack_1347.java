
package org.eclipse.jgit.internal.storage.pack;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.util.QuotedString;
import org.eclipse.jgit.util.RawParseUtils;

public class BinaryDelta {
	public static long getBaseSize(byte[] delta) {
		int p = 0;
		long baseLen = 0;
		int c
		do {
			c = delta[p++] & 0xff;
			baseLen |= ((long) (c & 0x7f)) << shift;
			shift += 7;
		} while ((c & 0x80) != 0);
		return baseLen;
	}

	public static long getResultSize(byte[] delta) {
		int p = 0;

		int c;
		do {
			c = delta[p++] & 0xff;
		} while ((c & 0x80) != 0);

		long resLen = 0;
		int shift = 0;
		do {
			c = delta[p++] & 0xff;
			resLen |= ((long) (c & 0x7f)) << shift;
			shift += 7;
		} while ((c & 0x80) != 0);
		return resLen;
	}

	public static final byte[] apply(byte[] base
		return apply(base
	}

	public static final byte[] apply(final byte[] base
			byte[] result) {
		int deltaPtr = 0;

		int baseLen = 0;
		int c
		do {
			c = delta[deltaPtr++] & 0xff;
			baseLen |= ((long) (c & 0x7f)) << shift;
			shift += 7;
		} while ((c & 0x80) != 0);
		if (base.length != baseLen)
			throw new IllegalArgumentException(
					JGitText.get().baseLengthIncorrect);

		int resLen = 0;
		shift = 0;
		do {
			c = delta[deltaPtr++] & 0xff;
			resLen |= ((long) (c & 0x7f)) << shift;
			shift += 7;
		} while ((c & 0x80) != 0);

		if (result == null)
			result = new byte[resLen];
		else if (result.length != resLen)
			throw new IllegalArgumentException(
					JGitText.get().resultLengthIncorrect);

		int resultPtr = 0;
		while (deltaPtr < delta.length) {
			final int cmd = delta[deltaPtr++] & 0xff;
			if ((cmd & 0x80) != 0) {
				int copyOffset = 0;
				if ((cmd & 0x01) != 0)
					copyOffset = delta[deltaPtr++] & 0xff;
				if ((cmd & 0x02) != 0)
					copyOffset |= (delta[deltaPtr++] & 0xff) << 8;
				if ((cmd & 0x04) != 0)
					copyOffset |= (delta[deltaPtr++] & 0xff) << 16;
				if ((cmd & 0x08) != 0)
					copyOffset |= (delta[deltaPtr++] & 0xff) << 24;

				int copySize = 0;
				if ((cmd & 0x10) != 0)
					copySize = delta[deltaPtr++] & 0xff;
				if ((cmd & 0x20) != 0)
					copySize |= (delta[deltaPtr++] & 0xff) << 8;
				if ((cmd & 0x40) != 0)
					copySize |= (delta[deltaPtr++] & 0xff) << 16;
				if (copySize == 0)
					copySize = 0x10000;

				System.arraycopy(base
				resultPtr += copySize;
			} else if (cmd != 0) {
				System.arraycopy(delta
				deltaPtr += cmd;
				resultPtr += cmd;
			} else {
				throw new IllegalArgumentException(
						JGitText.get().unsupportedCommand0);
			}
		}

		return result;
	}

	public static String format(byte[] delta) {
		return format(delta
	}

	public static String format(byte[] delta
		StringBuilder r = new StringBuilder();
		int deltaPtr = 0;

		long baseLen = 0;
		int c
		do {
			c = delta[deltaPtr++] & 0xff;
			baseLen |= ((long) (c & 0x7f)) << shift;
			shift += 7;
		} while ((c & 0x80) != 0);

		long resLen = 0;
		shift = 0;
		do {
			c = delta[deltaPtr++] & 0xff;
			resLen |= ((long) (c & 0x7f)) << shift;
			shift += 7;
		} while ((c & 0x80) != 0);

		if (includeHeader) {
			r.append(baseLen);
			r.append(resLen);
		}

		while (deltaPtr < delta.length) {
			final int cmd = delta[deltaPtr++] & 0xff;
			if ((cmd & 0x80) != 0) {
				int copyOffset = 0;
				if ((cmd & 0x01) != 0)
					copyOffset = delta[deltaPtr++] & 0xff;
				if ((cmd & 0x02) != 0)
					copyOffset |= (delta[deltaPtr++] & 0xff) << 8;
				if ((cmd & 0x04) != 0)
					copyOffset |= (delta[deltaPtr++] & 0xff) << 16;
				if ((cmd & 0x08) != 0)
					copyOffset |= (delta[deltaPtr++] & 0xff) << 24;

				int copySize = 0;
				if ((cmd & 0x10) != 0)
					copySize = delta[deltaPtr++] & 0xff;
				if ((cmd & 0x20) != 0)
					copySize |= (delta[deltaPtr++] & 0xff) << 8;
				if ((cmd & 0x40) != 0)
					copySize |= (delta[deltaPtr++] & 0xff) << 16;
				if (copySize == 0)
					copySize = 0x10000;

				r.append(copyOffset);
				r.append("
				r.append(copySize);
			} else if (cmd != 0) {
				r.append(QuotedString.GIT_PATH.quote(RawParseUtils.decode(
						delta
				deltaPtr += cmd;
			} else {
				throw new IllegalArgumentException(
						JGitText.get().unsupportedCommand0);
			}
		}

		return r.toString();
	}
}
