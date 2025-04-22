				return defaultCellEditor(srcBranchesEditor, element);
			}

			@Override
			protected boolean canEdit(Object element) {
				return true;
			}
		});
		srcColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				SyncData syncData = repoMapping.get(element);

				return syncData.srcRev == null ? "" : syncData.srcRev; //$NON-NLS-1$
			}
		});

		TreeViewerColumn dstColumn = new TreeViewerColumn(treeViewer, SWT.LEAD);
		dstColumn.getColumn().setText(
				UIText.GitSynchronizeWizard_DestinationBranch);
		dstColumn.getColumn().setImage(branchesImage);
		dstColumn.getColumn().setWidth(180);
		final ComboBoxCellEditor dstBranchesEditor = new ComboBoxCellEditor(
				treeViewer.getTree(), new String[0]);
		dstColumn.setEditingSupport(new EditingSupport(treeViewer) {
			@Override
			protected void setValue(Object element, Object value) {
				String branch = getSelectedBranchName(dstBranchesEditor, value);
				if (branch == null)
					return;

				SyncData syncData = repoMapping.get(element);
				syncData.dstRev = branch;

				repoMapping.put((Repository) element, syncData);
				treeViewer.refresh(element, true);

				boolean isCompleated = syncData.srcRev != null;
				setPageComplete(isCompleated);
				treeViewer.setChecked(element, isCompleated);
			}

			@Override
			protected Object getValue(Object element) {
				SyncData syncData = repoMapping.get(element);
				if (syncData == null)
					return Integer.valueOf(0);

				String branch = syncData.srcRev;
				CCombo combo = (CCombo) dstBranchesEditor.getControl();
				return Integer.valueOf(branch == null ? 0 : combo
						.indexOf(branch));
			}

			@Override
			protected CellEditor getCellEditor(Object element) {
				return defaultCellEditor(dstBranchesEditor, element);
