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
			}
			kind = Differencer.LEFT + Differencer.DELETION;
			right = rightItem.get();
		} else if (rightIter == null) {
			kind = Differencer.LEFT + Differencer.ADDITION;
			left = leftItem.get();
		} else if (leftIter.getEntryObjectId()
				.equals(rightIter.getEntryObjectId())) {
			return null;
		} else {
			kind = Differencer.CHANGE;
			left = leftItem.get();
			right = rightItem.get();
		}
		return new MergeDiffNode(kind, null, left, right);
	}

