    /*
     * @see WizardDataTransferPage.determinePageCompletion.
     */
    protected boolean determinePageCompletion() {
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
        IProject[] projects = IDEWorkbenchPlugin.getPluginWorkspace().getRoot()
                .getProjects();
        for (int i = 0; i < projects.length; i++) {
            if (projects[i].isOpen()) {
				return false;
			}
        }
        return true;
    }
