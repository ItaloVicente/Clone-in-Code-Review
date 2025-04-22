        if (watchService != null) {
            try {
                watchService.close();
            } catch (final Exception ex) {
                LOGGER.error("Can't close watch service [" + toString() + "]",
                             ex);
            }
        }
    }

    public void shutdown() {
        fsWatchServices.keySet().forEach(key -> this.close(key));
