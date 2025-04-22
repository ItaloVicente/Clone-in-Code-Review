	@Override
	public SubscribeConnection openSubscribe()
			throws NotSupportedException
		return new SmartHttpSubscribeConnection();
	}

