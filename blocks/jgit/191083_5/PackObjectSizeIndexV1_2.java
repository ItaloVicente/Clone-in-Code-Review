package org.eclipse.jgit.internal.storage.file;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.eclipse.jgit.util.NB;

public class PackObjectSizeIndexLoader {

	public static PackObjectSizeIndex load(InputStream in) throws IOException {
		byte[] header = in.readNBytes(4);
		if (!Arrays.equals(header
		}

		int version = NB.decodeInt32(in.readNBytes(4)
		switch (version) {
		case 1:
			return new PackObjectSizeIndexV1(in);
		default:
		}
	}
}
