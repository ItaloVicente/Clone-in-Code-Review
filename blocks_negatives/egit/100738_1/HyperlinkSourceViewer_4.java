		spellCheckingListener = event -> {
			if (SpellingService.PREFERENCE_SPELLING_ENABLED
					.equals(event.getProperty())) {
				boolean isEnabled = EditorsUI.getPreferenceStore().getBoolean(
						SpellingService.PREFERENCE_SPELLING_ENABLED);
				updateSpellChecking(isEnabled);
