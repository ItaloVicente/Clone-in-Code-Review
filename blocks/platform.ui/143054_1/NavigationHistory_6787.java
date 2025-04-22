		}
		size = list.size();
		for (int i = 0; i < size; i++) {
			NavigationHistoryEntry entry = (NavigationHistoryEntry) list.get(i);
			IMemento childMem = memento.createChild(IWorkbenchConstants.TAG_ITEM);
			if (entry == cEntry) {
