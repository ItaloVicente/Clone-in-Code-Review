	int getMemoryUsedByTableForTest() {
		int r = 0;
		for (int i = 0; i < table.length; i++) {
			for (Slot e = table[i]; e != null; e = e.tableNext) {
				r += e.size;
			}
		}
		return r;
