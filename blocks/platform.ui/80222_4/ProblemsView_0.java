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

	private void initToolBar() {
		IActionBars bars = getViewSite().getActionBars();
		IToolBarManager tm = bars.getToolBarManager();
		tm.add(new Separator("filterGroup")); //$NON-NLS-1$
		tm.add(filterAction);

	}

	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		createFilterAction();
		initToolBar();
	}

