		unstagedViewer.addFilter(filter);
		stagedViewer.addFilter(filter);
	}

	private void createUnstagedToolBarComposite() {
		Composite unstagedToolbarComposite = toolkit
				.createComposite(unstagedSection);
		unstagedToolbarComposite.setBackground(null);
		RowLayout unstagedLayout = new RowLayout();
		unstagedLayout.marginHeight = 0;
		unstagedLayout.marginWidth = 0;
		unstagedLayout.marginTop = 0;
		unstagedLayout.marginBottom = 0;
		unstagedLayout.marginLeft = 0;
		unstagedLayout.marginRight = 0;
		unstagedToolbarComposite.setLayout(unstagedLayout);
		unstagedSection.setTextClient(unstagedToolbarComposite);
		unstagedExpandAllAction = new Action(UIText.UIUtils_ExpandAll,
				IAction.AS_PUSH_BUTTON) {
			public void run() {
				unstagedViewer.expandAll();
			}
		};
		unstagedExpandAllAction.setImageDescriptor(UIIcons.EXPAND_ALL);

		unstagedCollapseAllAction = new Action(UIText.UIUtils_CollapseAll,
				IAction.AS_PUSH_BUTTON) {
			public void run() {
				unstagedViewer.collapseAll();
			}
		};
		unstagedCollapseAllAction.setImageDescriptor(UIIcons.COLLAPSEALL);

		ToolBarManager unstagedToolBarManager = new ToolBarManager(SWT.FLAT
				| SWT.HORIZONTAL);

		unstagedToolBarManager.add(unstagedExpandAllAction);
		unstagedToolBarManager.add(unstagedCollapseAllAction);

		unstagedToolBarManager.update(true);
		unstagedToolBarManager.createControl(unstagedToolbarComposite);
	}

	private void createStagedToolBarComposite() {
		Composite stagedToolbarComposite = toolkit
				.createComposite(stagedSection);
		stagedToolbarComposite.setBackground(null);
		RowLayout stagedLayout = new RowLayout();
		stagedLayout.marginHeight = 0;
		stagedLayout.marginWidth = 0;
		stagedLayout.marginTop = 0;
		stagedLayout.marginBottom = 0;
		stagedLayout.marginLeft = 0;
		stagedLayout.marginRight = 0;
		stagedToolbarComposite.setLayout(stagedLayout);
		stagedSection.setTextClient(stagedToolbarComposite);
		stagedExpandAllAction = new Action(UIText.UIUtils_ExpandAll,
				IAction.AS_PUSH_BUTTON) {
			public void run() {
				stagedViewer.expandAll();
			}
		};
		stagedExpandAllAction.setImageDescriptor(UIIcons.EXPAND_ALL);

		stagedCollapseAllAction = new Action(UIText.UIUtils_CollapseAll,
				IAction.AS_PUSH_BUTTON) {
			public void run() {
				stagedViewer.collapseAll();
			}
		};
		stagedCollapseAllAction.setImageDescriptor(UIIcons.COLLAPSEALL);

		ToolBarManager stagedToolBarManager = new ToolBarManager(SWT.FLAT
				| SWT.HORIZONTAL);

		stagedToolBarManager.add(stagedExpandAllAction);
		stagedToolBarManager.add(stagedCollapseAllAction);
		stagedToolBarManager.update(true);
		stagedToolBarManager.createControl(stagedToolbarComposite);
	}

	public Repository getCurrentRepository() {
		return currentRepository;
