	private void createEditors(Composite parent) {
		final IEditorRegistry editorRegistry = PlatformUI.getWorkbench().getEditorRegistry();
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		Label label = new Label(composite, SWT.NONE);
		label.setFont(composite.getFont());
		label.setText(WorkbenchMessages.ContentTypes_editorAssociations);
		GridData data = new GridData();
		data.horizontalSpan = 2;
		label.setLayoutData(data);
		editorAssociationsViewer = new TableViewer(composite);
		editorAssociationsViewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		editorAssociationsViewer.setContentProvider((IStructuredContentProvider) arg0 -> {
			if (arg0 instanceof IContentType) {
				return editorRegistry.getEditors(null, (IContentType) arg0);
			}
			return new Object[0];
		});
		editorAssociationsViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				return ((IEditorDescriptor) element).getLabel();
			}

			@Override
			public Image getImage(Object element) {
				Image res = ((IEditorDescriptor) element).getImageDescriptor().createImage();
				if (res != null) {
					disposableEditorIcons.add(res);
				}
				return res;
			}
		});
		Composite buttonsComposite = new Composite(composite, SWT.NONE);
		buttonsComposite.setLayout(new GridLayout(1, false));
		buttonsComposite.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
		addEditorAssociationButton = new Button(buttonsComposite, SWT.PUSH);
		addEditorAssociationButton.setText(WorkbenchMessages.ContentTypes_editorAssociationAddLabel);
		setButtonLayoutData(addEditorAssociationButton);
		addEditorAssociationButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (editorRegistry instanceof EditorRegistry) {
					EditorSelectionDialog dialog = new EditorSelectionDialog(getShell());
					EditorRegistry registry = (EditorRegistry) editorRegistry;
					IContentType contentType = (IContentType) editorAssociationsViewer.getInput();
					if (dialog.open() == IDialogConstants.OK_ID) {
						registry.addUserAssociation(contentType, dialog.getSelectedEditor());
						editorAssociationsViewer.refresh();
					}
				}
			}
		});
		final Button removeEditorButton = new Button(buttonsComposite, SWT.PUSH);
		removeEditorButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (editorRegistry instanceof EditorRegistry) {
					EditorRegistry registry = (EditorRegistry) editorRegistry;
					IEditorDescriptor editor = (IEditorDescriptor) ((IStructuredSelection) editorAssociationsViewer
							.getSelection()).getFirstElement();
					IContentType contentType = (IContentType) editorAssociationsViewer.getInput();
					registry.removeUserAssociation(contentType, editor);
					editorAssociationsViewer.refresh();
				}
			}
		});
		removeEditorButton.setText(WorkbenchMessages.ContentTypes_editorAssociationRemoveLabel);
		setButtonLayoutData(removeEditorButton);
		editorAssociationsViewer.addSelectionChangedListener(event -> {
			if (editorRegistry instanceof EditorRegistry) {
				EditorRegistry registry = (EditorRegistry) editorRegistry;
				IEditorDescriptor editor = (IEditorDescriptor) ((IStructuredSelection) editorAssociationsViewer
						.getSelection()).getFirstElement();
				IContentType contentType = (IContentType) editorAssociationsViewer.getInput();
				removeEditorButton.setEnabled(registry.isUserAssociation(contentType, editor));
			}
		});
		addEditorAssociationButton.setEnabled(editorAssociationsViewer.getInput() != null);
		removeEditorButton.setEnabled(editorAssociationsViewer.getInput() != null);
	}

