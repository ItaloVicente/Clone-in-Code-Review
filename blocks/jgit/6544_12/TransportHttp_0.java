	@Override
	public SubscribeConnection openSubscribe(Subscriber subscriber)
			throws NotSupportedException
		SubscribeConnection conn = new SmartHttpSubscribeConnection();
		try {
			conn.doSubscribeAdvertisement(subscriber);
		} catch (IOException e) {
			throw new TransportException(uri
		}
		return conn;
	}

