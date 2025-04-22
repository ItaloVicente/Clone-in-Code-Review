        PrefUtil.getAPIPreferenceStore().setValue(IWorkbenchPreferenceConstants.SHOW_MEMORY_MONITOR, showHeapStatusButton.getSelection());
        updateHeapStatus(showHeapStatusButton.getSelection());

        int singleClickMethod = openOnSingleClick ? OpenStrategy.SINGLE_CLICK
                : OpenStrategy.DOUBLE_CLICK;
        if (openOnSingleClick) {
            if (selectOnHover) {
                singleClickMethod |= OpenStrategy.SELECT_ON_HOVER;
            }
            if (openAfterDelay) {
                singleClickMethod |= OpenStrategy.ARROW_KEYS_OPEN;
            }
        }
        OpenStrategy.setOpenMethod(singleClickMethod);

        PrefUtil.savePrefs();
        return true;
    }
