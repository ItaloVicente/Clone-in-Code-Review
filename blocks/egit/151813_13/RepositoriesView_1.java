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
				ColumnViewerEditor.TABBING_HORIZONTAL);

		String initialValue[] = { null };

		textCellEditor = new MessagePopupTextCellEditor(viewer.getTree(), true);
		textCellEditor.setValidator(value -> {
			String currentText = value.toString().trim();
			if (currentText.isEmpty()) {
				return UIText.RepositoriesView_RepoGroup_EmptyNameError;
			}
			if (!currentText.equals(initialValue[0]) && RepositoryGroups
					.getInstance().groupExists(currentText)) {
				return UIText.RepositoryGroups_DuplicateGroupNameError;
			}
			return null;
		});


		viewer.setColumnProperties(new String[] { "Name" }); //$NON-NLS-1$
		viewer.setCellEditors(new CellEditor[] { textCellEditor });
		viewer.setCellModifier(new ICellModifier() {

			@Override
			public boolean canModify(Object element, String property) {
				return element instanceof RepositoryGroupNode;
			}

			private String doGetValue(Object element) {
				if (element instanceof RepositoryGroupNode) {
					return ((RepositoryGroupNode) element).getObject()
							.getName();
				}
				return null;
			}

			@Override
			public Object getValue(Object element, String property) {
				String result = doGetValue(element);
				initialValue[0] = result;
				return result;
			}

			@Override
			public void modify(Object element, String property, Object value) {
				if (element instanceof Item) {
					element = ((Item) element).getData();
				}
				if (element instanceof RepositoryGroupNode
						&& value instanceof CharSequence) {
					RepositoryGroup group = ((RepositoryGroupNode) element)
							.getObject();
					String newName = value.toString().trim();
					if (!newName.equals(group.getName())) {
						RepositoryGroups.getInstance().renameGroup(group,
								newName);
						viewer.refresh();
						viewer.setSelection(new StructuredSelection(element),
								true);
					}
				}
			}
		});
	}

	public boolean pasteInEditor() {
		if (textCellEditor != null && textCellEditor.isActivated()) {
			textCellEditor.performPaste();
			return true;
		}
		return false;
	}

