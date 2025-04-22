		copyButton = new Button(textContainer, SWT.PUSH);
		copyButton.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_COPY));
		copyButton.setLayoutData(new GridData(GridData.FILL_VERTICAL));
		((GridData) copyButton.getLayoutData()).verticalAlignment = SWT.BOTTOM;
		clipboard = new Clipboard(parent.getDisplay());
		copyButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				clipboard.setContents(new Object[] { descriptionText.getText() },
						new Transfer[] { TextTransfer.getInstance() });
				clipboard.dispose();
			}
		});
