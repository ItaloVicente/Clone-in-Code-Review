package org.eclipse.jgit.internal.storage.file;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class PackObjectSizeIndexLoader {

	public static PackObjectSizeIndex load(InputStream in) throws IOException {
		byte[] header = in.readNBytes(4);
		if (!Arrays.equals(header
		}

		int version = in.readNBytes(1)[0];
		if (version != 1) {
		}
		return PackObjectSizeIndexV1.parse(in);
	}
}
