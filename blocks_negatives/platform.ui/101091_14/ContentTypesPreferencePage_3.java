	/**
	 * @param composite
	 */
	private void createContentTypesTree(Composite composite) {
		{
			Label label = new Label(composite, SWT.NONE);
			label.setFont(composite.getFont());
			label.setText(WorkbenchMessages.ContentTypes_contentTypesLabel);
			GridData data = new GridData();
			data.horizontalSpan = 2;
			label.setLayoutData(data);
		}
		{
			contentTypesViewer = new TreeViewer(composite, SWT.SINGLE
					| SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
			contentTypesViewer.getControl().setFont(composite.getFont());
			contentTypesViewer
					.setContentProvider(new ContentTypesContentProvider());
			contentTypesViewer
					.setLabelProvider(new ContentTypesLabelProvider());
			contentTypesViewer.setComparator(new ViewerComparator());
			contentTypesViewer.setInput(Platform.getContentTypeManager());
			GridData data = new GridData(GridData.FILL_BOTH);
			contentTypesViewer.getControl().setLayoutData(data);

			contentTypesViewer
					.addSelectionChangedListener(event -> {
						IContentType contentType = (IContentType) ((IStructuredSelection) event
								.getSelection()).getFirstElement();
						fileAssociationViewer.setInput(contentType);
						editButton.setEnabled(false);
						removeButton.setEnabled(false);

						if (contentType != null) {
							String charset = contentType
									.getDefaultCharset();
							if (charset == null) {
							}
							charsetField.setText(charset);
						} else {
						}

						charsetField.setEnabled(contentType != null);
						addButton.setEnabled(contentType != null);
						setButton.setEnabled(false);

						addChildContentTypeButton.setEnabled(contentType != null);
						removeContentTypeButton.setEnabled(contentType != null && contentType.isUserDefined());
					});
		}
