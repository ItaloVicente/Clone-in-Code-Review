        if (toolItem != null && !toolItem.isDisposed()) {
            toolItem
                    .setSelection(workbenchPage.getPerspective() == perspective);
            if (apiPreferenceStore
                    .getBoolean(IWorkbenchPreferenceConstants.SHOW_TEXT_ON_PERSPECTIVE_BAR)) {
                if (apiPreferenceStore.getString(
                        IWorkbenchPreferenceConstants.DOCK_PERSPECTIVE_BAR)
                        .equals(IWorkbenchPreferenceConstants.TOP_LEFT)) {
