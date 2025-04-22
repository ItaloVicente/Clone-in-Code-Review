
package org.eclipse.jgit.util;

public final class RawCharSequence implements CharSequence {
	public static final RawCharSequence EMPTY = new RawCharSequence(null

	final byte[] buffer;

	final int startPtr;

	final int endPtr;

	public RawCharSequence(byte[] buf
		buffer = buf;
		startPtr = start;
		endPtr = end;
	}

	@Override
	public char charAt(int index) {
		return (char) (buffer[startPtr + index] & 0xff);
	}

	@Override
	public int length() {
		return endPtr - startPtr;
	}

	@Override
	public CharSequence subSequence(int start
		return new RawCharSequence(buffer
	}

	@Override
	public String toString() {
		final int n = length();
		final StringBuilder b = new StringBuilder(n);
		for (int i = 0; i < n; i++)
			b.append(charAt(i));
		return b.toString();
	}
}
