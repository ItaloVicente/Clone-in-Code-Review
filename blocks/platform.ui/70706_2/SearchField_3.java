		updateText(text);
		return text;
	}

	private void updateText(Text text) {
		if (text == null || text.isDisposed())
			return;
		String shortcut = getQuickAccessTriggerSequenceFormat();

		text.setMessage(shortcut != null ? NLS.bind(QuickAccessMessages.QuickAccess_EnterSearch, shortcut)
				: QuickAccessMessages.QuickAccess_EnterSearchUnbound);

