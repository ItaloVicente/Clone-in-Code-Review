		bucketQueue.clear();
	}

	@Override
	public RevCommit next() {
		return bucketQueue.pop();
