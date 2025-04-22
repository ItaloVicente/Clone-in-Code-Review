	private ExternalFileBufferListener bufferListener;

	class ExternalFileBufferListener implements IFileBufferListener {

		private void updateRepoState(IFileBuffer buffer) {
			IFile file = getResource(buffer);
			if (file != null) {
				return;
			}

			Repository repo = getRepository(buffer);
			if (repo == null || repo.isBare()) {
				return;
			}
			IPath relativePath = getRelativePath(repo, buffer);
			if (relativePath == null) {
				return;
			}

			IndexDiffCacheEntry diffEntry = getIndexDiffCacheEntry(repo);
			if (diffEntry != null) {
				if (GITIGNORE_NAME.equals(relativePath.lastSegment())) {
					diffEntry.refresh();
				} else {
					diffEntry.refreshFiles(
						Collections.singleton(relativePath.toString()));
				}
			}
		}

		@Nullable
		private IPath getRelativePath(Repository repo, IFileBuffer buffer) {
			IPath path = getPath(buffer);
			if (path == null) {
				return null;
			}
			IPath repositoryRoot = new Path(repo.getWorkTree().getPath());
			return path.makeRelativeTo(repositoryRoot);
		}

		@Nullable
		private IFile getResource(IFileBuffer buffer) {
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			IPath location = buffer.getLocation();
			if (location == null) {
				return null;
			}
			IFile file = root.getFile(location);
			if (!file.isAccessible()) {
				return null;
			}
			return file;
		}

		@Nullable
		private Repository getRepository(IFileBuffer buffer) {
			IPath location = getPath(buffer);
			if (location != null) {
				return Activator.getDefault().getRepositoryCache()
						.getRepository(location);
			}
			return null;
		}

		@Nullable
		private IPath getPath(IFileBuffer buffer) {
			IPath location = buffer.getLocation();
			if (location != null) {
				return location;
			}
			IFileStore store = buffer.getFileStore();
			if (store != null) {
				URI uri = store.toURI();
				if (uri != null) {
					try {
						File file = new File(uri);
						return new Path(file.getAbsolutePath());
					} catch (IllegalArgumentException e) {
					}
				}
			}
			return null;
		}

		@Override
		public void underlyingFileDeleted(IFileBuffer buffer) {
			updateRepoState(buffer);
		}

		@Override
		public void dirtyStateChanged(IFileBuffer buffer, boolean isDirty) {
			if (!isDirty) {
				updateRepoState(buffer);
			}
		}

		@Override
		public void underlyingFileMoved(IFileBuffer buffer, IPath path) {
		}

		@Override
		public void stateValidationChanged(IFileBuffer buffer,
				boolean isStateValidated) {
		}

		@Override
		public void stateChanging(IFileBuffer buffer) {
		}

		@Override
		public void stateChangeFailed(IFileBuffer buffer) {
		}

		@Override
		public void bufferDisposed(IFileBuffer buffer) {
		}

		@Override
		public void bufferCreated(IFileBuffer buffer) {
		}

		@Override
		public void bufferContentReplaced(IFileBuffer buffer) {
		}

		@Override
		public void bufferContentAboutToBeReplaced(IFileBuffer buffer) {
		}
	}

