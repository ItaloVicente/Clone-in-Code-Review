
package org.eclipse.jgit.internal.storage.pack;

public class PackExt {
	private static volatile PackExt[] VALUES = new PackExt[] {};






	public static PackExt[] values() {
		return VALUES;
	}

	public synchronized static PackExt newPackExt(String ext) {
		PackExt[] dst = new PackExt[VALUES.length + 1];
		for (int i = 0; i < VALUES.length; i++) {
			PackExt packExt = VALUES[i];
			if (packExt.getExtension().equals(ext))
				return packExt;
			dst[i] = packExt;
		}
		if (VALUES.length >= 32)
			throw new IllegalStateException(

		PackExt value = new PackExt(ext
		dst[VALUES.length] = value;
		VALUES = dst;
		return value;
	}

	private final String ext;

	private final int pos;

	private PackExt(String ext
		this.ext = ext;
		this.pos = pos;
	}

	public String getExtension() {
		return ext;
	}

	public int getPosition() {
		return pos;
	}

	public int getBit() {
		return 1 << getPosition();
	}

	@Override
	public String toString() {
		return String.format("PackExt[%s
				Integer.toHexString(getBit()));
	}
}
