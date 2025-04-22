    private final IActivityRegistryListener activityRegistryListener = new IActivityRegistryListener() {
                @Override
				public void activityRegistryChanged(
                        ActivityRegistryEvent activityRegistryEvent) {
                    readRegistry(false);
                }
            };
