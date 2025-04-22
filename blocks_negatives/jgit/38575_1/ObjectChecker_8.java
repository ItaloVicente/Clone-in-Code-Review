	private boolean isDotGit(byte[] buf, int p) {
		if (windows || macosx)
			return toLower(buf[p]) == 'g'
					&& toLower(buf[p + 1]) == 'i'
					&& toLower(buf[p + 2]) == 't';
		return buf[p] == 'g' && buf[p + 1] == 'i' && buf[p + 2] == 't';
