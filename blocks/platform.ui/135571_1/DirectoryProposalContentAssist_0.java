		directoryCombo.addModifyListener(e -> {
			if (ignoreModifyCallback[0]) {
				ignoreModifyCallback[0] = false;
				return;
			}
			updateProposals(directoryCombo.getText().substring(0, directoryCombo.getCaretPosition()), true);
		});

