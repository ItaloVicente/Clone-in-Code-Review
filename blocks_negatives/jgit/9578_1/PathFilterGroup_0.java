		private static int pathPrefixSortCompare(byte[] p1, byte[] p2,
				boolean justMatch) {
			int ci = 0;
			while (ci < p1.length && ci < p2.length) {
				int c1 = p1[ci];
				int c2 = p2[ci];
				if (c1 == '/')
					c1 = 0;
				if (c2 == '/')
					c2 = 0;
				int cmp = c1 - c2;
				if (cmp != 0)
					return cmp;
				++ci;
			}
			if (ci < p1.length) {
				int c1 = p1[ci];
				if (c1 == '/')
					if (justMatch)
						return 0;
				return 1;
			}
			if (ci < p2.length) {
				int c2 = p2[ci];
				if (c2 == '/')
					return 0;
				return -1;
			}
			return 0;
		}

		private static final Comparator<PathFilter> PATH_PREFIX_SORT = new Comparator<PathFilter>() {
