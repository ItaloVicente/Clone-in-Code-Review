	/**
	 * Infinitely loop processing IO.
	 */
	@Override
	public void run() {
		while(running) {
            if (!reconfiguring) {
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
		}
		getLogger().info("Shut down membase client");
	}

