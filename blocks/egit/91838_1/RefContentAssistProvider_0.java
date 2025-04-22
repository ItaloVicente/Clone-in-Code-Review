	private void sortByName(List<Ref> refs) {
		Collections.sort(refs, new Comparator<Ref>() {

			@Override
			public int compare(Ref ref1, Ref ref2) {
				return ref1.getName().compareToIgnoreCase(ref2.getName());
			}
		});
	}

