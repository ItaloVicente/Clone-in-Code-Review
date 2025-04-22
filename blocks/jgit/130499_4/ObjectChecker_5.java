	private boolean isGitmodules(byte[] buf
			throws CorruptObjectException {
		if (end - start < 8) {
			return false;
		}
		return (end - start == dotGitmodules.length
				&& RawParseUtils.match(buf
			|| (macosx && isMacHFSGitmodules(buf
			|| (windows && isNTFSGitmodules(buf
	}

	private boolean matchLowerCase(byte[] b
		if (ptr + src.length > b.length) {
			return false;
		}
		for (int i = 0; i < src.length; i++
			if (toLower(b[ptr]) != src[i]) {
				return false;
			}
		}
		return true;
	}

	private boolean isNTFSGitmodules(byte[] buf
		if (end - start == 11) {
			return matchLowerCase(buf
		}

		if (end - start != 8) {
			return false;
		}

		byte[] gitmod = new byte[]{'g'
		if (matchLowerCase(buf
			start += 6;
		} else {
			byte[] gi7eba = new byte[]{'g'
			for (int i = 0; i < gi7eba.length; i++
				byte c = (byte) toLower(buf[start]);
				if (c == '~') {
					break;
				}
				if (c != gi7eba[i]) {
					return false;
				}
			}
		}

		if (end - start < 2) {
			return false;
		}
		if (buf[start] != '~') {
			return false;
		}
		start++;
		if (buf[start] < '1' || buf[start] > '9') {
			return false;
		}
		start++;
		for (; start != end; start++) {
			if (buf[start] < '0' || buf[start] > '9') {
				return false;
			}
		}
		return true;
	}

