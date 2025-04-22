
	public static ToolBar addExpansionItems(final ToolBar toolbar,
			final AbstractTreeViewer viewer) {
		ToolItem collapseItem = new ToolItem(toolbar, SWT.PUSH);
		Image collapseImage = UIIcons.COLLAPSEALL.createImage();
		UIUtils.hookDisposal(collapseItem, collapseImage);
		collapseItem.setImage(collapseImage);
		collapseItem.setToolTipText(UIText.UIUtils_CollapseAll);
		collapseItem.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				viewer.collapseAll();
			}

		});

		ToolItem expandItem = new ToolItem(toolbar, SWT.PUSH);
		Image expandImage = UIIcons.EXPAND_ALL.createImage();
		UIUtils.hookDisposal(expandItem, expandImage);
		expandItem.setImage(expandImage);
		expandItem.setToolTipText(UIText.UIUtils_ExpandAll);
		expandItem.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				viewer.expandAll();
			}

		});
		return toolbar;
	}
