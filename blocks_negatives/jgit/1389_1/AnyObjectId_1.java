	 * @param repo
	 *            repository for checking uniqueness within.
	 * @return SHA-1 abbreviation.
	 */
	public AbbreviatedObjectId abbreviate(final Repository repo) {
		return abbreviate(repo, 8);
	}

	/**
	 * Return unique abbreviation (prefix) of this object SHA-1.
	 * <p>
	 * Current implementation is not guaranteeing uniqueness, it just returns
	 * fixed-length prefix of SHA-1 string.
