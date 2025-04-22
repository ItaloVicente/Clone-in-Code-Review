		TreeSet<Ref> refs = new TreeSet<>(new Comparator<Ref>() {

			@Override
			public int compare(Ref r1, Ref r2) {
				return r1.getName().compareTo(r2.getName());
			}
		});
