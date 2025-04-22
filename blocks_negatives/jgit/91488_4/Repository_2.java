
	/**
	 * Normalizes the passed branch name into a possible valid branch name. The
	 * validity of the returned name should be checked by a subsequent call to
	 * {@link #isValidRefName(String)}.
	 * <p/>
	 * Future implementations of this method could be more restrictive or more
	 * lenient about the validity of specific characters in the returned name.
	 * <p/>
	 * The current implementation returns the trimmed input string if this is
	 * already a valid branch name. Otherwise it returns a trimmed string only
	 * containing word characters ([a-zA-Z_0-9]) and hyphens ('-'). Colons are
	 * replaced by hyphens. Repeating underscores and hyphens are replaced by a
	 * single occurrence. Underscores and hyphens at the beginning of the string
	 * are removed.
	 *
	 * @param name
	 *            The name to normalize.
	 *
	 * @return The normalized String or null if null was passed.
	 * @since 4.7
	 * @see #isValidRefName(String)
	 */
	public static String normalizeBranchName(String name) {
		if (name == null || name.length() == 0) {
			return name;
		}
		String result = name.trim();
		String fullName = result.startsWith(Constants.R_HEADS) ? result
				: Constants.R_HEADS + result;
		if (isValidRefName(fullName)) {
			return result;
		}
		return result.replaceAll("\\s+([_:-])*?\\s+", "$1") //$NON-NLS-1$//$NON-NLS-2$
				.replaceAll(":", "-") //$NON-NLS-1$//$NON-NLS-2$
				.replaceAll("\\s+", "_") //$NON-NLS-1$//$NON-NLS-2$
				.replaceAll("_{2,}", "_") //$NON-NLS-1$//$NON-NLS-2$
				.replaceAll("-{2,}", "-") //$NON-NLS-1$//$NON-NLS-2$
				.replaceAll("[^\\w-]", "") //$NON-NLS-1$ //$NON-NLS-2$
				.replaceAll("^_+", "") //$NON-NLS-1$//$NON-NLS-2$
				.replaceAll("^-+", ""); //$NON-NLS-1$//$NON-NLS-2$
	}
