
	@NonNull
	public static IStatus validateNewRefName(String refNameInput,
			@NonNull Repository repo, @NonNull String refPrefix,
			final boolean errorOnEmptyName) {
		if (refNameInput == null || refNameInput.isEmpty()) {
			if (errorOnEmptyName) {
				return Activator.error(
						CoreText.ValidationUtils_PleaseEnterNameMessage, null);
			} else {
				return OK_STATUS;
			}
		}
		String testFor = refPrefix + refNameInput;
		if (!Repository.isValidRefName(testFor)) {
			return Activator.error(MessageFormat.format(
					CoreText.ValidationUtils_InvalidRefNameMessage, testFor),
					null);
		}
		try {
			if (repo.resolve(testFor) != null) {
				return Activator.error(MessageFormat.format(
						CoreText.ValidationUtils_RefAlreadyExistsMessage,
						testFor), null);
			}
			RefDatabase refDatabase = repo.getRefDatabase();
			Collection<String> conflictingNames = refDatabase
					.getConflictingNames(testFor);
			if (!conflictingNames.isEmpty()) {
				String joined = conflictingNames.stream().sorted()
						.collect(joining(", ")); //$NON-NLS-1$
				return Activator.error(MessageFormat.format(
						CoreText.ValidationUtils_RefNameConflictsWithExistingMessage,
						joined), null);
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
