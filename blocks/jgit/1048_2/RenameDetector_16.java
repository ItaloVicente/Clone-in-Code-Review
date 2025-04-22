	private void findContentRenames(ProgressMonitor pm) throws IOException {
		int cnt = Math.max(added.size()
		if (cnt == 0)
			return;

		if (getRenameLimit() == 0 || cnt <= getRenameLimit()) {
			SimilarityRenameDetector d;

			d = new SimilarityRenameDetector(repo
			d.setRenameScore(getRenameScore());
			d.compute(pm);
			deleted = d.getLeftOverSources();
			added = d.getLeftOverDestinations();
			entries.addAll(d.getMatches());
		} else {
			overRenameLimit = true;
		}
	}

