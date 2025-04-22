	public static SimilarityIndex create(ObjectLoader obj)
			throws MissingObjectException
		SimilarityIndex idx = new SimilarityIndex();
		idx.hash(obj);
		idx.sort();
		return idx;
	}

