				boolean reverse, NodePointer startWith) {
		if (index == WHOLE_COLLECTION) {
			return new CollectionChildNodeIterator(
				this,
				test,
				reverse,
				startWith);
		}
		return getValuePointer().childIterator(test, reverse, startWith);
	}
