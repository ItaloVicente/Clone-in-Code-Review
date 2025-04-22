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
		StringBuilder result = new StringBuilder(EMPTY_STRING);
		PushOperationResult pushOperationResult = element
				.getPushOperationResult();
		Set<URIish> urIs = pushOperationResult.getURIs();
		Iterator<URIish> iterator = urIs.iterator();
		while(iterator.hasNext()) {
			boolean lineBreakNeeded = false;
			URIish uri = iterator.next();
			result.append(UIText.PushResultTable_repository);
			result.append(SPACE);
			result.append(uri.toString());
			result.append(Text.DELIMITER);
			result.append(Text.DELIMITER);
			String message = element.getRemoteRefUpdate(uri).getMessage();
			if (message != null) {
				result.append(message);
				result.append(Text.DELIMITER);
				lineBreakNeeded = true;
			}
			StringBuilder messagesBuffer = new StringBuilder(pushOperationResult
					.getPushResult(uri).getMessages());
			trim(messagesBuffer);
			if (messagesBuffer.length()>0) {
				result.append(messagesBuffer);
				result.append(Text.DELIMITER);
				lineBreakNeeded = true;
			}
			if (iterator.hasNext() && lineBreakNeeded)
				result.append(Text.DELIMITER);
		}
		trim(result);
		return result.toString();
	}

	private static void trim(StringBuilder s) {
		while (s.length()>0 && (s.charAt(0)=='\n' || s.charAt(0)=='\r'))
			s.deleteCharAt(0);
		while (s.length()>0 && (s.charAt(s.length()-1)=='\n' || s.charAt(s.length()-1)=='\r'))
			s.deleteCharAt(s.length()-1);
