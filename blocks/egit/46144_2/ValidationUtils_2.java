
	public static IInputValidator getRemoteNameInputValidator(
			final StoredConfig config, final boolean errorOnEmptyName) {
		return new IInputValidator() {
			@Override
			public String isValid(String newText) {
				if (newText.length() == 0) {
					if (errorOnEmptyName)
						return UIText.ValidationUtils_PleaseEnterNameMessage;
					else
						return null;
				}
				if (!Repository.isValidRefName(Constants.R_REMOTES + newText)) {
					return NLS.bind(
							UIText.ValidationUtils_InvalidRemoteNameMessage,
							newText);
				}
				if (config.getNames(RepositoriesView.REMOTE, newText).size() > 0) {
					return NLS.bind(
							UIText.ValidationUtils_RemoteAlreadyExistsMessage,
							newText);
				}

				return null;
			}
		};
	}
