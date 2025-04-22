		keyText.addFocusListener(new FocusAdapter() {

			@Override
			public void focusLost(FocusEvent e) {
				addValueContentProposal(valueText, keyText.getText());
			}
		});
