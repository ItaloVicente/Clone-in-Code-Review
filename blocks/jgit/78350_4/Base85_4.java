
package org.eclipse.jgit.internal.storage.zlib;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.InflaterOutputStream;

public class ZlibSupport {

	public static byte[] inflate(byte[] bytes
			throws DataFormatException {

		try (ByteArrayOutputStream baos = new ByteArrayOutputStream(expectedSize);
			 InflaterOutputStream ios = new InflaterOutputStream(baos)) {
			ios.write(bytes
			return baos.toByteArray();
		} catch (IOException e) {
			throw new DataFormatException();
		}
	}

}
