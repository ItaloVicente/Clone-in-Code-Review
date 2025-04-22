			if (Activator.getDefault().getPreferenceStore().getBoolean(
					UIPreferences.STAGING_VIEW_COMPARE_MODE)) {
				runCommand(ActionCommands.COMPARE_WITH_INDEX_ACTION, selection);
			} else {
				openSelectionInEditor(selection);
			}

