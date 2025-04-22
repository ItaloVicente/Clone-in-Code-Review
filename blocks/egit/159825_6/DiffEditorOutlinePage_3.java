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
