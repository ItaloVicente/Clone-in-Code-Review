        getContainer().showPage(getNextPage());
    }

    /*
     * Update the wizard's message based on the given (selected) wizard element.
     */
    private void updateSelectedNode(WorkbenchWizardElement wizardElement){
        setErrorMessage(null);
        if (wizardElement == null) {
        	updateMessage();
            setSelectedNode(null);
            return;
        }

        setSelectedNode(createWizardNode(wizardElement));
        setMessage(wizardElement.getDescription());
    }

    /*
     * Update the wizard's message based on the currently selected tab
     * and the selected wizard on that tab.
     */
    protected void updateMessage(){
    	TreeViewer viewer = getTreeViewer();
    	if (viewer != null){
    		ISelection selection = viewer.getSelection();
            IStructuredSelection ss = (IStructuredSelection) selection;
            Object sel = ss.getFirstElement();
            if (sel instanceof WorkbenchWizardElement){
               	updateSelectedNode((WorkbenchWizardElement)sel);
            }
            else{
            	setSelectedNode(null);
            }
    	} else {
