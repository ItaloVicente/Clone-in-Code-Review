	int getMemoryUsedByTableForTest() {
		int r = 0;
		for (int i = 0; i < table.length; i++) {
			for (Entry e = table[i]; e != null; e = e.tableNext) {
				r += e.data.length;
			}
		}
		return r;
	}
