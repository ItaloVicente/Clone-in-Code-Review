		IShellProvider provider = navigator.getSite();

		newWizardMenu = new NewWizardMenu(navigator.getSite().getWorkbenchWindow());
		addBookmarkAction = new AddBookmarkAction(provider, true);
		addTaskAction = new AddTaskAction(provider);
		propertyDialogAction = new PropertyDialogAction(provider, navigator.getViewer());

		importAction = new ImportResourcesAction(navigator.getSite().getWorkbenchWindow());
		importAction.setDisabledImageDescriptor(getImageDescriptor("dtool16/import_wiz.png")); //$NON-NLS-1$
		importAction.setImageDescriptor(getImageDescriptor("etool16/import_wiz.png")); //$NON-NLS-1$

		exportAction = new ExportResourcesAction(navigator.getSite().getWorkbenchWindow());
		exportAction.setDisabledImageDescriptor(getImageDescriptor("dtool16/export_wiz.png")); //$NON-NLS-1$
		exportAction.setImageDescriptor(getImageDescriptor("etool16/export_wiz.png")); //$NON-NLS-1$

		collapseAllAction = new CollapseAllAction(navigator, ResourceNavigatorMessages.CollapseAllAction_title);
		collapseAllAction.setToolTipText(ResourceNavigatorMessages.CollapseAllAction_toolTip);
		collapseAllAction.setImageDescriptor(getImageDescriptor("elcl16/collapseall.png")); //$NON-NLS-1$

		toggleLinkingAction = new ToggleLinkingAction(navigator, ResourceNavigatorMessages.ToggleLinkingAction_text);
		toggleLinkingAction.setToolTipText(ResourceNavigatorMessages.ToggleLinkingAction_toolTip);
		toggleLinkingAction.setImageDescriptor(getImageDescriptor("elcl16/synced.png"));//$NON-NLS-1$
	}

	protected void makeSubGroups() {
		gotoGroup = new GotoActionGroup(navigator);
		openGroup = new OpenActionGroup(navigator);
		refactorGroup = new RefactorActionGroup(navigator);
		IPropertyChangeListener workingSetUpdater = event -> {
			String property = event.getProperty();

			if (WorkingSetFilterActionGroup.CHANGE_WORKING_SET.equals(property)) {
				IResourceNavigator navigator = getNavigator();
				Object newValue = event.getNewValue();

				if (newValue instanceof IWorkingSet) {
					navigator.setWorkingSet((IWorkingSet) newValue);
				} else if (newValue == null) {
					navigator.setWorkingSet(null);
				}
			}
