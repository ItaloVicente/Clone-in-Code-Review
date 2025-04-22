    /**
     * Creates a working set new wizard. The wizard will allow creating new
     * working sets. Returns <code>null</code> if there aren't any working set
     * definitions that support creation of working sets.
     * <p>
     * Example:
     * <code>
     *   IWorkingSetNewWizard wizard= workingSetManager.createWorkingSetNewWizard(null);
     *   if (wizard != null) {
     *	     WizardDialog dialog = new WizardDialog(shell, wizard);
     *
     *	     dialog.create();
     *	     if (dialog.open() == Window.OK) {
     *		    ...
     *       }
     *   }
     * </code>
     * </p>
     *
     * @param workingSetIds a list of working set ids which are valid workings sets
     *  to be created or <code>null</code> if all currently available working set types
     *  are valid
     *
     * @return the working set new wizard or <code>null</code>
     *
     * @since 3.1
     */
    IWorkingSetNewWizard createWorkingSetNewWizard(String[] workingSetIds);
