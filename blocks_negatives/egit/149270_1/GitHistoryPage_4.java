			if (store
					.getBoolean(UIPreferences.RESOURCEHISTORY_SHOW_ADDITIONAL_REFS))
				walk.addAdditionalRefs(db.getRefDatabase()
						.getAdditionalRefs());
			walk.addAdditionalRefs(db.getRefDatabase()
					.getRefsByPrefix(Constants.R_NOTES));
