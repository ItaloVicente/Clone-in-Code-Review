	private void createFilterAction() {
		filterAction = new Action(MarkerMessages.configureFiltersCommand_title) { // $NON-NLS-1$
			@Override
			public void run() {
				openFiltersDialog();
			}
		};
		filterAction.setToolTipText(MarkerMessages.configureFiltersCommand_title);// $NON-NLS-1$
		ImageDescriptor id = IDEWorkbenchPlugin.getIDEImageDescriptor("elcl16/filter_ps.png"); //$NON-NLS-1$
		if (id != null) {
			filterAction.setImageDescriptor(id);
		}
		id = IDEWorkbenchPlugin.getIDEImageDescriptor("/dlcl16/filter_ps.png"); //$NON-NLS-1$
		if (id != null) {
			filterAction.setDisabledImageDescriptor(id);
		}
	}

