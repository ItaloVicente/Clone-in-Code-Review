			filterText.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.keyCode == 0x0D) {// return
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
				viewer.getControl().setRedraw(false);
				String text=filterText.getText();
				Pattern pattern = Pattern.compile(
						".*" + text.replace('?', '.').replaceAll("\\*", //$NON-NLS-1$ //$NON-NLS-2$
								"\\.\\*") + ".*", //$NON-NLS-1$//$NON-NLS-2$
						Pattern.CASE_INSENSITIVE);
				viewer.setFilters(new ViewerFilter() {

					@Override
					public boolean select(Viewer v, Object parentElement,
							Object element) {
						boolean result = isMatch(text, pattern, element);
						if (result && element instanceof FileDiffRegion
								&& !firstMatchFound.getAndSet(true)) {
							viewer.setSelection(
									new StructuredSelection(element));
						}
						return result;
					}
				});
				viewer.expandAll();
				viewer.getControl().setRedraw(true);
			});
			return filterText;
		}

		private boolean isMatch(String text, Pattern pattern,
				Object treeElement) {
			if (treeElement instanceof FileDiffRegion) {
				String path = ((FileDiffRegion) treeElement).getDiff()
						.getPath();
				String fileName = path;
				int lastSegmentIndex = path.lastIndexOf('/');
				if (lastSegmentIndex >= 0) {
					fileName = path.substring(lastSegmentIndex + 1);
					if (SearchPattern.camelCaseMatch(text, fileName,
							false)) {
						return true;
					}
				}
				return pattern.matcher(fileName).matches();
			} else if (treeElement instanceof DiffContentProvider.Folder) {
				DiffContentProvider.Folder folder = (DiffContentProvider.Folder) treeElement;
				return folder.files.stream()
						.anyMatch(r -> isMatch(text, pattern, r))
						|| folder.folders.stream()
								.anyMatch(f -> isMatch(text, pattern, f));
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

		private void gotoSelectedElement() {
			IStructuredSelection sel = delegate.getTreeViewer()
					.getStructuredSelection();
			if (!sel.isEmpty()) {
				selectionProvider.setSelection(sel);
			}
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
