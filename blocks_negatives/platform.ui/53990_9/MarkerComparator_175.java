		return new Comparator<MarkerItem>() {
			@Override
			public int compare(MarkerItem o1, MarkerItem o2) {
				return compareFields(o1, o2);
			}
		};
