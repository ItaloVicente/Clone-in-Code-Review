	private MergeDiffNode twoWayDiff(AbstractTreeIterator leftIter,
			AbstractTreeIterator rightIter,
			Supplier<ITypedElement> leftItem,
			Supplier<ITypedElement> rightItem) {
		int kind;
		ITypedElement left = null;
		ITypedElement right = null;
		if (leftIter == null) {
			if (rightIter == null) {
				return null;
