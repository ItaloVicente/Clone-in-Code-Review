	protected Transport(final Repository local
			throws TransportException {
		TransferConfig tc;
		try {
			tc = local.getConfig().get(TransferConfig.KEY);
		} catch (ConfigIllegalValueException e) {
			throw new TransportException(e.getMessage()
		}

