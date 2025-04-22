		Arrays.sort(entries, new Comparator() {
			@Override
			public int compare(Object a, Object b) {
				return PropertySheetSorter.this.compare(
						(IPropertySheetEntry) a, (IPropertySheetEntry) b);
			}
		});
