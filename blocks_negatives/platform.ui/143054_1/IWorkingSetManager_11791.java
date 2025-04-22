    /**
     * Creates a working set edit wizard for the specified working set.
     * The working set will already be set in the wizard.
     * The caller is responsible for creating and opening a wizard dialog.
     *
     * Example:
     * <code>
     *  IWorkingSetEditWizard wizard = workingSetManager.createWorkingSetEditWizard(workingSet);
     *  if (wizard != null) {
     *	  WizardDialog dialog = new WizardDialog(shell, wizard);
     *
     *	  dialog.create();
     *	  if (dialog.open() == Window.OK) {
     *		  workingSet = wizard.getSelection();
     *    }
     *	}
     * </code>
     *
     * @param workingSet working set to create a working set edit wizard
     * 	for.
     * @return a working set edit wizard to edit the specified working set
     *  or <code>null</code> if no edit wizard has been defined for the
     *  working set. If the defined edit wizard for the working set could
     *  not be loaded a default IResource based wizard will be returned.
     * 	If the default edit wizard can not be loaded <code>null</code> is
     *  returned.
     * @since 2.1
     */
    IWorkingSetEditWizard createWorkingSetEditWizard(
            IWorkingSet workingSet);
