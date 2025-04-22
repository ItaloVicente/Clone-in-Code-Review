	public static String repeat(String input, int size) {
		StringBuilder sb = new StringBuilder(input.length() * size);
		for (int i = 0; i < size; i++)
			sb.append(input);
		String s = sb.toString();
		return s;
	}

