        if (noOpenProjects()) {
            setErrorMessage(IDEWorkbenchMessages.WizardImportPage_noOpenProjects);
            return false;
        }
        return super.determinePageCompletion();
    }

    /**
     * Returns whether or not the passed workspace has any
     * open projects
     * @return boolean
     */
    private boolean noOpenProjects() {
