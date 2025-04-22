		Long timestamp1 = ((Long) s1
				.getProperty(IStatusAdapterConstants.TIMESTAMP_PROPERTY));
		Long timestamp2 = ((Long) s2
				.getProperty(IStatusAdapterConstants.TIMESTAMP_PROPERTY));
		if (timestamp1 == null || timestamp2 == null
				|| (timestamp1.equals(timestamp2))) {
