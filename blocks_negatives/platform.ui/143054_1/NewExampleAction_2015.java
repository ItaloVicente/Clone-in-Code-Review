        Shell parent = window.getShell();
        WizardDialog dialog = new WizardDialog(parent, wizard);
        dialog.create();
        wizard.setWindowTitle(IDEWorkbenchMessages.NewExample_title);
        dialog.getShell().setSize(
                Math.max(SIZING_WIZARD_WIDTH, dialog.getShell().getSize().x),
                SIZING_WIZARD_HEIGHT);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(dialog.getShell(),
                IIDEHelpContextIds.NEW_PROJECT_WIZARD);
