	private void checkNameConflicts() {
		int end = entryCnt - 1;
		for (int eIdx = 0; eIdx < end; eIdx++) {
			DirCacheEntry e = entries[eIdx];
			if (e.getStage() != 0) {
				continue;
			}

			byte[] ePath = e.path;
			int prefixLen = lastSlash(ePath) + 1;

			for (int nIdx = eIdx + 1; nIdx < entryCnt; nIdx++) {
				DirCacheEntry n = entries[nIdx];
				if (n.getStage() != 0) {
					continue;
				}

				byte[] nPath = n.path;
				if (!startsWith(ePath
					break;
				}

				int s = nextSlash(nPath
				int m = s < nPath.length ? TYPE_TREE : n.getRawMode();
				int cmp = pathCompare(
						ePath
						nPath
				if (cmp < 0) {
					break;
				} else if (cmp == 0) {
					throw new DirCacheNameConflictException(
							e.getPathString()
							n.getPathString());
				}
			}
		}
	}

	private static int lastSlash(byte[] path) {
		for (int i = path.length - 1; i >= 0; i--) {
			if (path[i] == '/') {
				return i;
			}
		}
		return -1;
	}

	private static int nextSlash(byte[] b
		final int n = b.length;
		for (; p < n; p++) {
			if (b[p] == '/') {
				return p;
			}
		}
		return n;
	}

	private static boolean startsWith(byte[] a
		if (b.length < n) {
			return false;
		}
		for (n--; n >= 0; n--) {
			if (a[n] != b[n]) {
				return false;
			}
		}
		return true;
	}

	private static int pathCompare(byte[] aPath
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
		return TREE.equals(mode) ? '/' : '\0';
	}

