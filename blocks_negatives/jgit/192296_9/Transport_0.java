	 * @param monitor
	 *            progress monitor to inform the user about our processing
	 *            activity. Must not be null. Use
	 *            {@link org.eclipse.jgit.lib.NullProgressMonitor} if progress
	 *            updates are not interesting or necessary.
	 * @param toFetch
	 *            specification of refs to fetch locally. May be null or the
	 *            empty collection to use the specifications from the
	 *            RemoteConfig. Source for each RefSpec can't be null.
	 * @param branch
	 *            the initial branch to check out when cloning the repository.
	 *            Can be specified as ref name (<code>refs/heads/master</code>),
	 *            branch name (<code>master</code>) or tag name
	 *            (<code>v1.2.3</code>). The default is to use the branch
	 *            pointed to by the cloned repository's HEAD and can be
	 *            requested by passing {@code null} or <code>HEAD</code>.
