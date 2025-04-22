	/**
	 * Determine if an entry path matches an ignore rule.
	 *
	 * @param entryPath
	 *            the path to test. The path must be relative to this ignore
	 *            node's own repository path, and in repository path format
	 *            (uses '/' and not '\').
	 * @param isDirectory
	 *            true if the target item is a directory.
	 * @param negateFirstMatch
	 *            true if the first match should be negated
	 * @deprecated negateFirstMatch is not honored anymore
	 * @return status of the path.
	 * @since 3.6
	 */
	@Deprecated
	public MatchResult isIgnored(String entryPath, boolean isDirectory,
			boolean negateFirstMatch) {
		final Boolean result = checkIgnored(entryPath, isDirectory);
		if (result == null) {
			return negateFirstMatch
					? MatchResult.CHECK_PARENT_NEGATE_FIRST_MATCH
					: MatchResult.CHECK_PARENT;
		}

		return result.booleanValue() ? MatchResult.IGNORED
				: MatchResult.NOT_IGNORED;
	}

