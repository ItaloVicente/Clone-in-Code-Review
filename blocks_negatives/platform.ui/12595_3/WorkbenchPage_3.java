		for (final Object listener : partListenerList.getListeners()) {
			SafeRunner.run(new SafeRunnable() {
				public void run() throws Exception {
					((IPartListener) listener).partClosed(part);
				}
			});
