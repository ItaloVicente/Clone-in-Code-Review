		Collection<Change> changes = list.get();
		if (doAutoFill) {
			fillInPatchSet(changes, originalRefText);
		}
		return changes;
	}

	private void fillInPatchSet(Collection<Change> changes,
			String originalText) {
		String currentText = refText.getText();
		if (contentProposer.isProposalPopupOpen()
				|| originalText != null && !originalText.equals(currentText)) {
			return;
		}
		Change change = determineChangeFromString(currentText);
		if (change != null && change.getPatchSetNumber() == null) {
			int changeNumber = change.getChangeNumber().intValue();
			for (Change fromGerrit : changes) {
				int num = fromGerrit.getChangeNumber().intValue();
				if (num < changeNumber) {
					return; // Doesn't exist
				} else if (changeNumber == num) {
					String fullRef = fromGerrit.getRefName();
					refText.setText(fullRef);
					refText.setSelection(fullRef.length());
					return;
				}
			}
		}
