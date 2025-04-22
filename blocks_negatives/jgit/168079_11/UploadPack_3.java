	 * @param policy
	 *            the policy used to enforce validation of a client's want list.
	 *            By default the policy is
	 *            {@link org.eclipse.jgit.transport.UploadPack.RequestPolicy#ADVERTISED},
	 *            which is the Git default requiring clients to only ask for an
	 *            object that a reference directly points to. This may be
	 *            relaxed to
	 *            {@link org.eclipse.jgit.transport.UploadPack.RequestPolicy#REACHABLE_COMMIT}
	 *            or
	 *            {@link org.eclipse.jgit.transport.UploadPack.RequestPolicy#REACHABLE_COMMIT_TIP}
	 *            when callers have {@link #setBiDirectionalPipe(boolean)} set
	 *            to false. Overrides any policy specified in a
	 *            {@link org.eclipse.jgit.transport.TransferConfig}.
