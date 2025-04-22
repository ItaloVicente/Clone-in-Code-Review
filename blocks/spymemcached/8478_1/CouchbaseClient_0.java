	@Override
	public void reconfigure(Bucket bucket) {
		reconfiguring = true;
		try {
			mconn.reconfigure(bucket);
			cconn.reconfigure(bucket);
		} catch (IllegalArgumentException ex) {
			getLogger().warn("Failed to reconfigure client, staying with previous configuration.", ex);
		} finally {
			reconfiguring = false;
		}
	}

