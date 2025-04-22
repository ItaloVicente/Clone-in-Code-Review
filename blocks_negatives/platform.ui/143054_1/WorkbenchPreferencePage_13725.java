        IPreferenceStore store = getPreferenceStore();
        openOnSingleClick = store
                .getBoolean(IPreferenceConstants.OPEN_ON_SINGLE_CLICK);
        selectOnHover = store.getBoolean(IPreferenceConstants.SELECT_ON_HOVER);
        openAfterDelay = store
                .getBoolean(IPreferenceConstants.OPEN_AFTER_DELAY);
    }

    /**
     * The default button has been pressed.
     */
    @Override
