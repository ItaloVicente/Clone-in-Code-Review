		memento = null;

		getSite().getWorkbenchWindow().getWorkbench().getHelpSystem().setHelp(viewer.getControl(), getHelpContextId());
	}

	protected String getHelpContextId() {
		return INavigatorHelpContextIds.RESOURCE_VIEW;
	}

	protected void initContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(manager -> ResourceNavigator.this.fillContextMenu(manager));
		TreeViewer viewer = getTreeViewer();
		Menu menu = menuMgr.createContextMenu(viewer.getTree());
		viewer.getTree().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	protected TreeViewer createViewer(Composite parent) {
		TreeViewer viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setUseHashlookup(true);
		initContentProvider(viewer);
		initLabelProvider(viewer);
		initFilters(viewer);
		initListeners(viewer);

		return viewer;
	}

	protected void initContentProvider(TreeViewer viewer) {
		viewer.setContentProvider(new WorkbenchContentProvider());
	}

	protected void initLabelProvider(TreeViewer viewer) {
		viewer.setLabelProvider(new DecoratingLabelProvider(new WorkbenchLabelProvider(),
				getPlugin().getWorkbench().getDecoratorManager().getLabelDecorator()));
	}

	protected void initFilters(TreeViewer viewer) {
		viewer.addFilter(patternFilter);
		viewer.addFilter(workingSetFilter);
	}

	private void initLinkingEnabled() {
		String setting = settings.get(IWorkbenchPreferenceConstants.LINK_NAVIGATOR_TO_EDITOR);
		if (setting != null) {
			linkingEnabled = setting.equals("true"); //$NON-NLS-1$
			return;
		}
		linkingEnabled = PlatformUI.getPreferenceStore()
				.getBoolean(IWorkbenchPreferenceConstants.LINK_NAVIGATOR_TO_EDITOR);
	}

	protected void initListeners(final TreeViewer viewer) {
		viewer.addSelectionChangedListener(event -> handleSelectionChanged(event));
		viewer.addDoubleClickListener(event -> handleDoubleClick(event));
