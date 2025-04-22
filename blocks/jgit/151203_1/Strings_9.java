		}
		int backSlash = pattern.indexOf('\\');
		if (backSlash >= 0) {
			int nextIdx = backSlash + 1;
			if (pattern.length() == nextIdx) {
				return false;
			}
			char nextChar = pattern.charAt(nextIdx);
			if (escapedByBackslash(nextChar)) {
				return true;
