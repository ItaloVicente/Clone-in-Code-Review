		directoryCombo.addVerifyListener(e -> {
				Combo source = (Combo) e.getSource();
				if (e.start == 0 && e.end == source.getText().length() && e.text.isEmpty()) {
					dropdownSelection = true;
				}
		});

		directoryCombo.addModifyListener(e -> {
					boolean openProposalPopup = true;
					if (dropdownSelection) {
						dropdownSelection = false;
						openProposalPopup = false;
						return;
					}
					updateProposals(directoryCombo.getText().substring(0, directoryCombo.getCaretPosition()),
							openProposalPopup);
				});
