		updateText(text);
		return text;
	}

	private void updateText(Text text) {
		text.setToolTipText(QuickAccessMessages.QuickAccess_TooltipDescription);
		text.setMessage(NLS.bind(QuickAccessMessages.QuickAccess_EnterSearch, getQuickAccessTriggerSequenceFormat()));

