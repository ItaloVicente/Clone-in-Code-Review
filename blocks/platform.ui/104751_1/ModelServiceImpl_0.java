		if (eventBroker == null) {
			StringBuilder msg = new StringBuilder();
			msg.append("Could not retrieve event broker!"); //$NON-NLS-1$
			Bundle bundle = Platform.getBundle("org.eclipse.equinox.event"); //$NON-NLS-1$
			if (bundle == null) {
				msg.append(" Bundle 'org.eclipse.equinox.event' is missing. Please check your configuration."); //$NON-NLS-1$
			} else if (bundle.getState() != Bundle.ACTIVE) {
				msg.append(" Bundle 'org.eclipse.equinox.event' is not active. Please check your configuration."); //$NON-NLS-1$
			}
			throw new IllegalStateException(msg.toString());
		}
