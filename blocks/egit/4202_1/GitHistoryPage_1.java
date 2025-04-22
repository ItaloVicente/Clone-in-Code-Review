			if (store
					.getBoolean(UIPreferences.RESOURCEHISTORY_SHOW_NOTES))
				markStartAllRefs(Constants.R_NOTES);
			else {
				markUninteresting(Constants.R_NOTES);
			}

