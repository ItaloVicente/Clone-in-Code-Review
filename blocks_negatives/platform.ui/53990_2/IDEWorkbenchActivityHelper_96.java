        return new IResourceChangeListener() {
            @Override
			public void resourceChanged(IResourceChangeEvent event) {
                if (!WorkbenchActivityHelper.isFiltering()) {
					return;
				}
                IResourceDelta mainDelta = event.getDelta();
