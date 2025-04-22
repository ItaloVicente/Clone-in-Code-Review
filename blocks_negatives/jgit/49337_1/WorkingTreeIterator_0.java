	/**
	 * Checks whether this entry differs from a given entry from the
	 * {@link DirCache}.
	 *
	 * File status information is used and if status is same we consider the
	 * file identical to the state in the working directory. Native git uses
	 * more stat fields than we have accessible in Java.
	 *
	 * @param entry
	 *            the entry from the dircache we want to compare against
	 * @param forceContentCheck
	 *            True if the actual file content should be checked if
	 *            modification time differs.
	 * @return true if content is most likely different.
	 * @deprecated Use {@link #isModified(DirCacheEntry, boolean, ObjectReader)}
	 */
	@Deprecated
	public boolean isModified(DirCacheEntry entry, boolean forceContentCheck) {
		try {
			return isModified(entry, forceContentCheck,
					repository.newObjectReader());
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage(), e);
		}
	}

