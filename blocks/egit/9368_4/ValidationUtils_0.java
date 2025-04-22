					RefDatabase refDatabase = repo.getRefDatabase();
					Collection<String> conflictingNames = refDatabase.getConflictingNames(testFor);
					if (!conflictingNames.isEmpty()) {
						ArrayList<String> names = new ArrayList<String>(conflictingNames);
						Collections.sort(names);
						String joined = StringUtils.join(names, ", "); //$NON-NLS-1$
						return NLS.bind(
								UIText.ValidationUtils_RefNameConflictsWithExistingMessage,
								joined);
					}
