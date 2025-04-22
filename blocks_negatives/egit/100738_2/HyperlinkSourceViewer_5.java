		};
		EditorsUI.getPreferenceStore()
				.addPropertyChangeListener(spellCheckingListener);
		this.getTextWidget().addDisposeListener(event -> {
			if (hyperlinkChangeListener != null) {
				EditorsUI.getPreferenceStore()
						.removePropertyChangeListener(hyperlinkChangeListener);
