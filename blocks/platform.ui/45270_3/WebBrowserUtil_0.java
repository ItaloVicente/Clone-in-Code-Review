	private static String[] tokenize(String string) {
		StringTokenizer tokenizer = new StringTokenizer(string);
		String[] tokens = new String[tokenizer.countTokens()];
		for (int i = 0; tokenizer.hasMoreTokens(); i++)
			tokens[i] = tokenizer.nextToken();
		return tokens;
