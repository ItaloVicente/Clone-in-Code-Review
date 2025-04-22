		setButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					String text = charsetField.getText().trim();
					if (text.length() == 0) {
						text = null;
					}
					getSelectedContentType().setDefaultCharset(text);
					setButton.setEnabled(false);
				} catch (CoreException e1) {
					StatusUtil.handleStatus(e1.getStatus(), StatusManager.SHOW,
							parent.getShell());
