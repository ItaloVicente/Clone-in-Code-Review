		boolean close = close(PlatformUI.RETURN_OK, false);
		if (close && osgiRegistration != null) {
			osgiRegistration.unregister();
			osgiRegistration = null;
		}
		return close;
