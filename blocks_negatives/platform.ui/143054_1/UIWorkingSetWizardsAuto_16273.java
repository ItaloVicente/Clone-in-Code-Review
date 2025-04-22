        super.doSetUp();

        fWizardDialog = new WizardDialog(getShell(), fWizard);
        fWizardDialog.create();
        Shell dialogShell = fWizardDialog.getShell();
        dialogShell.setSize(Math.max(SIZING_WIZARD_WIDTH_2, dialogShell
                .getSize().x), SIZING_WIZARD_HEIGHT_2);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(fWizardDialog.getShell(),
                IWorkbenchHelpContextIds.WORKING_SET_NEW_WIZARD);

        IWorkingSetManager workingSetManager = fWorkbench
                .getWorkingSetManager();
        IWorkingSet[] workingSets = workingSetManager.getWorkingSets();
        for (IWorkingSet workingSet : workingSets) {
            workingSetManager.removeWorkingSet(workingSet);
        }
        setupResources();
    }

    private void setupResources() throws CoreException {
        p1 = FileUtil.createProject("TP1");
        p2 = FileUtil.createProject("TP2");
        f1 = FileUtil.createFile("f1.txt", p1);
        f2 = FileUtil.createFile("f2.txt", p2);
    }

    protected void setTextWidgetText(String text, IWizardPage page) {
