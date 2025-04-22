			final List<PreviousCommit> previousList;
			try {
				previousList = findPreviousCommits();
			} catch (IOException e) {
				Activator.handleError(e.getMessage(), e, true);
				return;
			}
			final AtomicReference<PreviousCommit> previous = new AtomicReference<PreviousCommit>();
			if (previousList.size() == 0) {
