	private void buildIndex() {
		int i = 0;
		index.clear();
		Entry q = head;
		while (q != null) {
			if (++i % 100 == 0) {
				index.add(q);
			}
			q = q.next;
		}
	}

