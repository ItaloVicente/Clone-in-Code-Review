
package org.eclipse.jgit.util;

import static org.eclipse.jgit.lib.FileMode.TYPE_MASK;
import static org.eclipse.jgit.lib.FileMode.TYPE_TREE;

public class Paths {
	public static String stripTrailingSeparator(String path) {
		if (path == null || path.isEmpty()) {
			return path;
		}

		int i = path.length();
		if (path.charAt(path.length() - 1) != '/') {
			return path;
		}
		do {
			i--;
		} while (path.charAt(i - 1) == '/');
		return path.substring(0
	}

	public static int compare(byte[] aPath
			byte[] bPath
		int cmp = coreCompare(
				aPath
				bPath
		if (cmp == 0) {
			cmp = lastPathChar(aMode) - lastPathChar(bMode);
		}
		return cmp;
	}

	public static int compareSameName(
			byte[] aPath
			byte[] bPath
		return coreCompare(
				aPath
				bPath
	}

	private static int coreCompare(
			byte[] aPath
			byte[] bPath
		while (aPos < aEnd && bPos < bEnd) {
			int cmp = (aPath[aPos++] & 0xff) - (bPath[bPos++] & 0xff);
			if (cmp != 0) {
				return cmp;
			}
		}
		if (aPos < aEnd) {
			return (aPath[aPos] & 0xff) - lastPathChar(bMode);
		}
		if (bPos < bEnd) {
			return lastPathChar(aMode) - (bPath[bPos] & 0xff);
		}
		return 0;
	}

	private static int lastPathChar(int mode) {
		if ((mode & TYPE_MASK) == TYPE_TREE) {
			return '/';
		}
		return 0;
	}

	private Paths() {
	}
}
