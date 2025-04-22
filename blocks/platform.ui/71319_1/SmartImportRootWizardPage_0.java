		Button hideProjectsAlreadyInWorkspace = new Button(selectionButtonsGroup, SWT.CHECK);
		hideProjectsAlreadyInWorkspace.setText(DataTransferMessages.SmartImportProposals_hideExistingProjects);
		hideProjectsAlreadyInWorkspace.addSelectionListener(new SelectionAdapter() {
			final ViewerFilter existingProjectsFilter = new ViewerFilter() {
				@Override
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					return !alreadyExistingProjects.contains(element);
				}
			};
