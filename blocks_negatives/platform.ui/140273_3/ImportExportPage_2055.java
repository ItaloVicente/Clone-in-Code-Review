    }

    /*
     * Method to call whenever the selection in one of the lists has changed.
     * Updates the wizard's message to relect the description of the currently
     * selected wizard.
     */
    protected void listSelectionChanged(ISelection selection){
        setErrorMessage(null);
        IStructuredSelection ss = (IStructuredSelection) selection;
        Object sel = ss.getFirstElement();
        if (sel instanceof WorkbenchWizardElement){
	        WorkbenchWizardElement currentWizardSelection = (WorkbenchWizardElement) sel;
	        updateSelectedNode(currentWizardSelection);
        } else {
