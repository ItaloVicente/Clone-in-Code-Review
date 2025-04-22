        setResult(chosenContainerPathList);
        super.okPressed();
    }

    /**
     * Sets the validator to use.
     *
     * @param validator A selection validator
     */
    public void setValidator(ISelectionValidator validator) {
        this.validator = validator;
    }

    /**
     * Set whether or not closed projects should be shown
     * in the selection dialog.
     *
     * @param show Whether or not to show closed projects.
     */
    public void showClosedProjects(boolean show) {
        this.showClosedProjects = show;
    }
