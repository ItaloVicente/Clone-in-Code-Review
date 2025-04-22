
		private void updateEnablement() {
			boolean useGitFormat = gitFormat.getSelection();
			contextLines.setEnabled(useGitFormat);
			contextLinesLabel.setEnabled(useGitFormat);
		}

		private void validatePage() {
			boolean pageValid = true;
			if(gitFormat.getSelection())
				pageValid = validateContextLines();

			if (pageValid) {
				setMessage(null);
				setErrorMessage(null);
			}
			setPageComplete(pageValid);
		}

		private boolean validateContextLines() {
			String text = contextLines.getText();
			if(text == null || text.trim().length() == 0) {
				setErrorMessage(UIText.GitCreatePatchWizard_ContextMustBePositiveInt);
				return false;
			}

			text = text.trim();

			char[] charArray = text.toCharArray();
			for (char c : charArray) {
				if(!Character.isDigit(c)) {
					setErrorMessage(UIText.GitCreatePatchWizard_ContextMustBePositiveInt);
					return false;
				}
			}
			return true;
		}
