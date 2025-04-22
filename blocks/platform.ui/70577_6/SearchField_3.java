		updateText(text);
		return text;
	}

	private void updateText(Text text) {
		if (text == null || text.isDisposed())
			return;

		text.setMessage(NLS.bind(QuickAccessMessages.QuickAccess_EnterSearch, getQuickAccessTriggerSequenceFormat()));

