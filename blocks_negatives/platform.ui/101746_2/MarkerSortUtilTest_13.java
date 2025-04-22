		Comparator comparator=new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				return ((MockMarkerEntry)o1).name.compareTo(((MockMarkerEntry)o2).name);
			}
		};
