
package org.eclipse.jgit.storage.dht;

import org.eclipse.jgit.util.RawParseUtils;

final class KeyUtils {
	static short parse16(byte[] src
		return (short) RawParseUtils.parseHexInt16(src
	}

	static int parse32(byte[] src
		return RawParseUtils.parseHexInt32(src
	}

	static void format16(byte[] dst
		int o = p + 3;
		while (o >= p && w != 0) {
			dst[o--] = hexbyte[w & 0xf];
			w >>>= 4;
		}
		while (o >= p)
			dst[o--] = '0';
	}

	static void format32(byte[] dst
		int o = p + 7;
		while (o >= p && w != 0) {
			dst[o--] = hexbyte[w & 0xf];
			w >>>= 4;
		}
		while (o >= p)
			dst[o--] = '0';
	}

	private static final byte[] hexbyte = { '0'
			'7'

	private KeyUtils() {
	}
}
