        getButton(OK).setEnabled(enabled);
        if (globalBuildButton != null) {
            globalBuildButton.setEnabled(buildNowButton.getSelection());
        }
        if (projectBuildButton != null) {
            projectBuildButton.setEnabled(buildNowButton.getSelection());
        }
    }

    /**
     * Updates the enablement of the dialog's build selection radio
     * buttons.
     */
    protected void updateBuildRadioEnablement() {
        projectBuildButton.setSelection(!globalBuildButton.getSelection());
    }

    @Override
