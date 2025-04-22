		if (tables.length == 0) {
			return 0;
		}
		long maxUpdateIndex = tables[tables.length - 1].maxUpdateIndex();
		for (int i = tables.length - 2; i >= 0; i--) {
			if (maxUpdateIndex < tables[i].maxUpdateIndex()) {
				maxUpdateIndex = tables[i].maxUpdateIndex();
			}
		}
		return maxUpdateIndex;
