			if (store.getBoolean(
					UIPreferences.RESOURCEHISTORY_SHOW_ALL_BRANCHES)) {
				markStartAllRefs(db, Constants.R_HEADS);
				markStartAllRefs(db, Constants.R_REMOTES);
				markStartAllRefs(db, Constants.R_TAGS);
			}
