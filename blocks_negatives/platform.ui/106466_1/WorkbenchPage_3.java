		for (final IPartListener listener : partListenerList) {
			SafeRunner.run(new SafeRunnable() {
				@Override
				public void run() throws Exception {
					listener.partClosed(part);
				}
			});
