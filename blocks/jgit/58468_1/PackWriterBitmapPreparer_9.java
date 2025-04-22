		if (!isActiveBranch) {
			if (idxFromStart == 0)
				return 0;
			if (idxFromStart < recentCommitCount)
				return recentCommitSpan;
			return distantCommitSpan;
		}

		if (idxFromStart <= contiguousCommitCount)
