			return;
		}

		Map categoryCache = new HashMap(categories.length * 2 + 1);
		for (int i = 0; i < categories.length; i++) {
			categories[i].removeAllEntries();
			categoryCache.put(categories[i].getCategoryName(), categories[i]);
