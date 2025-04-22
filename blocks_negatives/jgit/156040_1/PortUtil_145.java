    public static int validateOrGetNew(int preferredPort) {
        if (preferredPort == 0 || isPortInUse(preferredPort)) {
            if (preferredPort != 0) {
                LOG.warn("Port {} already in use, system will automatically look for a new one.", preferredPort);
            }
            try (ServerSocket ss = new ServerSocket(0)) {
                return ss.getLocalPort();
            } catch (IOException e) {
                LOG.error(ERROR_MESSAGE, e);
                throw new RuntimeException(ERROR_MESSAGE);
            }
        }
        return preferredPort;
    }
