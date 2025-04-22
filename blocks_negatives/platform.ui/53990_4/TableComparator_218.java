		Arrays.sort(elements, start, end, new Comparator() {
			@Override
			public int compare(Object a, Object b) {
				return TableComparator.this.compare(viewer, a, b);
			}
		});
