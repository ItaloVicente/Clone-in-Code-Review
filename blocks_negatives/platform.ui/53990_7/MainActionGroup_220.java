        resourceChangeListener = new IResourceChangeListener() {
            @Override
			public void resourceChanged(IResourceChangeEvent event) {
                handleResourceChanged(event);
            }
        };
