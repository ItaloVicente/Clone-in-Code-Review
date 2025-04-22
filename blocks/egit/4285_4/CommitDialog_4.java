		commitText.getDocument().addDocumentListener(new IDocumentListener() {

			public void documentChanged(DocumentEvent event) {
				updateMessage();
			}

			public void documentAboutToBeChanged(DocumentEvent event) {
			}
		});
