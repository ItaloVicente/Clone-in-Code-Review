	private void initAndParse(int prev
		prevPtr = prev;
		currPtr = curr;
		if (eof()) {
			nextPtr = 0;
		} else {
			parseEntry();
		}
	}

