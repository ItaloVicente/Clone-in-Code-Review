				if (event.getBundle() == getBundle()) {
					if (event.getType() == BundleEvent.STARTED) {
						if (getBundle().getState() == Bundle.ACTIVE) {
							refreshPluginActions();
						}
						try {
							fc.removeBundleListener(this);
						} catch (IllegalStateException ex) {
						}
					}
				}
			}
		};
		context.addBundleListener(bundleListener);
	}

	@Override
