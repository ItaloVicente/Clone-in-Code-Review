
	private void suggestBranchName(String ref) {
		if (nameText.getText().length() == 0 || nameIsSuggestion) {
			int indexOfLastSlash = ref.lastIndexOf("/"); //$NON-NLS-1$
			String branchNameSuggestion = indexOfLastSlash != -1 ? ref.substring(indexOfLastSlash + 1) : ref;
			nameText.setText(branchNameSuggestion);
			nameIsSuggestion = true;
		}
	}
