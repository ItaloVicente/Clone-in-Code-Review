	public static <E> ListDiff<E> createListDiff(ListDiffEntry<E> difference1,
			ListDiffEntry<E> difference2) {
		List<ListDiffEntry<E>> differences = new ArrayList<>(2);
		differences.add(difference1);
		differences.add(difference2);
		return createListDiff(differences);
