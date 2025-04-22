
package org.eclipse.jgit.internal.storage.zlib;

import java.util.zip.Inflater;

public class ZlibSupport {

	public static byte[] inflate(byte[] bytes
		try {
			Inflater decompressor = new Inflater();
			decompressor.setInput(bytes
			byte[] result = new byte[expectedSize];
			decompressor.inflate(result);
			decompressor.end();
			return result;
		} catch (Exception e) {
			throw new RuntimeException("Unable to inflate binary data"
		}
	}

}
