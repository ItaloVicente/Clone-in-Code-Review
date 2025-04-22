		final ICompareInput input = super.asCompareInput(object);
		final ISynchronizationContext ctx = getContext();

		if (input instanceof ResourceDiffCompareInput && ctx instanceof SubscriberMergeContext) {
			final IResource resource = ((ResourceNode) input.getLeft())
					.getResource();
			final Subscriber subscriber = ((SubscriberMergeContext)ctx).getSubscriber();

			if (resource instanceof IFile
					&& subscriber instanceof GitResourceVariantTreeSubscriber) {
				try {
					final IFileRevision revision = ((GitResourceVariantTreeSubscriber) subscriber)
							.getSourceFileRevision((IFile) resource);
					final ITypedElement newSource = new FileRevisionTypedElement(
							revision,
							getLocalEncoding(resource));
					((ResourceDiffCompareInput) input).setLeft(newSource);
				} catch (TeamException e) {
