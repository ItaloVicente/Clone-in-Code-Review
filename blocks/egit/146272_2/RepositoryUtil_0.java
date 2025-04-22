	public static List<ReflogEntry> safeReadReflog(Repository repository,
			String refName) throws IOException {
		ReflogReader reflogReader = repository.getReflogReader(refName);
		if (reflogReader != null) {
			try {
				return reflogReader.getReverseEntries();
			} catch (RuntimeException e) {
				Activator.logError(MessageFormat.format(
						CoreText.RepositoryUtil_ReflogCorrupted, refName,
						repository.getDirectory()), e);
			}
		}
		return Collections.emptyList();
	}

