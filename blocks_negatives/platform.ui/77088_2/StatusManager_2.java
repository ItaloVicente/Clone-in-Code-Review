		Object[] oListeners = listeners.getListeners();
		for (int i = 0; i < oListeners.length; i++) {
			if (oListeners[i] instanceof INotificationListener) {
				((INotificationListener) oListeners[i])
						.statusManagerNotified(type, adapters);
			}
