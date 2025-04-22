	private void buildIndex() {
		sinceLastIndex = 0;
		first = 0;
		index = new Entry[inQueue / 100 + 1];
		int qi = 0
		for (Entry q = head; q != null; q = q.next) {
			if (++qi % 100 == 0)
				index[ii++] = q;
		}
		last = ii - 1;
	}

