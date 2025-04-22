		GridDataFactory.fillDefaults().grab(true, true)
				.hint(SWT.DEFAULT, TEXT_PREFERRED_HEIGHT).applyTo(text);
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				ISelection selection = event.getSelection();
				if (!(selection instanceof IStructuredSelection)) {
					text.setText(EMPTY_STRING);
					return;
				}
				IStructuredSelection structuredSelection = (IStructuredSelection) selection;
				if (structuredSelection.size() != 1) {
					text.setText(EMPTY_STRING);
					return;
				}
				Object selected = structuredSelection.getFirstElement();
				if (selected instanceof RefUpdateElement)
					text.setText(getResult((RefUpdateElement) selected));
			}
		});
	}

	private void addToolbar(Composite parent) {
		ToolBar toolbar = new ToolBar(parent, SWT.VERTICAL);
		GridDataFactory.fillDefaults().grab(false, true).applyTo(toolbar);

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
