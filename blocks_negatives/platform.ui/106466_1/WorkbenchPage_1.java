		SaveablesList saveablesList = (SaveablesList) getWorkbenchWindow().getService(
				ISaveablesLifecycleListener.class);
		saveablesList.postOpen(part);

		for (final IPartListener listener : partListenerList) {
			SafeRunner.run(new SafeRunnable() {
				@Override
				public void run() throws Exception {
					listener.partOpened(part);
				}
			});
