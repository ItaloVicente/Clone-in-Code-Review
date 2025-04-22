				String testFor = refPrefix + newText;
				if (!Repository.isValidRefName(testFor))
					return NLS.bind(
							UIText.ValidationUtils_InvalidRefNameMessage,
							testFor);
				try {
					if (repo.resolve(testFor) != null)
						return NLS.bind(
								UIText.ValidationUtils_RefAlreadyExistsMessage,
								testFor);
					RefDatabase refDatabase = repo.getRefDatabase();
					Collection<String> conflictingNames = refDatabase.getConflictingNames(testFor);
					if (!conflictingNames.isEmpty()) {
						ArrayList<String> names = new ArrayList<>(conflictingNames);
						Collections.sort(names);
						String joined = StringUtils.join(names, ", "); //$NON-NLS-1$
						return NLS.bind(
								UIText.ValidationUtils_RefNameConflictsWithExistingMessage,
								joined);
					}
				} catch (IOException e) {
					Activator.logError(NLS.bind(
							UIText.ValidationUtils_CanNotResolveRefMessage,
							testFor), e);
					return e.getMessage();
				} catch (RevisionSyntaxException e) {
					String m = MessageFormat.format(
							UIText.ValidationUtils_InvalidRevision,
							testFor);
					Activator.logError(m, e);
					return m;
