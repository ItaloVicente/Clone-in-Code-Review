        	comparator.setTopPriority(column);
            updateSortState();
            viewer.refresh();
            IDialogSettings workbenchSettings = getPlugin().getDialogSettings();
            IDialogSettings settings = workbenchSettings
            if (settings == null) {
