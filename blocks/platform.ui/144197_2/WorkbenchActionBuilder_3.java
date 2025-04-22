		String newText = IDEWorkbenchMessages.Workbench_new;
		String newId = ActionFactory.NEW.getId();
		MenuManager newMenu = new MenuManager(newText, newId);
		newMenu.setActionDefinitionId("org.eclipse.ui.file.newQuickMenu"); //$NON-NLS-1$
		newMenu.add(new Separator(newId));
		this.newWizardMenu = new NewWizardMenu(getWindow());
		newMenu.add(this.newWizardMenu);
		newMenu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		menu.add(newMenu);
