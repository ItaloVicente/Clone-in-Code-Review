		private static int compare(byte[] a, byte[] b) {
			int i = 0;
			while (i < a.length && i < b.length) {
				int ba = a[i] & 0xFF;
				int bb = b[i] & 0xFF;
				int cmp = ba - bb;
				if (cmp != 0)
					return cmp;
				++i;
			}
			return a.length - b.length;
