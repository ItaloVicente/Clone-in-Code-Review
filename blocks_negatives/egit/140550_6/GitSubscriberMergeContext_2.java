		indexChangeListener = new IndexDiffChangedListener() {
			@Override
			public void indexDiffChanged(Repository repository,
					IndexDiffData indexDiffData) {
				handleRepositoryChange(repository);
			}
		};
		resourceChangeListener = new IResourceChangeListener() {

			@Override
			public void resourceChanged(IResourceChangeEvent event) {
				IResourceDelta delta = event.getDelta();
				if (delta == null)
					return;

