	private FileDiff getFirstInterestingElement() {
		FileDiff[] diffs = diff;
		if (diffs != null) {
			for (FileDiff d : diffs) {
				if (d.isMarked(INTERESTING_MARK_TREE_FILTER_INDEX)) {
					return d;
				}
			}
		}
		return null;
	}

	private void cancel() {
		if (loader != null) {
			loader.cancel();
			loader = null;
		}
	}

