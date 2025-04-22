	public static int validateOrGetNew(int preferredPort) {
		if (preferredPort == 0 || isPortInUse(preferredPort)) {
			if (preferredPort != 0) {
				LOG.warn("Port {} already in use
			}
			try (ServerSocket ss = new ServerSocket(0)) {
				return ss.getLocalPort();
			} catch (IOException e) {
				LOG.error(ERROR_MESSAGE
				throw new RuntimeException(ERROR_MESSAGE);
			}
		}
		return preferredPort;
	}
