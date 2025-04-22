		empty = isEmpty(); // Determine the initial emptiness after the CommonViewer has been created.
		emptyWorkspaceHelper.setEmptinessDetector(new EmptinessDetector() {

			@Override
			public void init() {
			}

			@Override
			public void dispose() {
			}

			@Override
			public boolean isEmpty() {
				return empty;
			}
		});

