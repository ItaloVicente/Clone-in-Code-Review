		okButton.setEnabled(!isCurrentBranch(refName));
	}

	private boolean isCurrentBranch(String refName) {
		if (refName != null)
			return refName.equals(currentBranch);
		return false;
