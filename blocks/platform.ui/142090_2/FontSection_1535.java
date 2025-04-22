			}
		});

		data = new FormData();
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(fontText,
			-ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(0, 0);
		fontLabel.setLayoutData(data);

		data = new FormData();
		data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH);
		data.right = new FormAttachment(fontButton,
			-ITabbedPropertyConstants.HSPACE);
		data.top = new FormAttachment(0, 0);
		fontText.setLayoutData(data);

		data = new FormData();
		data.right = new FormAttachment(100, 0);
		data.top = new FormAttachment(0, 0);
		data.height = buttonHeight;
		fontButton.setLayoutData(data);
	}

	public void refresh() {
		FontData fontdata = buttonElement.getControl().getFont().getFontData()[0];
		fontText.setText(StringConverter.asString(fontdata));
	}
