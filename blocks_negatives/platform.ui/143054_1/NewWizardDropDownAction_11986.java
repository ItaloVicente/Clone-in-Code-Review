        if (workbenchWindow == null) {
            return;
        }
        tracker.dispose();
        showDlgAction.dispose();
        newWizardMenu.dispose();
        menuCreator.dispose();
        workbenchWindow = null;
    }

    /**
     * Runs the action, which opens the New wizard dialog.
     */
    @Override
