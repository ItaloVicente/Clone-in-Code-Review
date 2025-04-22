		Collections.sort(entries, new Comparator<ObjectToPack>() {
			@Override
			public int compare(ObjectToPack a, ObjectToPack b) {
				return Long.signum(a.getOffset() - b.getOffset());
			}
		});
