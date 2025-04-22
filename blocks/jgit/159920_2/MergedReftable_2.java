		if (tables.length == 0) {
			return 0;
		}
		long minUpdateIndex = tables[0].minUpdateIndex();
		for (int i = 0; i < tables.length - 1; i++) {
			if (tables[i].minUpdateIndex() < minUpdateIndex) {
				minUpdateIndex = tables[i].minUpdateIndex();
			}
		}
		return minUpdateIndex;
