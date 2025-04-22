		return text;
	}

	private void updateQuickAccessText() {
		if (txtQuickAccess == null || txtQuickAccess.isDisposed()) {
			return;
		}
		updateQuickAccessTriggerSequence();

		if (triggerSequence != null) {
			txtQuickAccess.setMessage(NLS.bind(QuickAccessMessages.QuickAccess_EnterSearch, triggerSequence.format()));
		} else {
			txtQuickAccess.setMessage(QuickAccessMessages.QuickAccess_EnterSearch_Empty);
		}

		GC gc = new GC(txtQuickAccess);
