			ObjectReader reader = repo.newObjectReader();
			try {
				SimilarityRenameDetector d;

				d = new SimilarityRenameDetector(reader, deleted, added);
				d.setRenameScore(getRenameScore());
				d.compute(pm);
				deleted = d.getLeftOverSources();
				added = d.getLeftOverDestinations();
				entries.addAll(d.getMatches());
			} finally {
				reader.release();
			}
