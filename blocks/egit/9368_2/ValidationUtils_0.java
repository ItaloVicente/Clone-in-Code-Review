					RefDatabase refDatabase = repo.getRefDatabase();
					List<String> conflictingNames = refDatabase.getConflictingNames(testFor);
					if (!conflictingNames.isEmpty()) {
						String joined = StringUtils.join(conflictingNames, ", "); //$NON-NLS-1$
						return NLS.bind(
								UIText.ValidationUtils_RefNameConflictsWithExistingMessage,
								joined);
					}
