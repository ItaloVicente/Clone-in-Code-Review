		TreeViewerColumn branchesColumn = new TreeViewerColumn(treeViewer,
				SWT.LEAD);
		branchesColumn.getColumn().setText(UIText.GitBranchSynchronizeWizardPage_branches);
		branchesColumn.getColumn().setImage(branchesImage);
		branchesColumn.getColumn().setWidth(200);
		final ComboBoxCellEditor branchesEditor = new ComboBoxCellEditor(
