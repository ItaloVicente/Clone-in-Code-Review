	/**
	 * Get the large file repository for the given request and path.
	 *
	 * @param request
	 *            the request
	 * @param path
	 *            the path
	 * @return the large file repository storing large files.
	 * @throws org.eclipse.jgit.lfs.errors.LfsException
	 *             implementations should throw more specific exceptions to
	 *             signal which type of error occurred:
	 *             <dl>
	 *             <dt>{@link org.eclipse.jgit.lfs.errors.LfsValidationError}</dt>
	 *             <dd>when there is a validation error with one or more of the
	 *             objects in the request</dd>
	 *             <dt>{@link org.eclipse.jgit.lfs.errors.LfsRepositoryNotFound}</dt>
	 *             <dd>when the repository does not exist for the user</dd>
	 *             <dt>{@link org.eclipse.jgit.lfs.errors.LfsRepositoryReadOnly}</dt>
	 *             <dd>when the user has read, but not write access. Only
	 *             applicable when the operation in the request is "upload"</dd>
	 *             <dt>{@link org.eclipse.jgit.lfs.errors.LfsRateLimitExceeded}</dt>
	 *             <dd>when the user has hit a rate limit with the server</dd>
	 *             <dt>{@link org.eclipse.jgit.lfs.errors.LfsBandwidthLimitExceeded}</dt>
	 *             <dd>when the bandwidth limit for the user or repository has
	 *             been exceeded</dd>
	 *             <dt>{@link org.eclipse.jgit.lfs.errors.LfsInsufficientStorage}</dt>
	 *             <dd>when there is insufficient storage on the server</dd>
	 *             <dt>{@link org.eclipse.jgit.lfs.errors.LfsUnavailable}</dt>
	 *             <dd>when LFS is not available</dd>
	 *             <dt>{@link org.eclipse.jgit.lfs.errors.LfsException}</dt>
	 *             <dd>when an unexpected internal server error occurred</dd>
	 *             </dl>
	 * @since 4.5
	 * @deprecated use
	 *             {@link #getLargeFileRepository(LfsRequest, String, String)}
	 */
	@Deprecated
	protected LargeFileRepository getLargeFileRepository(LfsRequest request,
			String path) throws LfsException {
		return getLargeFileRepository(request, path, null);
	}

