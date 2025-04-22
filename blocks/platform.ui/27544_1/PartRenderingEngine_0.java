				BundleContext context = WorkbenchSWTActivator.getDefault()
						.getContext();
				for (Bundle bundle : context.getBundles()) {
					if (bundle.getSymbolicName() != null) {
						prefs.add(InstanceScope.INSTANCE.getNode(bundle
								.getSymbolicName()));
