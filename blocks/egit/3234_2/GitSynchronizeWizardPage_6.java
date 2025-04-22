				SyncData syncData = repoMapping.get(element);

				return syncData.dstRev == null ? "" : syncData.dstRev; //$NON-NLS-1$
			}
		});

		final TreeViewerColumn includeLocalColumn = new TreeViewerColumn(
				treeViewer, SWT.CENTER);
		includeLocalColumn.getColumn().setWidth(80);
		includeLocalColumn.getColumn().setText(
				UIText.GitSynchronizeWizard_IncludeLocal);
		includeLocalColumn.getColumn().setToolTipText(
				UIText.GitSynchronizeWizard_IncludeLocalToolTip);
		final CheckboxCellEditor includeLocalEditor = new CheckboxCellEditor(
				treeViewer.getTree());
		includeLocalColumn.setLabelProvider(new CheckboxLabelProvider(
				treeViewer.getControl()) {
			@Override
			protected boolean isChecked(Object element) {
				return repoMapping.get(element).includeLocal;
			}
		});
		includeLocalColumn.setEditingSupport(new EditingSupport(treeViewer) {
			@Override
			protected void setValue(Object element, Object value) {
				SyncData syncData = repoMapping.get(element);
				syncData.includeLocal = ((Boolean) value).booleanValue();
			}

			@Override
			protected Object getValue(Object element) {
				return Boolean.valueOf(repoMapping.get(element).includeLocal);
			}

			@Override
			protected CellEditor getCellEditor(Object element) {
				return includeLocalEditor;
			}

			@Override
			protected boolean canEdit(Object element) {
				return true;
			}
		});

		treeViewer.addCheckStateListener(new ICheckStateListener() {
			public void checkStateChanged(CheckStateChangedEvent event) {
				Repository element = (Repository) event.getElement();
				if (event.getChecked()) {
					SyncData data = repoMapping.get(element);
					data.srcRev = ((CCombo) srcBranchesEditor.getControl())
							.getText();
					data.dstRev = ((CCombo) dstBranchesEditor.getControl())
							.getText();
					Boolean includeLocal = (Boolean) includeLocalEditor.getValue();
					data.includeLocal = includeLocal == null ? false : includeLocal.booleanValue();
					repoMapping.put(element, data);
