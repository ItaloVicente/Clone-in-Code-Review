			}

			if (buf.length() > 0) {
				temp.addElement(buf.toString());
				words[i].bound += buf.length();
			}

			words[i].fragments = new String[temp.size()];
			temp.copyInto(words[i].fragments);
		} // end of for loop
	}

	protected int posIn(String text, int start, int end) {// no wild card in pattern
		int max = end - fLength;

		if (!fIgnoreCase) {
			int i = text.indexOf(fPattern, start);
			if (i == -1 || i > max) {
