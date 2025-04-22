		try {
			if (branchExists(newText)) {
				return String.format(UIText.NameValidator_nameAlreadyExists,
						newText);
			}
			if (!BranchNameValidator.isBranchNameValid(newText)) {
				return String.format(UIText.NameValidator_invalidName, newText,
						BranchNameValidator.ILLEGAL_CHARS);
			}
		} catch (CoreException e) {
