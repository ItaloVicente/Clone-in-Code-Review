		Activator activator = Activator.getDefault();
		if (activator == null) {
			if (logger != null) {
				logger.error(NLS.bind(ServiceMessages.NO_EVENT_ADMIN, event.toString()));
			}
			return false;
		}
		EventAdmin eventAdmin = activator.getEventAdmin();
