		String[] list = getSortedForbiddenFileNames();
		forbidden = new byte[list.length][];
		for (int i = 0; i < list.length; ++i)
			forbidden[i] = Constants.encodeASCII(list[i]);
	}

	static String[] getSortedForbiddenFileNames() {
