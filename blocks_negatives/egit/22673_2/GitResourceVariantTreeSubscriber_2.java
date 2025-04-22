	/**
	 * The default implementation of SyncInfoToDiffConverter uses inaccurate
	 * information with regards to some of EGit features.
	 * <p>
	 * SyncInfoToDiffConverter#asFileRevision(IResourceVariant) is called when a
	 * user double-clicks a revision from the synchronize view (among others).
	 * However, the default implementation returns an IFileRevision with no
	 * comment, author or timestamp information (this can be observed by
	 * commenting this implementation out and launching
	 * HistoryTest#queryHistoryThroughTeam()).
	 * </p>
	 * <p>
	 * SyncInfoToDiffConverter#getDeltaFor(SyncInfo) had been originally thought
	 * by Team to be used for synchronizations that considered local changes.
	 * This is not always the case with EGit. For example, a user might try and
	 * compare two refs together from the Git repository explorer (right click >
	 * synchronize with each other). In such a case, the local files must not be
	 * taken into account (i.e. we must respect the value of our
	 * GitSynchronizeData#shouldIncludeLocal(). Most of the private methods here
	 * were copy/pasted from the super implementation.
	 * </p>
	 */
	private class GitSyncInfoToDiffConverter extends SyncInfoToDiffConverter {
		@Override
		public IDiff getDeltaFor(SyncInfo info) {
			if (info.getComparator().isThreeWay()) {
				ITwoWayDiff local = getLocalDelta(info);
				ITwoWayDiff remote = getRemoteDelta(info);
				return new ThreeWayDiff(local, remote);
			} else {
				if (info.getKind() != SyncInfo.IN_SYNC) {
					IResourceVariant remote = info.getRemote();
					IResource local = info.getLocal();

					int kind;
					if (remote == null)
						kind = IDiff.REMOVE;
					else if (!local.exists())
						kind = IDiff.ADD;
					else
						kind = IDiff.CHANGE;

					if (local.getType() == IResource.FILE) {
						IFileRevision after = asFileState(remote);
						IFileRevision before = getLocalFileRevision((IFile) local);
						return new ResourceDiff(info.getLocal(), kind, 0,
								before, after);
					}
					return new ResourceDiff(info.getLocal(), kind);
				}
				return null;
			}
		}

		private ITwoWayDiff getLocalDelta(SyncInfo info) {
			int direction = SyncInfo.getDirection(info.getKind());
			if (direction == SyncInfo.OUTGOING
					|| direction == SyncInfo.CONFLICTING) {
				IResourceVariant ancestor = info.getBase();
				IResource local = info.getLocal();

				int kind;
				if (ancestor == null)
					kind = IDiff.ADD;
				else if (!local.exists())
					kind = IDiff.REMOVE;
				else
					kind = IDiff.CHANGE;

				if (local.getType() == IResource.FILE) {
					IFileRevision before = asFileState(ancestor);
					IFileRevision after = getLocalFileRevision((IFile) local);
					return new ResourceDiff(info.getLocal(), kind, 0, before,
							after);
				}
				return new ResourceDiff(info.getLocal(), kind);

			}
			return null;
		}

		/**
		 * Depending on the Synchronize data, this will return either the local
		 * file or the "source" revision.
		 *
		 * @param local
		 *            The local file.
		 * @return The file revision that should be considered for the local
		 *         (left) side a delta
		 */
		protected IFileRevision getLocalFileRevision(IFile local) {
			final GitSynchronizeData data = gsds.getData(local.getProject());
			if (data.shouldIncludeLocal())
				return new WorkspaceFileRevision(local);

			try {
				return asFileState(getSourceTree().getResourceVariant(local));
			} catch (TeamException e) {
				String error = NLS
						.bind(CoreText.GitResourceVariantTreeSubscriber_CouldNotFindSourceVariant,
								local.getName());
				Activator.logError(error, e);
				return new WorkspaceFileRevision(local);
			}
		}

		/*
		 * copy-pasted from the private implementation in
		 * SyncInfoToDiffConverter
		 */
		private ITwoWayDiff getRemoteDelta(SyncInfo info) {
			int direction = SyncInfo.getDirection(info.getKind());
			if (direction == SyncInfo.INCOMING
					|| direction == SyncInfo.CONFLICTING) {
				IResourceVariant ancestor = info.getBase();
				IResourceVariant remote = info.getRemote();

				int kind;
				if (ancestor == null)
					kind = IDiff.ADD;
				else if (remote == null)
					kind = IDiff.REMOVE;
				else
					kind = IDiff.CHANGE;

				if (info.getLocal().getType() == IResource.FILE) {
					IFileRevision before = asFileState(ancestor);
					IFileRevision after = asFileState(remote);
					return new ResourceDiff(info.getLocal(), kind, 0, before,
							after);
				}

				return new ResourceDiff(info.getLocal(), kind);
			}
			return null;
		}

		/*
		 * copy-pasted from the private implementation in
		 * SyncInfoToDiffConverter
		 */
		private IFileRevision asFileState(final IResourceVariant variant) {
			if (variant == null)
				return null;
			return asFileRevision(variant);
		}

		@Override
		protected ResourceVariantFileRevision asFileRevision(
				IResourceVariant variant) {
			return new GitResourceVariantFileRevision(variant);
		}
	}

	/**
	 * The default implementation of ResourceVariantFileRevision has no author,
	 * comment, timestamp... or any information that could be provided by the
	 * Git resource variant. This implementation uses the variant's information.
	 */
	private static class GitResourceVariantFileRevision extends
			ResourceVariantFileRevision {
		private final IResourceVariant variant;

		public GitResourceVariantFileRevision(IResourceVariant variant) {
			super(variant);
			this.variant = variant;
		}

		@Override
		public String getContentIdentifier() {
			if (variant instanceof GitRemoteResource)
				return ((GitRemoteResource) variant).getCommitId().getId()
						.getName();

			return super.getContentIdentifier();
		}

		@Override
		public long getTimestamp() {
			if (variant instanceof GitRemoteResource) {
				final PersonIdent author = ((GitRemoteResource) variant)
						.getCommitId().getAuthorIdent();
				if (author != null)
					return author.getWhen().getTime();
			}

			return super.getTimestamp();
		}

		@Override
		public String getAuthor() {
			if (variant instanceof GitRemoteResource) {
				final PersonIdent author = ((GitRemoteResource) variant)
						.getCommitId().getAuthorIdent();
				if (author != null)
					return author.getName();
			}

			return super.getAuthor();
		}

		@Override
		public String getComment() {
			if (variant instanceof GitRemoteResource)
				return ((GitRemoteResource) variant).getCommitId()
						.getFullMessage();

			return super.getComment();
		}
	}

