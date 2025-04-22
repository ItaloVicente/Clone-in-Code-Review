
package org.eclipse.jgit.junit;

public class TestRng {
	private int next;

	public TestRng(String seed) {
		next = 0;
		for (int i = 0; i < seed.length(); i++)
			next = next * 11 + seed.charAt(i);
	}

	public byte[] nextBytes(int cnt) {
		final byte[] r = new byte[cnt];
		for (int i = 0; i < cnt; i++)
			r[i] = (byte) nextInt();
		return r;
	}

	public int nextInt() {
		next = next * 1103515245 + 12345;
		return next;
	}
}
