        NavigationHistory history = (NavigationHistory) page
                .getNavigationHistory();
        NavigationHistoryEntry[] entries;
        if (forward) {
            setEnabled(history.canForward());
            entries = history.getForwardEntries();
            if (entries.length > 0) {
                NavigationHistoryEntry entry = entries[0];
                String text = NLS.bind(WorkbenchMessages.NavigationHistoryAction_forward_toolTipName, entry.getHistoryText() );
                setToolTipText(text);
            } else {
                setToolTipText(WorkbenchMessages.NavigationHistoryAction_forward_toolTip);
            }
        } else {
            setEnabled(history.canBackward());
            entries = history.getBackwardEntries();
            if (entries.length > 0) {
                NavigationHistoryEntry entry = entries[0];
                String text = NLS.bind(WorkbenchMessages.NavigationHistoryAction_backward_toolTipName, entry.getHistoryText() );
                setToolTipText(text);
            } else {
                setToolTipText(WorkbenchMessages.NavigationHistoryAction_backward_toolTip);
            }
        }
        recreateMenu = true;
    }
