		ModifyListener validator = new ModifyListener() {

			public void modifyText(ModifyEvent e) {
				updateMessage();
			}
		};
		commitText.getDocument().addDocumentListener(new IDocumentListener() {

			public void documentChanged(DocumentEvent event) {
				updateMessage();
			}

			public void documentAboutToBeChanged(DocumentEvent event) {
			}
		});
		authorText.addModifyListener(validator);
		committerText.addModifyListener(validator);
