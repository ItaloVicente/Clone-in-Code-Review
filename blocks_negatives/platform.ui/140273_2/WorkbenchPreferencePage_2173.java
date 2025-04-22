        stickyCycleButton.setSelection(store
                .getBoolean(IPreferenceConstants.STICKY_CYCLE));
        openOnSingleClick = store
                .getDefaultBoolean(IPreferenceConstants.OPEN_ON_SINGLE_CLICK);
        selectOnHover = store
                .getDefaultBoolean(IPreferenceConstants.SELECT_ON_HOVER);
        openAfterDelay = store
                .getDefaultBoolean(IPreferenceConstants.OPEN_AFTER_DELAY);
        singleClickButton.setSelection(openOnSingleClick);
        doubleClickButton.setSelection(!openOnSingleClick);
        selectOnHoverButton.setSelection(selectOnHover);
        openAfterDelayButton.setSelection(openAfterDelay);
        selectOnHoverButton.setEnabled(openOnSingleClick);
        openAfterDelayButton.setEnabled(openOnSingleClick);
        stickyCycleButton.setSelection(store
                .getDefaultBoolean(IPreferenceConstants.STICKY_CYCLE));
        showUserDialogButton.setSelection(store.getDefaultBoolean(
                IPreferenceConstants.RUN_IN_BACKGROUND));
        showHeapStatusButton.setSelection(PrefUtil.getAPIPreferenceStore().getDefaultBoolean(
                IWorkbenchPreferenceConstants.SHOW_MEMORY_MONITOR));

        super.performDefaults();
    }

    /**
     * The user has pressed Ok. Store/apply this page's values appropriately.
     */
    @Override
