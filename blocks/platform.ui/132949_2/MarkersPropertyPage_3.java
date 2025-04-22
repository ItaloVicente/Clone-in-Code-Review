
		copyButton = new Button(textContainer, SWT.PUSH);
		copyButton.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_COPY));
		copyButton.setLayoutData(new GridData(GridData.FILL_VERTICAL));
		((GridData) copyButton.getLayoutData()).verticalAlignment = SWT.BOTTOM;
		copyButton.addListener(SWT.Selection, event -> {
			Clipboard clipboard = new Clipboard(event.display);
			try {
				clipboard.setContents(new Object[] { descriptionText.getText() },
						new Transfer[] { TextTransfer.getInstance() });
			} finally {
				clipboard.dispose();
			}
		});
