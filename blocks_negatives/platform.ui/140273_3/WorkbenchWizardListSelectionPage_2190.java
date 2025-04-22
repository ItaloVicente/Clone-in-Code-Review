        WorkbenchWizardElement currentWizardSelection = (WorkbenchWizardElement) selection
                .getFirstElement();
        if (currentWizardSelection == null) {
            setMessage(null);
            setSelectedNode(null);
            return;
        }

        setSelectedNode(createWizardNode(currentWizardSelection));
        setMessage(currentWizardSelection.getDescription());
    }
