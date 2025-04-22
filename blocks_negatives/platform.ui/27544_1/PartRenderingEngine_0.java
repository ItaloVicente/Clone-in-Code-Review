				PlatformAdmin admin = WorkbenchSWTActivator.getDefault()
						.getPlatformAdmin();

				State state = admin.getState(false);
				BundleDescription[] bundles = state.getBundles();

				for (BundleDescription desc : bundles) {
					if (desc.getName() != null) {
						prefs.add(InstanceScope.INSTANCE.getNode(desc.getName()));
