	private void ensureEventAdminStarted() {
		if (Activator.getDefault().getEventAdmin() == null) {
			Bundle[] bundles = Activator.getDefault().getBundle()
					.getBundleContext().getBundles();
			for (Bundle bundle : bundles) {
				if (!"org.eclipse.equinox.event".equals(bundle
						.getSymbolicName()))
					continue;
				try {
					bundle.start(Bundle.START_TRANSIENT);
				} catch (BundleException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}
