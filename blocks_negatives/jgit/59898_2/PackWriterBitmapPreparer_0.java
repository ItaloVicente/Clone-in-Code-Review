		if (!reuseCommits.isEmpty()) {
			for (BitmapBuilderEntry entry : tipCommitBitmaps) {
				entry.getBuilder().andNot(reuse);
			}
		}

