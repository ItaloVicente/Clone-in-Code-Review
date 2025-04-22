			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER)
					.grab(true, false).applyTo(filterText);

			filterText.addKeyListener(new KeyAdapter() {

				@Override
				public void keyPressed(KeyEvent e) {
					if (e.keyCode == SWT.CR || e.character == '\r'
							|| e.character == '\n') {// return
						gotoSelectedElement();
						close();
					} else if (e.keyCode == SWT.ARROW_DOWN) {
						delegate.getTreeViewer().getTree().setFocus();
						selectFirst();
					} else if (e.character == SWT.ESC) {
						close();
					}
				}
			});
			filterText.setMessage(UIText.DiffEditor_QuickOutlineFilterHint);
			filterText.addModifyListener(e -> {
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
								return isMatch(pattern, element);
							}
						});
					}
					viewer.expandAll();
					selectFirst();
				} finally {
					viewer.getControl().setRedraw(true);
				}
			});
			return filterText;
		}

		private void selectFirst() {
			TreeViewer viewer = delegate.getTreeViewer();
			Tree tree = viewer.getTree();
			if (tree.getItemCount() > 0) {
				TreeItem folder = tree.getItem(0);
				if (folder.getItemCount() > 0) {
					TreeItem file = folder.getItem(0);
					viewer.setSelection(
							new StructuredSelection(file.getData()));
					gotoSelectedElement();
				}
			}
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

			final Tree tree = delegate.getTreeViewer().getTree();
			tree.addKeyListener(new KeyAdapter() {

				@Override
				public void keyPressed(KeyEvent e) {
					if (e.character == SWT.ESC) {
						close();
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
						close();
					}
				}
			});
			tree.setMenu(null);
			return delegate.getTreeViewer().getControl();
		}

		private void gotoSelectedElement() {
			IStructuredSelection sel = delegate.getTreeViewer()
					.getStructuredSelection();
			if (!sel.isEmpty()) {
				selectionProvider.setSelection(sel);
			}
		}

		@Override
		public boolean close() {
			delegate.dispose();
			return super.close();
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

		@Override
		protected Point getDefaultLocation(Point initialSize) {
			IEditorPart editor = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage()
					.getActiveEditor();
			Control widget = editor.getAdapter(Control.class);
			Point size = widget.getSize();

			Point popupLocation = new Point((size.x / 2) - (initialSize.x / 2),
					(size.y / 2) - (initialSize.y / 2));
			return widget.toDisplay(popupLocation);
		}
	}
}
