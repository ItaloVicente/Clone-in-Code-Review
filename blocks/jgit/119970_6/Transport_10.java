	protected Transport(Repository local
			throws TransportException {
		final TransferConfig tc;
		try {
			tc = local.getConfig().get(TransferConfig.KEY);
		} catch (ConfigIllegalValueException e) {
			throw new TransportException(e.getMessage()
		}

