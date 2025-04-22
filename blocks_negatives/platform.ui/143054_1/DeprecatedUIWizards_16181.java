        wizard.setDialogSettings(wizardSettings);
        wizard.setForcePreviousAndNextButtons(true);

        WizardDialog dialog = new WizardDialog(getShell(), wizard);
        dialog.create();
        dialog.getShell().setSize(
                Math.max(SIZING_WIZARD_WIDTH, dialog.getShell().getSize().x),
                SIZING_WIZARD_HEIGHT);
