		directoryCombo.addVerifyListener(e -> {
			boolean openProposalPopup = true;
			if (e.text.length() > 1) {
				openProposalPopup = false;
			}
			updateProposals(directoryCombo.getText().substring(0, directoryCombo.getCaretPosition()),
					openProposalPopup);
		});
