		final Text text = new Text(parent, SWT.MULTI | SWT.READ_ONLY
				| SWT.BORDER);
		GridData textLayoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
		textLayoutData.heightHint = TEXT_PREFERRED_HEIGHT;
		text.setLayoutData(textLayoutData);
		tableViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {
					public void selectionChanged(SelectionChangedEvent event) {
						ISelection selection = event.getSelection();
						if (!(selection instanceof IStructuredSelection)) {
							text.setText(EMPTY_STRING);
							return;
						}
						IStructuredSelection structuredSelection = (IStructuredSelection) selection;
						if (structuredSelection.size() != 1) {
							text.setText(EMPTY_STRING);
							return;
						}
						RefUpdateElement element = (RefUpdateElement) structuredSelection
								.getFirstElement();
						text.setText(getResult(element));
					}
				});
	}

	private String getResult(RefUpdateElement element) {
		StringBuffer result = new StringBuffer(EMPTY_STRING);
		PushOperationResult pushOperationResult = element
				.getPushOperationResult();
		Set<URIish> urIs = pushOperationResult.getURIs();
		for (URIish uri : urIs) {
			result.append(UIText.PushResultTable_repository);
			result.append(SPACE);
			result.append(uri.toString());
			result.append(NEWLINE);
			result.append(NEWLINE);
			String message = element.getRemoteRefUpdate(uri).getMessage();
			if (message != null) {
				result.append(message);
				result.append(NEWLINE);
			}
			StringBuffer messagesBuffer = new StringBuffer(pushOperationResult
					.getPushResult(uri).getMessages());
			trim(messagesBuffer);
			result.append(messagesBuffer);
		}
		return result.toString();
	}

	private static void trim(StringBuffer s) {
		while (s.length()>0 && s.charAt(0)=='\n')
			s.deleteCharAt(0);
		while (s.length()>0 && s.charAt(s.length()-1)=='\n')
			s.deleteCharAt(s.length()-1);
