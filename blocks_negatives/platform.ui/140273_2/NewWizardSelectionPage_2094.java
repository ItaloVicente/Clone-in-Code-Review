        IDialogSettings settings = getDialogSettings();
        newResourcePage = new NewWizardNewPage(this, wizardCategories,
				primaryWizards, projectsOnly);
        newResourcePage.setDialogSettings(settings);

        Control control = newResourcePage.createControl(parent);
        getWorkbench().getHelpSystem().setHelp(control,
				IWorkbenchHelpContextIds.NEW_WIZARD_SELECTION_WIZARD_PAGE);
        setControl(control);
    }

    /**
     * Since Finish was pressed, write widget values to the dialog store so that they
     *will persist into the next invocation of this wizard page
     */
    protected void saveWidgetValues() {
        newResourcePage.saveWidgetValues();
    }

    @Override
