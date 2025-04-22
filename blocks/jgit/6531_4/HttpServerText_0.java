
package org.eclipse.jgit.http.server;

import static org.eclipse.jgit.http.server.ServletUtils.isChunked;

import javax.servlet.http.HttpServletRequest;

public class ClientVersionUtil {
	private static final int[] v1_7_5 = { 1
	private static final int[] v1_7_8_6 = { 1
	private static final int[] v1_7_9 = { 1

	public static int[] invalidVersion() {
		return new int[] { Integer.MAX_VALUE };
	}

	public static int[] parseVersion(String version) {
		if (version != null && version.startsWith("git/"))
			return splitVersion(version.substring("git/".length()));
		return invalidVersion();
	}

	private static int[] splitVersion(String versionString) {
		char[] str = versionString.toCharArray();
		int[] ver = new int[4];
		int end = 0;
		int acc = 0;
		for (int i = 0; i < str.length; i++) {
			char c = str[i];
			if ('0' <= c && c <= '9') {
				acc *= 10;
				acc += c - '0';
			} else if (c == '.') {
				if (end == ver.length)
					ver = grow(ver);
				ver[end++] = acc;
				acc = 0;
			} else if (c == 'g' && 0 < i && str[i - 1] == '.' && 0 < end) {
				ver[end - 1] = 0;
				acc = 0;
				break;
			} else if (c == '-' && (i + 2) < str.length
					&& str[i + 1] == 'r' && str[i + 2] == 'c') {
				if (acc > 0)
					acc--;
				break;
			} else
				break;
		}
		if (acc != 0) {
			if (end == ver.length)
				ver = grow(ver);
			ver[end++] = acc;
		} else {
			while (0 < end && ver[end - 1] == 0)
				end--;
		}
		if (end < ver.length) {
			int[] n = new int[end];
			System.arraycopy(ver
			ver = n;
		}
		return ver;
	}

	private static int[] grow(int[] tmp) {
		int[] n = new int[tmp.length + 1];
		System.arraycopy(tmp
		return n;
	}

	public static int compare(int[] a
		for (int i = 0; i < a.length && i < b.length; i++) {
			int cmp = a[i] - b[i];
			if (cmp != 0)
				return cmp;
		}
		return a.length - b.length;
	}

	public static String toString(int[] ver) {
		StringBuilder b = new StringBuilder();
		for (int v : ver) {
			if (b.length() > 0)
				b.append('.');
			b.append(v);
		}
		return b.toString();
	}

	public static boolean hasPushStatusBug(int[] version) {
		int cmp = compare(version
		if (cmp < 0)
		else if (cmp == 0)

		if (compare(version
	}

	public static boolean hasChunkedEncodingRequestBug(
			int[] version
		return compare(version
	}

	private ClientVersionUtil() {
	}
}
