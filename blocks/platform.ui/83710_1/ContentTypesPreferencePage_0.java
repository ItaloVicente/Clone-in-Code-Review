		Composite buttonsComposite = new Composite(composite, SWT.NONE);
		buttonsComposite.setLayoutData(new GridData(SWT.DEFAULT, SWT.TOP, false, false));
		buttonsComposite.setLayout(new GridLayout(1, false));
		Button addRootContentTypeButton = new Button(buttonsComposite, SWT.PUSH);
		setButtonLayoutData(addRootContentTypeButton);
		addRootContentTypeButton.setText(WorkbenchMessages.ContentTypes_addRootContentTypeButton);
		addRootContentTypeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String id = "userCreated" + System.currentTimeMillis(); //$NON-NLS-1$
				IContentTypeManager manager = (IContentTypeManager) contentTypesViewer.getInput();
				try {
					IContentType mewCcontentType = manager.addContentType(id, id, null);
					contentTypesViewer.refresh();
					contentTypesViewer.setSelection(new StructuredSelection(mewCcontentType));
				} catch (CoreException e1) {
					MessageDialog.openError(getShell(), "Failed", e1.getMessage());
				}
			}
		});
		addChildContentTypeButton = new Button(buttonsComposite, SWT.PUSH);
		setButtonLayoutData(addChildContentTypeButton);
		addChildContentTypeButton.setText(WorkbenchMessages.ContentTypes_addChildContentTypeButton);
		addChildContentTypeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String id = "userCreated" + System.currentTimeMillis(); //$NON-NLS-1$
				IContentTypeManager manager = (IContentTypeManager) contentTypesViewer.getInput();
				try {
					IContentType newContentType = manager.addContentType(id, id, getSelectedContentType());
					contentTypesViewer.refresh(getSelectedContentType());
					contentTypesViewer.setSelection(new StructuredSelection(newContentType));
				} catch (CoreException e1) {
					MessageDialog.openError(getShell(), "Failed", e1.getMessage());
				}
			}
		});
		addChildContentTypeButton.setEnabled(getSelectedContentType() != null);
		removeContentTypeButton = new Button(buttonsComposite, SWT.PUSH);
		setButtonLayoutData(removeContentTypeButton);
		removeContentTypeButton.setText(WorkbenchMessages.ContentTypes_removeContentTypeButton);
		removeContentTypeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IContentType selectedContentType = getSelectedContentType();
				try {
					Platform.getContentTypeManager().removeContentType(selectedContentType);
					contentTypesViewer.refresh();
				} catch (CoreException e1) {
					MessageDialog.openError(getShell(), "Failed", e1.getMessage());
				}
			}
		});
		removeContentTypeButton
				.setEnabled(getSelectedContentType() != null && getSelectedContentType().isUserDefined());
