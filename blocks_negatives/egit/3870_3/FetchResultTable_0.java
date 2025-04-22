
		ToolItem collapseItem = new ToolItem(toolbar, SWT.PUSH);
		Image collapseImage = UIIcons.COLLAPSEALL.createImage();
		UIUtils.hookDisposal(collapseItem, collapseImage);
		collapseItem.setImage(collapseImage);
		collapseItem.setToolTipText(UIText.FetchResultTable_collapseAll);
		collapseItem.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				treeViewer.collapseAll();
			}

		});

		ToolItem expandItem = new ToolItem(toolbar, SWT.PUSH);
		Image expandImage = UIIcons.EXPAND_ALL.createImage();
		UIUtils.hookDisposal(expandItem, expandImage);
		expandItem.setImage(expandImage);
		expandItem.setToolTipText(UIText.FetchResultTable_expandAll);
		expandItem.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				treeViewer.expandAll();
			}

		});
