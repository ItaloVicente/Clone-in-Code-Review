		if (diff == null && walk != null && commit != null)
			try {
				diff = FileDiff.compute(repo, walk, commit, markTreeFilter);
			} catch (IOException err) {
				Activator.handleError(NLS.bind(UIText.FileDiffContentProvider_errorGettingDifference,
						commit.getId()), err, false);
