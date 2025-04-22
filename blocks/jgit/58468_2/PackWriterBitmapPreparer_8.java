		if (!reuseCommits.isEmpty())
			for (BitmapBuilderEntry entry : tipCommitBitmaps)
				entry.getBuilder().andNot(reuse);

		List<BitmapBuilderEntry> orderedTipCommitBitmaps = new ArrayList<>(
				tipCommitBitmaps.size());
		while (!tipCommitBitmaps.isEmpty()) {
			Collections.sort(tipCommitBitmaps
			BitmapBuilderEntry largest = tipCommitBitmaps.remove(0);
			orderedTipCommitBitmaps.add(largest);
