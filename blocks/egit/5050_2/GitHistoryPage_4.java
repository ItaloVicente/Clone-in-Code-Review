			if (store
					.getBoolean(UIPreferences.RESOURCEHISTORY_SHOW_ADDITIONAL_REFS))
				currentWalk.addAdditionalRefs(db.getRefDatabase()
						.getAdditionalRefs());
			currentWalk.addAdditionalRefs(db.getRefDatabase()
					.getRefs(Constants.R_NOTES).values());
