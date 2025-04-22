		IPreferenceStore store = getPreferenceStore();

		store.setValue(IPreferenceConstants.STICKY_CYCLE, stickyCycleButton.getSelection());
		store.setValue(IPreferenceConstants.OPEN_ON_SINGLE_CLICK, openOnSingleClick);
		store.setValue(IPreferenceConstants.SELECT_ON_HOVER, selectOnHover);
		store.setValue(IPreferenceConstants.OPEN_AFTER_DELAY, openAfterDelay);
		store.setValue(IPreferenceConstants.RUN_IN_BACKGROUND, showUserDialogButton.getSelection());
