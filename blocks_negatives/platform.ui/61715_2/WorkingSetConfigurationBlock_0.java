		Arrays.sort(history, new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				return Collator.getInstance().compare(o1, o2);
			}
		});
