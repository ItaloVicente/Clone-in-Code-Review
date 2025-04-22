		Map<String, AboutBundleData> map = new HashMap<>();
		for (int i = 0; i < bundles.length; ++i) {
			AboutBundleData data = new AboutBundleData(bundles[i]);
			if (BundleUtility.isReady(data.getState())
					&& !map.containsKey(data.getVersionedId())) {
				map.put(data.getVersionedId(), data);
			}
		}
		bundleInfos = map.values().toArray(
				new AboutBundleData[0]);
