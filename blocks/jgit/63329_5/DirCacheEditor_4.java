		DeleteTree(byte[] path) {
			super(appendSlash(path));
		}

		private static byte[] appendSlash(byte[] path) {
			int n = path.length;
			if (n > 0 && path[n - 1] != '/') {
				byte[] r = new byte[n + 1];
				System.arraycopy(path
				r[n] = '/';
				return r;
			}
			return path;
		}

