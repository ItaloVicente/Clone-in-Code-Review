		BatchRefUpdate batch;
		try {
			batch = transport.local.getRefDatabase()
					.newBatchUpdate()
					.setAllowNonFastForwards(true)
					.setRefLogMessage("fetch"
		} catch (ConfigIllegalValueException e) {
			throw new TransportException(e.getMessage()
		}

