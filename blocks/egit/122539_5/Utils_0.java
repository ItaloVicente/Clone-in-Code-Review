
	public static IStatus validateNewRefName(String refNameInput,
			String refPrefix) {
		String testFor = refPrefix + refNameInput;
		if (!Repository.isValidRefName(testFor)) {
			return Activator.error(MessageFormat.format(
					CoreText.ValidationUtils_InvalidRefNameMessage, testFor),
					null);
		}
		return OK_STATUS;
	}
