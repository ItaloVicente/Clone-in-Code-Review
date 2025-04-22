		return new Comparator<MarkerGroupingEntry>() {
			@Override
			public int compare(MarkerGroupingEntry o1, MarkerGroupingEntry o2) {
				return -(o1.getPriority() - o2.getPriority());
			}
		};
