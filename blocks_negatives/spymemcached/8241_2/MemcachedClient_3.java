	protected void logRunException(Exception e) {
		if(shuttingDown) {
			getLogger().debug("Exception occurred during shutdown", e);
		} else {
			getLogger().warn("Problem handling memcached IO", e);
		}
	}

	/**
	 * Infinitely loop processing IO.
	 */
	@Override
	public void run() {
		while(running) {
            try {
                mconn.handleIO();
            } catch (IOException e) {
                logRunException(e);
            } catch (CancelledKeyException e) {
                logRunException(e);
            } catch (ClosedSelectorException e) {
                logRunException(e);
            } catch (IllegalStateException e) {
                logRunException(e);
            }
		}
		getLogger().info("Shut down memcached client");
	}

