	private static class QuickOutlinePopup extends PopupDialog {
		private DiffEditorOutlinePage delegate;

		private ISelectionProvider selectionProvider;

		private Text filterText;

		public QuickOutlinePopup(IDocument document,
				ISelectionProvider selectionProvider) {
			this(null, document, selectionProvider);
		}

		public QuickOutlinePopup(Shell parent, IDocument document,
				ISelectionProvider selectionProvider) {
			super(parent, SWT.RESIZE, true, false, true, true, true,
					UIText.DiffEditor_QuickOutlineAction,
					UIText.DiffEditor_QuickOutlineFilterDescription);
			delegate = new DiffEditorOutlinePage();
			delegate.setInput(document);
			this.selectionProvider = selectionProvider;
		}

		@Override
		protected Control createTitleControl(Composite parent) {
			filterText = createFilterText(parent);
			return filterText;
		}

		@Override
		protected Control getFocusControl() {
			return filterText;
		}

		protected Text createFilterText(Composite parent) {
			filterText = new Text(parent, SWT.NONE);
			Dialog.applyDialogFont(filterText);

			GridData data = new GridData(GridData.FILL_HORIZONTAL);
			data.horizontalAlignment = GridData.FILL;
			data.verticalAlignment = GridData.CENTER;
			filterText.setLayoutData(data);

			filterText.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.keyCode == SWT.ARROW_DOWN) {
						delegate.getTreeViewer().getTree().setFocus();
					} else if (e.character == SWT.ESC) {
						dispose();
					}
				}
			});
			filterText.setText(""); //$NON-NLS-1$
			filterText.addModifyListener(e -> {
				TreeViewer viewer = delegate.getTreeViewer();
				viewer.getControl().setRedraw(false);
				viewer.setFilters(new ViewerFilter() {

					@Override
					public boolean select(Viewer v, Object parentElement,
							Object element) {
						return isMatch(filterText.getText(), element);
					}
				});
				viewer.expandAll();
				viewer.getControl().setRedraw(true);
			});
			return filterText;
		}

		private boolean isMatch(String text, Object treeElement) {
			if (treeElement instanceof FileDiffRegion) {
				return ((FileDiffRegion) treeElement).getDiff().getPath()
						.toLowerCase().contains(text.toLowerCase());
			} else if (treeElement instanceof DiffContentProvider.Folder) {
				DiffContentProvider.Folder folder = (DiffContentProvider.Folder) treeElement;
				return folder.files.stream().anyMatch(r -> isMatch(text, r))
						|| folder.folders.stream()
								.anyMatch(f -> isMatch(text, f));
			}
			return false;
		}

		@Override
		protected Control createDialogArea(Composite parent) {
			delegate.createControl(parent);

			final Tree tree = delegate.getTreeViewer().getTree();
			tree.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.character == SWT.ESC) {
						dispose();
					}
				}
			});

			tree.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					selectionProvider.setSelection(
							delegate.getTreeViewer().getSelection());
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					widgetSelected(e);
					ITreeSelection selection = delegate.getTreeViewer()
							.getStructuredSelection();
					if (selection.getFirstElement() instanceof FileDiffRegion) {
						dispose();
					}
				}
			});
			tree.setMenu(null);
			return delegate.getTreeViewer().getControl();
		}

		private void dispose() {
			delegate.dispose();
			close();
		}

		@Override
		protected IDialogSettings getDialogSettings() {
			String sectionName = "diffEditor.quickoutline"; //$NON-NLS-1$

			IDialogSettings settings = Activator.getDefault()
					.getDialogSettings().getSection(sectionName);
			if (settings == null) {
				settings = Activator.getDefault().getDialogSettings()
						.addNewSection(sectionName);
			}

			return settings;
		}
	}
}
