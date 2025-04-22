	@Override
	public IDiff getDiff(IResource resource) throws CoreException {
		final GitSynchronizeData syncData = gsds.getData(resource.getProject());
		if (syncData.shouldIncludeLocal())
			return super.getDiff(resource);

		SyncInfo info = getSyncInfo(resource);
		if (info == null || info.getKind() == SyncInfo.IN_SYNC)
			return null;
		return syncInfoConverter.getDeltaFor(info);
	}

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
					if (remote == null) {
						kind = IDiff.REMOVE;
					} else if (!local.exists()) {
						kind = IDiff.ADD;
					} else {
						kind = IDiff.CHANGE;
					}
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
				if (ancestor == null) {
					kind = IDiff.ADD;
				} else if (!local.exists()) {
					kind = IDiff.REMOVE;
				} else {
					kind = IDiff.CHANGE;
				}
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

		protected IFileRevision getLocalFileRevision(IFile local) {
			final GitSynchronizeData data = gsds.getData(local.getProject());
			if (data.shouldIncludeLocal())
				return new WorkspaceFileRevision(local);

			try {
				return asFileState(getSourceTree().getResourceVariant(local));
			} catch (TeamException e) {
				return new WorkspaceFileRevision(local);
			}
		}

		private ITwoWayDiff getRemoteDelta(SyncInfo info) {
			int direction = SyncInfo.getDirection(info.getKind());
			if (direction == SyncInfo.INCOMING
					|| direction == SyncInfo.CONFLICTING) {
				IResourceVariant ancestor = info.getBase();
				IResourceVariant remote = info.getRemote();
				int kind;
				if (ancestor == null) {
					kind = IDiff.ADD;
				} else if (remote == null) {
					kind = IDiff.REMOVE;
				} else {
					kind = IDiff.CHANGE;
				}
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

	private class GitResourceVariantFileRevision extends
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

