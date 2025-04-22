		Arrays.sort(categories, new Comparator() {
			@Override
			public int compare(Object a, Object b) {
				return PropertySheetSorter.this.compareCategories(
						((PropertySheetCategory) a).getCategoryName(),
						((PropertySheetCategory) b).getCategoryName());
			}
		});
