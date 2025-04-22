        dialog.setTitle(FILTER_TITLE_MESSAGE);
        dialog.setInitialSelections((Object[]) contentProvider.getInitialSelections());
        dialog.open();
        if (dialog.getReturnCode() == Window.OK) {
            Object[] results = dialog.getResult();
            String[] selectedPatterns = new String[results.length];
            System.arraycopy(results, 0, selectedPatterns, 0, results.length);
            filter.setPatterns(selectedPatterns);
            navigator.setFiltersPreference(selectedPatterns);
            Viewer viewer = getViewer();
            viewer.getControl().setRedraw(false);
            viewer.refresh();
            viewer.getControl().setRedraw(true);
        }
    }
