		unstagedViewer.addFilter(filter);
		stagedViewer.addFilter(filter);
	}

	private void createUnstagedToolBarComposite() {
		Composite unstagedToolbarComposite = toolkit
				.createComposite(unstagedSection);
		unstagedToolbarComposite.setBackground(null);
		unstagedToolbarComposite.setLayout(createRowLayoutWithoutMargin());
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

		unstagedToolBarManager = new ToolBarManager(SWT.FLAT | SWT.HORIZONTAL);

		unstagedToolBarManager.add(unstagedExpandAllAction);
		unstagedToolBarManager.add(unstagedCollapseAllAction);

		unstagedToolBarManager.update(true);
		unstagedToolBarManager.createControl(unstagedToolbarComposite);
	}

	private void createStagedToolBarComposite() {
		Composite stagedToolbarComposite = toolkit
				.createComposite(stagedSection);
		stagedToolbarComposite.setBackground(null);
		stagedToolbarComposite.setLayout(createRowLayoutWithoutMargin());
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

		stagedToolBarManager = new ToolBarManager(SWT.FLAT | SWT.HORIZONTAL);

		stagedToolBarManager.add(stagedExpandAllAction);
		stagedToolBarManager.add(stagedCollapseAllAction);
		stagedToolBarManager.update(true);
		stagedToolBarManager.createControl(stagedToolbarComposite);
	}

	private static RowLayout createRowLayoutWithoutMargin() {
		RowLayout layout = new RowLayout();
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.marginTop = 0;
		layout.marginBottom = 0;
		layout.marginLeft = 0;
		layout.marginRight = 0;
		return layout;
	}

	public Repository getCurrentRepository() {
		return currentRepository;
