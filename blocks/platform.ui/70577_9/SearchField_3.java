		updateText(text);
		return text;
	}

	private void updateText(Text text) {
		if (text == null || text.isDisposed()) {
			return;
		}

		if (triggerSequence != null) {
			text.setMessage(NLS.bind(QuickAccessMessages.QuickAccess_EnterSearch, triggerSequence.format()));
		} else {
			text.setMessage(QuickAccessMessages.QuickAccess_EnterSearch_Empty);
		}

