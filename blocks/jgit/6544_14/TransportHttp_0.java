	@Override
	public SubscribeConnection openSubscribe(SubscriptionState subscriber)
			throws NotSupportedException
		SubscribeConnection conn = new SmartHttpSubscribeConnection();
		try {
			conn.sendSubscribeAdvertisement(subscriber);
		} catch (TransportException e) {
			throw e;
		} catch (IOException e) {
			throw new TransportException(uri
		}
		return conn;
	}

