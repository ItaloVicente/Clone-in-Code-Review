	private void buildIndex() {
		first = 0;
		index = new Entry[inQueue / 100 + 1];
		Entry q = head;
		int qi = 0
		while (q != null) {
			if (++qi % 100 == 0)
				index[ii++] = q;
			q = q.next;
		}
		last = ii - 1;
	}

