		Collections.sort(entries, new Comparator<PackIndex.MutableEntry>() {
			@Override
			public int compare(MutableEntry o1, MutableEntry o2) {
				return Long.signum(o1.getOffset() - o2.getOffset());
			}
		});
