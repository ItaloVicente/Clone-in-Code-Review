        WorkingSetRegistry registry = WorkbenchPlugin.getDefault()
                .getWorkingSetRegistry();
        fDefaultEditPage = registry.getDefaultWorkingSetPage();
        fWizard = new WorkingSetEditWizard(fDefaultEditPage);
        super.doSetUp();
    }
