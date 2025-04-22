		final StyleRange[] arr = new StyleRange[styles.size()];
		styles.toArray(arr);
		Arrays.sort(arr, new Comparator<StyleRange>() {
			@Override
			public int compare(StyleRange o1, StyleRange o2) {
				return o1.start - o2.start;
			}
		});
