		WalkFetchConnection r;
		try {
			r = new WalkFetchConnection(this
		} catch (ConfigIllegalValueException e) {
			throw new TransportException(e.getMessage()
		}
