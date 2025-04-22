	void write(OutputStream os
			throws IOException {
		final int len = isExtended() ? INFO_LEN_EXTENDED : INFO_LEN;
		if (version != DirCacheVersion.DIRC_VERSION_PATHCOMPRESS) {
			os.write(info
			os.write(path
			int entryLen = len + path.length;
			int expLen = (entryLen + 8) & ~7;
			if (entryLen != expLen)
				os.write(nullpad
		} else {
			int pathCommon = 0;
			int toRemove;
			if (previous != null) {
				int pathLen = Math.min(path.length
				while (pathCommon < pathLen
						&& path[pathCommon] == previous.path[pathCommon]) {
					pathCommon++;
				}
				toRemove = previous.path.length - pathCommon;
			} else {
				toRemove = 0;
			}
			byte[] tmp = new byte[16];
			int n = tmp.length;
			tmp[--n] = (byte) (toRemove & 0x7F);
			while ((toRemove >>>= 7) != 0) {
				tmp[--n] = (byte) (0x80 | (--toRemove & 0x7F));
			}
			os.write(info
			os.write(tmp
			os.write(path
			os.write(0);
		}
