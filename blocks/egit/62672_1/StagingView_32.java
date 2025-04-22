		private void getSelectedFiles(@NonNull List<String> files,
				@NonNull List<String> inaccessibleFiles) {
			Iterator iterator = selection.iterator();
			while (iterator.hasNext()) {
				Object selectedItem = iterator.next();
				if (selectedItem instanceof StagingEntry) {
					StagingEntry stagingEntry = (StagingEntry) selectedItem;
					String path = stagingEntry.getPath();
					files.add(path);
					IFile resource = stagingEntry.getFile();
					if (resource == null || !resource.isAccessible()) {
						inaccessibleFiles.add(path);
					}
				}
			}
		}

		private void replaceWith(@NonNull List<String> files,
				@NonNull List<String> inaccessibleFiles) {
			Repository repository = currentRepository;
			if (files.isEmpty() || repository == null) {
				return;
			}
			CheckoutCommand checkoutCommand = new Git(repository)
					.checkout();
			if (headRevision) {
				checkoutCommand.setStartPoint(Constants.HEAD);
			}
			for (String path : files) {
				checkoutCommand.addPath(path);
			}
			try {
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

