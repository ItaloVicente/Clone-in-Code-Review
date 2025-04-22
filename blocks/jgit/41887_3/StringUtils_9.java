
	public static String capitalize(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		return new StringBuffer(strLen)
				.append(Character.toTitleCase(str.charAt(0)))
				.append(str.substring(1)).toString();
	}

