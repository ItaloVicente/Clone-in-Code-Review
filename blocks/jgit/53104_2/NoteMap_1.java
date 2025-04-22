	public boolean isEmpty() {
		if (!(root instanceof LeafBucket)) {
			return false;
		}
		LeafBucket b = (LeafBucket) root;
		return b.size() == 0 && b.nonNotes == null;
	}

