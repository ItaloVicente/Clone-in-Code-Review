		int totCommits = result.commitsByOldest.length - result.commitStartPos;
		BlockList<BitmapCommit> selections = new BlockList<BitmapCommit>(
				totCommits / minCommits + 1);
		for (BitmapCommit reuse : result.reuse)
			selections.add(reuse);

		if (totCommits == 0) {
			for (AnyObjectId id : result.peeledWant)
				selections.add(new BitmapCommit(id
			return selections;
		}
