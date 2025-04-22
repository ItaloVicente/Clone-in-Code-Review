	public static <E> ListDiff<E> createListDiff(final List<ListDiffEntry<E>> differences) {
		final ListDiffEntry<E>[] differencesArray = differences.toArray(new ListDiffEntry[differences.size()]);
		return new ListDiff<E>() {
			@Override
			public ListDiffEntry<E>[] getDifferences() {
				return differencesArray;
			}
		};
	}

