			handleBlockedLanes(index
		}
	}

	private void handleBlockedLanes(final int index
			final PlotCommit<L> commit
		int remaining = nChildren;
		BitSet blockedPositions = new BitSet();
		for (int r = index - 1; r >= 0; r--) {
			final PlotCommit rObj = get(r);
			if (commit.isChild(rObj)) {
				if (--remaining == 0)
					break;
