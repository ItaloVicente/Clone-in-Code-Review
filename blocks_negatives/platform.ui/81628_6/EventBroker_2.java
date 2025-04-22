	static {
		EventAdmin eventAdmin = Activator.getDefault().getEventAdmin();
		if (eventAdmin == null) {
			Bundle[] bundles = Activator.getDefault().getBundleContext().getBundles();
			for (Bundle bundle : bundles) {
				if (!"org.eclipse.equinox.event".equals(bundle.getSymbolicName()))
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

	public EventBroker() {
	}
