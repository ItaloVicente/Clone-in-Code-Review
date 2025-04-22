package org.eclipse.jgit.util;

public final class Hex {

	private static final char[] HEX = "0123456789ABCDEF".toCharArray();

	private Hex() {
	}

	public static byte[] decode(String s) {
		int len = s.length();
		byte[] b = new byte[len / 2];

		for (int i = 0; i < len; i += 2) {
			b[i / 2] = (byte) ((Character.digit(s.charAt(i)
		}
		return b;
	}

	public static String toHexString(byte[] b) {
		char[] c = new char[b.length * 2];

		for (int i = 0; i < b.length; i++) {
			int v = b[i] & 0xFF;

			c[i * 2] = HEX[v >>> 4];
			c[i * 2 + 1] = HEX[v & 0x0F];
		}

		return new String(c);
	}
}
