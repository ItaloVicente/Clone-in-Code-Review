		if (!reuseCommits.isEmpty())
			for (BitmapBuilder bitmap : tipCommitBitmaps)
				bitmap.andNot(reuse);

		List<BitmapBuilder> orderedTipCommitBitmaps = new ArrayList<BitmapBuilder>(
				tipCommitBitmaps.size());
		while (!tipCommitBitmaps.isEmpty()) {
			Collections.sort(tipCommitBitmaps
			BitmapBuilder largest = tipCommitBitmaps.remove(0);
			orderedTipCommitBitmaps.add(largest);
