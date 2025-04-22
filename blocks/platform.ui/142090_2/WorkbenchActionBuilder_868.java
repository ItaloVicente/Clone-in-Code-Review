		menuBar.add(createFileMenu());
		menuBar.add(createEditMenu());
		menuBar.add(createNavigateMenu());
		menuBar.add(createProjectMenu());
		menuBar.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
		menuBar.add(createWindowMenu());
		menuBar.add(createHelpMenu());
	}

	private MenuManager createFileMenu() {
		MenuManager menu = new MenuManager(IDEWorkbenchMessages.Workbench_file, IWorkbenchActionConstants.M_FILE);
		menu.add(new GroupMarker(IWorkbenchActionConstants.FILE_START));
		{
			String newText = IDEWorkbenchMessages.Workbench_new;
			String newId = ActionFactory.NEW.getId();
			MenuManager newMenu = new MenuManager(newText, newId);
			newMenu.setActionDefinitionId("org.eclipse.ui.file.newQuickMenu"); //$NON-NLS-1$
			newMenu.add(new Separator(newId));
			this.newWizardMenu = new NewWizardMenu(getWindow());
			newMenu.add(this.newWizardMenu);
			newMenu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
			menu.add(newMenu);
		}

		menu.add(new GroupMarker(IWorkbenchActionConstants.NEW_EXT));
