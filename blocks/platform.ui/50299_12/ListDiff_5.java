	public abstract ListDiffEntry<E>[] getDifferences();

	@SuppressWarnings("unchecked")
	public final List<ListDiffEntry<E>> getDifferencesAsList() {
		List<ListDiffEntry<E>> result = new ArrayList<ListDiffEntry<E>>();
		if (getDifferences() != null) {
			for (ListDiffEntry<?> entry : getDifferences()) {
				result.add((ListDiffEntry<E>) entry);
			}
		}
		return result;
	}
