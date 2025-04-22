		try {
			if (store
					.getBoolean(UIPreferences.RESOURCEHISTORY_SHOW_ALL_BRANCHES)) {
				markStartAllRefs(Constants.R_HEADS);
				markStartAllRefs(Constants.R_REMOTES);
			} else
				currentWalk.markStart(currentWalk.parseCommit(headId));
		} catch (IOException e) {
			throw new IllegalStateException(NLS.bind(
					UIText.GitHistoryPage_errorReadingHeadCommit, headId, db
							.getDirectory().getAbsolutePath()), e);
		}
