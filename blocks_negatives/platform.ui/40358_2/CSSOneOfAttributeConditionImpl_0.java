		int i = attr.indexOf(val);
		if (i == -1) {
			return false;
		}
		if (i != 0 && !Character.isSpaceChar(attr.charAt(i - 1))) {
			return false;
