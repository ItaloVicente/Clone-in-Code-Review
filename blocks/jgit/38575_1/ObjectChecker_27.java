	private static boolean isGit(byte[] buf
		return toLower(buf[p]) == 'g'
				&& toLower(buf[p + 1]) == 'i'
				&& toLower(buf[p + 2]) == 't';
	}

	private static boolean isGitTilde1(byte[] buf
		if (end - p != 5)
			return false;
		return toLower(buf[p]) == 'g' && toLower(buf[p + 1]) == 'i'
				&& toLower(buf[p + 2]) == 't' && buf[p + 3] == '~'
				&& buf[p + 4] == '1';
	}

	private static boolean isNormalizedGit(byte[] raw
		if (isGit(raw
			int dots = 0;
			boolean space = false;
			int p = end - 1;
			for (; (ptr + 2) < p; p--) {
				if (raw[p] == '.')
					dots++;
				else if (raw[p] == ' ')
					space = true;
				else
					break;
			}
			return p == ptr + 2 && (dots == 1 || space);
		}
		return false;
