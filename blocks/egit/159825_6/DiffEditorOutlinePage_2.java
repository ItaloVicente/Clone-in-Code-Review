	static void openQuickOutline(IDocument document,
			ISelectionProvider selectionProvider) {
		new QuickOutlinePopup(document, selectionProvider).open();
	}

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
					if (e.keyCode == SWT.CR) {// return
						gotoSelectedElement();
						dispose();
					} else if (e.keyCode == SWT.ARROW_DOWN) {
						delegate.getTreeViewer().getTree().setFocus();
					} else if (e.character == SWT.ESC) {
						dispose();
					}
				}
			});
			filterText.setText(""); //$NON-NLS-1$
			filterText.addModifyListener(e -> {
				AtomicBoolean firstMatchFound = new AtomicBoolean(false);
				TreeViewer viewer = delegate.getTreeViewer();
				try {
					viewer.getControl().setRedraw(false);
					String text = filterText.getText();
					if (StringUtils.isEmptyOrNull(text)) {
						viewer.setFilters();
					} else {
						final SearchPattern pattern = new SearchPattern();
						pattern.setPattern(text);
						viewer.setFilters(new ViewerFilter() {

							@Override
							public boolean select(Viewer v,
									Object parentElement, Object element) {
								boolean result = isMatch(pattern, element);
								if (result && element instanceof FileDiffRegion
										&& !firstMatchFound.getAndSet(true)) {
									viewer.setSelection(
											new StructuredSelection(element));
								}
								return result;
							}
						});
					}
					viewer.expandAll();
				} finally {
					viewer.getControl().setRedraw(true);
				}
			});
			return filterText;
		}

		private boolean isMatch(SearchPattern pattern, Object treeElement) {
			if (treeElement instanceof FileDiffRegion) {
				String path = ((FileDiffRegion) treeElement).getDiff()
						.getPath();
				String fileName = path;
				int lastSegmentIndex = path.lastIndexOf('/');
				if (lastSegmentIndex >= 0) {
					fileName = path.substring(lastSegmentIndex + 1);
					if (pattern.matches(fileName)) {
						return true;
					}
				}
				return pattern.matches(path);
			} else if (treeElement instanceof DiffContentProvider.Folder) {
				DiffContentProvider.Folder folder = (DiffContentProvider.Folder) treeElement;
				return folder.files.stream()
						.anyMatch(r -> isMatch(pattern, r))
						|| folder.folders.stream()
								.anyMatch(f -> isMatch(pattern, f));
			}
			return false;
		}

		@Override
		protected Control createDialogArea(Composite parent) {
			delegate.createControl(parent);
