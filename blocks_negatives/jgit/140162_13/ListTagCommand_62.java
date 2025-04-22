		Collections.sort(tags, new Comparator<Ref>() {
			@Override
			public int compare(Ref o1, Ref o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
