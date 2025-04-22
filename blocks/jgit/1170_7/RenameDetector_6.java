			SimilarityRenameDetector d;

			d = new SimilarityRenameDetector(reader
			d.setRenameScore(getRenameScore());
			d.compute(pm);
			deleted = d.getLeftOverSources();
			added = d.getLeftOverDestinations();
			entries.addAll(d.getMatches());
