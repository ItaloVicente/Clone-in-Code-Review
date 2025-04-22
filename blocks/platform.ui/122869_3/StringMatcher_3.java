
	public static String[] getWords(String text) {
		List<String> words = new ArrayList<>();
		BreakIterator iter = BreakIterator.getWordInstance();
		iter.setText(text);
		int i = iter.first();
		while (i != java.text.BreakIterator.DONE && i < text.length()) {
			int j = iter.following(i);
			if (j == java.text.BreakIterator.DONE) {
				j = text.length();
			}
			if (Character.isLetterOrDigit(text.charAt(i))) {
				String word = text.substring(i, j);
				words.add(word);
			}
			i = j;
		}
		return words.toArray(new String[words.size()]);
	}

