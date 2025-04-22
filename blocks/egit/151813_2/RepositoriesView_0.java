	private void setupInlineEditing(CommonViewer viewer) {
		TreeViewerFocusCellManager focusCellManager = new TreeViewerFocusCellManager(
				viewer, new FocusCellHighlighter(viewer) {
				});

		ColumnViewerEditorActivationStrategy editorActivation = new ColumnViewerEditorActivationStrategy(
				viewer) {

			@Override
			protected boolean isEditorActivationEvent(
					ColumnViewerEditorActivationEvent event) {
				return event.eventType == ColumnViewerEditorActivationEvent.PROGRAMMATIC;
			}
		};
		TreeViewerEditor.create(viewer, focusCellManager, editorActivation,
				ColumnViewerEditor.DEFAULT);

		TextCellEditor textCellEditor = new TextCellEditor(viewer.getTree());

		textCellEditor.setValidator(value -> {
			String currentText = value.toString().trim();
			if (currentText.isEmpty()) {
				return UIText.RepositoriesView_RepoGroup_EmptyNameError;
			}
			if (RepositoryGroups.getInstance().groupExists(currentText)) {
				return UIText.RepositoryGroups_DuplicateGroupNameError;
			}
			return null;
		});
		DefaultToolTip errorMessage = new DefaultToolTip(textCellEditor.getControl(),
				ToolTip.RECREATE, true);
		errorMessage.setBackgroundColor(Activator.getDefault().getResourceManager()
				.createColor(new RGB(0xFF, 0x96, 0x96)));
		textCellEditor.getControl().addDisposeListener(event -> errorMessage.hide());
		textCellEditor.addListener(new ICellEditorListener() {

			@Override
			public void editorValueChanged(boolean oldValidState,
					boolean newValidState) {
				if (newValidState) {
					errorMessage.hide();
					return;
				}
				Control editor = textCellEditor.getControl();
				Point pos = editor.getSize();
				errorMessage.setText(textCellEditor.getErrorMessage());
				pos.x = 0;
				errorMessage.show(pos);
			}

			@Override
			public void cancelEditor() {
				errorMessage.hide();
			}

			@Override
			public void applyEditorValue() {
				errorMessage.hide();
			}
		});


		viewer.setColumnProperties(new String[] { "Name" }); //$NON-NLS-1$
		viewer.setCellEditors(new CellEditor[] { textCellEditor });
		viewer.setCellModifier(new ICellModifier() {

			@Override
			public boolean canModify(Object element, String property) {
				return element instanceof RepositoryGroupNode;
			}

			@Override
			public Object getValue(Object element, String property) {
				if (element instanceof RepositoryGroupNode) {
					return ((RepositoryGroupNode) element).getObject()
							.getName();
				}
				return null;
			}

			@Override
			public void modify(Object element, String property, Object value) {
				if (element instanceof Item) {
					element = ((Item) element).getData();
				}
				if (element instanceof RepositoryGroupNode
						&& value instanceof CharSequence) {
					RepositoryGroups.getInstance().renameGroup(
							((RepositoryGroupNode) element).getObject(),
							value.toString());
					viewer.refresh();
					viewer.setSelection(new StructuredSelection(element), true);
				}
			}
		});
	}

