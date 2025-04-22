		ResourcesPlugin.getWorkspace().removeResourceChangeListener(resourceChangeListener);

		newWizardMenu.dispose();
		collapseAllAction.dispose();
		importAction.dispose();
		exportAction.dispose();
		propertyDialogAction.dispose();
		toggleLinkingAction.dispose();

		gotoGroup.dispose();
		openGroup.dispose();
		refactorGroup.dispose();
		sortAndFilterGroup.dispose();
		workingSetGroup.dispose();
		workspaceGroup.dispose();
		undoRedoGroup.dispose();
		super.dispose();
	}
