	static {
		try {
			OSGI_AVAILABLE = FrameworkUtil.getBundle(InternalPolicy.class) != null;
		} catch (Throwable t) {
			OSGI_AVAILABLE = false;
		}
	}

