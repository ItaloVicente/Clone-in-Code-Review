		@SuppressWarnings("unchecked")
		List<QuickAccessEntry>[] entries = new List[providers.length];
		int maxCount = maxNumberOfItemsInTable;
		int[] indexPerProvider = new int[providers.length];
		int countPerProvider = Math.min(maxCount / 4, INITIAL_COUNT_PER_PROVIDER);
		int prevPick = 0;
		int countTotal = 0;
		boolean perfectMatchAdded = true;
		if (perfectMatch != null) {
			maxCount--;
			perfectMatchAdded = false;
		}
		boolean done;
