		} catch (CoreException e) {
			Activator.logError(e.getMessage(), e);
		}
	}

	private void refreshResources(GitResourceVariantTreeSubscriber subscriber,
			Collection<IFile> resources) {
		IResource[] files = resources.toArray(new IResource[resources
				.size()]);
		try {
			subscriber.refresh(files, IResource.DEPTH_ONE,
					new NullProgressMonitor());
		} catch (final CoreException e) {
			Activator
					.logError(
							CoreText.GitSubscriberMergeContext_FailedRefreshSyncView,
							e);
