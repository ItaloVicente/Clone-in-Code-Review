		SimilarityIndex src = new SimilarityIndex();
		src.hash(reader.open(OLD, d));
		src.sort();

		SimilarityIndex dst = new SimilarityIndex();
		dst.hash(reader.open(NEW, d));
		dst.sort();
		return src.score(dst, 100);
