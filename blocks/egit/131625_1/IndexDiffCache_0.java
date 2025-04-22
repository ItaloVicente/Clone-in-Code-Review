		for (IndexDiffChangedListener listener : listeners) {
			SafeRunner.run(new ISafeRunnable() {
				@Override
				public void run() throws Exception {
					listener.indexDiffChanged(repository, indexDiffData);
				}

				@Override
				public void handleException(Throwable exception) {
				}
			});
