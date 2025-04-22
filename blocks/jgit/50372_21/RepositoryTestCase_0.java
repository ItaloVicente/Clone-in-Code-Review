	public static String standardizePath(String str) {
		str = str.replace('\\'
		if (str.matches("^[A-Z]:.*")) {
			str = "/cygdrive/" + str.substring(0
					+ str.substring(2);
		}
		return str;
	}

