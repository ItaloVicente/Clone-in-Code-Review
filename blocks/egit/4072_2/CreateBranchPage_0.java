
	private void suggestBranchName(String ref) {
		if (nameText.getText().length() == 0 || nameIsSuggestion) {
			String branchNameSuggestion = getProposedTargetName(ref);
			if (branchNameSuggestion != null) {
				nameText.setText(branchNameSuggestion);
				nameText.selectAll();
				nameIsSuggestion = true;
			}
		}
	}
