		try {
			SimilarityIndex src = new SimilarityIndex();
			src.hash(reader.open(OLD
			src.sort();

			SimilarityIndex dst = new SimilarityIndex();
			dst.hash(reader.open(NEW
			dst.sort();
			return src.score(dst
		} catch (TableFullException tableFull) {
			overRenameLimit = true;
			return breakScore + 1;
		}
