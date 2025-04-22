		spellCheckingListener = event -> {
			if (SpellingService.PREFERENCE_SPELLING_ENABLED
					.equals(event.getProperty())) {
				boolean isEnabled = EditorsUI.getPreferenceStore().getBoolean(
						SpellingService.PREFERENCE_SPELLING_ENABLED);
				updateSpellChecking(isEnabled);
			}
		};
		EditorsUI.getPreferenceStore()
				.addPropertyChangeListener(spellCheckingListener);
		this.getTextWidget().addDisposeListener(event -> {
			if (hyperlinkChangeListener != null) {
				EditorsUI.getPreferenceStore()
						.removePropertyChangeListener(hyperlinkChangeListener);
			}
			EditorsUI.getPreferenceStore()
					.removePropertyChangeListener(spellCheckingListener);
		});
	}

	private void updateSpellChecking(boolean isEnabled) {
		this.unconfigure();
		this.configure(configuration);
		if (!isEnabled) {
			SpellingProblem.removeAll(this, null);
		}
