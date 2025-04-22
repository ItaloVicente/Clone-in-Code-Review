	private void calculateAboutBundleData(Consumer<Collection<AboutBundleData>> aboutBundleDataConsumer,
			Display display) {
		Job loadBundleDataJob = Job.create(WorkbenchMessages.AboutPluginsPage_Load_Bundle_Data, (ICoreRunnable) monitor -> {
			SubMonitor subMonitor = SubMonitor.convert(monitor, bundles.length + 1);
			Map<String, AboutBundleData> map = new HashMap<>();
			for (int i = 0; i < bundles.length; ++i) {
				subMonitor.split(1);
				AboutBundleData data = new AboutBundleData(bundles[i]);
				if (BundleUtility.isReady(data.getState()) && !map.containsKey(data.getVersionedId())) {
					map.put(data.getVersionedId(), data);
				}
			}
			subMonitor.split(1);
			display.asyncExec(() -> aboutBundleDataConsumer.accept(map.values()));
		});
		loadBundleDataJob.schedule();
	}

