    private IAction getAction(String id) {
        IAction action = (IAction) actions.get(id);
        if (action == null) {
            IWizardDescriptor wizardDesc = WorkbenchPlugin.getDefault()
					.getNewWizardRegistry().findWizard(id);
            if (wizardDesc != null) {
                action = new NewWizardShortcutAction(workbenchWindow,
						wizardDesc);
