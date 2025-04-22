			SimilarityRenameDetector d;

			d = new SimilarityRenameDetector(repo, deleted, added);
			d.setRenameScore(getRenameScore());
			d.compute(pm);
			deleted = d.getLeftOverSources();
			added = d.getLeftOverDestinations();
			entries.addAll(d.getMatches());
