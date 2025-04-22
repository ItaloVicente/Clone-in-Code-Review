		return new Comparator<MarkerItem>() {
			@Override
			public int compare(MarkerItem o1, MarkerItem o2) {
				return compareCategory(o1, o2);
			}
		};
