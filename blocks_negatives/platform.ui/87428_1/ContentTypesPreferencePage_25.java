		charsetField.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				String errorMessage = null;
				String text = charsetField.getText();
				try {
					if (text.length() != 0 && !Charset.isSupported(text))
						errorMessage = WorkbenchMessages.ContentTypes_unsupportedEncoding;
				} catch (IllegalCharsetNameException ex) {
