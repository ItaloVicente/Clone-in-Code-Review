		private void replaceWith(@NonNull List<String> files,
				@NonNull List<String> inaccessibleFiles) {
			Repository repository = currentRepository;
			if (files.isEmpty() || repository == null) {
				return;
			}
			try (Git git = new Git(repository)) {
				CheckoutCommand checkoutCommand = git.checkout();
				if (headRevision) {
					checkoutCommand.setStartPoint(Constants.HEAD);
				}
				for (String path : files) {
					checkoutCommand.addPath(path);
				}
				checkoutCommand.call();
				if (!inaccessibleFiles.isEmpty()) {
					IndexDiffCacheEntry indexDiffCacheForRepository = org.eclipse.egit.core.Activator
							.getDefault().getIndexDiffCache()
							.getIndexDiffCacheEntry(repository);
					if (indexDiffCacheForRepository != null) {
						indexDiffCacheForRepository
								.refreshFiles(inaccessibleFiles);
					}
				}
			} catch (Exception e) {
				Activator.handleError(UIText.StagingView_checkoutFailed, e,
						true);
			}
		}

