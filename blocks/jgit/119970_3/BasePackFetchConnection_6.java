			FetchConfig cfg;
			try {
				cfg = getFetchConfig();
			} catch (ConfigIllegalValueException e) {
				throw new TransportException(e.getMessage()
			}

