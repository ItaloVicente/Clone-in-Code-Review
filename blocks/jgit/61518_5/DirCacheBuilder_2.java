	private static byte[] normalizePrefix(byte[] pathPrefix) {
		if (pathPrefix == null) {
			return new byte[0];
		}

		int n = pathPrefix.length;
		if (n == 0 || pathPrefix[n - 1] == '/') {
			return pathPrefix;
		}

		byte[] tmp = new byte[n + 1];
		System.arraycopy(pathPrefix
		tmp[n] = '/';
		return tmp;
	}

	private static DirCacheEntry toEntry(byte[] prefix
			RawTreeIterator i) {
		int prefixLen = prefix.length;
		int pathLen = i.getPathLength();
		byte[] path = new byte[prefixLen + pathLen];
		if (prefixLen > 0) {
			System.arraycopy(prefix
		}
		i.copyPath(path
