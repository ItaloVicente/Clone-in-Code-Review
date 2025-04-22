	private void refreshResources(GitResourceVariantTreeSubscriber subscriber,
			Collection<IFile> resources) {
		final IResource[] iResources = resources
				.toArray(new IResource[resources.size()]);
		try {
			subscriber.refresh(iResources, IResource.DEPTH_ONE,
					new NullProgressMonitor());
		} catch (final CoreException e) {
			Activator
					.error(CoreText.GitSubscriberMergeContext_FailedRefreshSyncView,
							e);
		}
	}

	private void refreshRepository(GitResourceVariantTreeSubscriber subscriber) {
		ResourceTraversal[] traversals = getScope().getTraversals();
		try {
			subscriber.refresh(traversals, new NullProgressMonitor());
		} catch (final CoreException e) {
			Activator
					.error(CoreText.GitSubscriberMergeContext_FailedRefreshSyncView,
							e);
		}
	}

