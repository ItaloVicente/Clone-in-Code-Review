		if (i != 0 && !Character.isSpaceChar(attr.charAt(i - 1))) {
			return false;
		}
		int j = i + val.length();
		return (j == attr.length() || (j < attr.length() && Character
				.isSpaceChar(attr.charAt(j))));
