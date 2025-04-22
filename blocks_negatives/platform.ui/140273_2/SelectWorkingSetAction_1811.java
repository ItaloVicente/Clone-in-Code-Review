        if (dialog.open() == Window.OK) {
            IWorkingSet[] result = dialog.getSelection();
            if (result != null && result.length > 0) {
                actionGroup.setWorkingSet(result[0]);
                manager.addRecentWorkingSet(result[0]);
            } else {
                actionGroup.setWorkingSet(null);
            }
        } else {
