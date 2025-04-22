
	public static IStatus validateRefName(String refNameInput,
			final Repository repo, final String refPrefix,
			final boolean errorOnEmptyName) {
		if (refNameInput.isEmpty()) {
			if (errorOnEmptyName)
				return new Status(ERROR, Activator.getPluginId(),
						CoreText.ValidationUtils_PleaseEnterNameMessage);
			else
				return OK_STATUS;
		}
		String testFor = refPrefix + refNameInput;
		if (!Repository.isValidRefName(testFor))
			return new Status(ERROR, Activator.getPluginId(), NLS.bind(
					CoreText.ValidationUtils_InvalidRefNameMessage, testFor));
		try {
			if (repo.resolve(testFor) != null)
				return new Status(ERROR, Activator.getPluginId(), NLS.bind(
						CoreText.ValidationUtils_RefAlreadyExistsMessage,
						testFor));
			RefDatabase refDatabase = repo.getRefDatabase();
			Collection<String> conflictingNames = refDatabase
					.getConflictingNames(testFor);
			if (!conflictingNames.isEmpty()) {
				String joined = conflictingNames.stream().sorted()
						.collect(joining(", ")); //$NON-NLS-1$
				return new Status(ERROR, Activator.getPluginId(), NLS.bind(
						CoreText.ValidationUtils_RefNameConflictsWithExistingMessage,
						joined));
			}
		} catch (IOException e) {
			return Activator.error(e.getMessage(), e);
		} catch (RevisionSyntaxException e) {
			String m = MessageFormat
					.format(CoreText.ValidationUtils_InvalidRevision, testFor);
			return Activator.error(m, e);
		}
		return OK_STATUS;
	}
