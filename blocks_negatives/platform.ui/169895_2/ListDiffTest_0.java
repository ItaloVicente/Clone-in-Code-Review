	private ListDiff createListDiff(ListDiffEntry difference) {
		return createListDiff(new ListDiffEntry[] { difference });
	}

	private ListDiff createListDiff(ListDiffEntry first, ListDiffEntry second) {
		return createListDiff(new ListDiffEntry[] { first, second });
	}

	private ListDiff createListDiff(ListDiffEntry[] differences) {
