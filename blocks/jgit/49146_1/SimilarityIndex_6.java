	public static SimilarityIndex create(ObjectLoader obj) throws IOException
			TableFullException {
		SimilarityIndex idx = new SimilarityIndex();
		idx.hash(obj);
		idx.sort();
		return idx;
	}

