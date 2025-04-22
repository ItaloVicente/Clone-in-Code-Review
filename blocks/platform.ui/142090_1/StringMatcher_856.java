	}

	protected int posIn(String text, int start, int end) {//no wild card in pattern
		int max = end - fLength;

		if (!fIgnoreCase) {
			int i = text.indexOf(fPattern, start);
			if (i == -1 || i > max) {
