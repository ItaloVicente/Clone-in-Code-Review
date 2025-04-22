
package org.eclipse.jgit.lib;

import java.io.IOException;
import java.io.OutputStream;

public abstract class FileMode {
	public static final int TYPE_MASK = 0170000;

	public static final int TYPE_TREE = 0040000;

	public static final int TYPE_SYMLINK = 0120000;

	public static final int TYPE_FILE = 0100000;

	public static final int TYPE_GITLINK = 0160000;

	public static final int TYPE_MISSING = 0000000;

	public static final FileMode TREE = new FileMode(TYPE_TREE
			Constants.OBJ_TREE) {
		@Override
		public boolean equals(int modeBits) {
			return (modeBits & TYPE_MASK) == TYPE_TREE;
		}
	};

	public static final FileMode SYMLINK = new FileMode(TYPE_SYMLINK
			Constants.OBJ_BLOB) {
		@Override
		public boolean equals(int modeBits) {
			return (modeBits & TYPE_MASK) == TYPE_SYMLINK;
		}
	};

	public static final FileMode REGULAR_FILE = new FileMode(0100644
			Constants.OBJ_BLOB) {
		@Override
		public boolean equals(int modeBits) {
			return (modeBits & TYPE_MASK) == TYPE_FILE && (modeBits & 0111) == 0;
		}
	};

	public static final FileMode EXECUTABLE_FILE = new FileMode(0100755
			Constants.OBJ_BLOB) {
		@Override
		public boolean equals(int modeBits) {
			return (modeBits & TYPE_MASK) == TYPE_FILE && (modeBits & 0111) != 0;
		}
	};

	public static final FileMode GITLINK = new FileMode(TYPE_GITLINK
			Constants.OBJ_COMMIT) {
		@Override
		public boolean equals(int modeBits) {
			return (modeBits & TYPE_MASK) == TYPE_GITLINK;
		}
	};

	public static final FileMode MISSING = new FileMode(TYPE_MISSING
			Constants.OBJ_BAD) {
		@Override
		public boolean equals(int modeBits) {
			return modeBits == 0;
		}
	};

	public static final FileMode fromBits(int bits) {
		switch (bits & TYPE_MASK) {
		case TYPE_MISSING:
			if (bits == 0)
				return MISSING;
			break;
		case TYPE_TREE:
			return TREE;
		case TYPE_FILE:
			if ((bits & 0111) != 0)
				return EXECUTABLE_FILE;
			return REGULAR_FILE;
		case TYPE_SYMLINK:
			return SYMLINK;
		case TYPE_GITLINK:
			return GITLINK;
		}

		return new FileMode(bits
			@Override
			public boolean equals(int a) {
				return bits == a;
			}
		};
	}

	private final byte[] octalBytes;

	private final int modeBits;

	private final int objectType;

	private FileMode(int mode
		modeBits = mode;
		objectType = expType;
		if (mode != 0) {
			final byte[] tmp = new byte[10];
			int p = tmp.length;

			while (mode != 0) {
				tmp[--p] = (byte) ('0' + (mode & 07));
				mode >>= 3;
			}

			octalBytes = new byte[tmp.length - p];
			for (int k = 0; k < octalBytes.length; k++) {
				octalBytes[k] = tmp[p + k];
			}
		} else {
			octalBytes = new byte[] { '0' };
		}
	}

	public abstract boolean equals(int modebits);

	public void copyTo(OutputStream os) throws IOException {
		os.write(octalBytes);
	}

	public void copyTo(byte[] buf
		System.arraycopy(octalBytes
	}

	public int copyToLength() {
		return octalBytes.length;
	}

	public int getObjectType() {
		return objectType;
	}

	@Override
	public String toString() {
		return Integer.toOctalString(modeBits);
	}

	public int getBits() {
		return modeBits;
	}
}
