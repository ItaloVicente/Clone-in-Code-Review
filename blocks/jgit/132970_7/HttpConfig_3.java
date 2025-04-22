		String agent = config.getString(HTTP
		if (agent != null) {
			agent = UserAgent.clean(agent);
		}
		userAgent = agent;
		String[] headers = config.getStringList(HTTP
		int start = findLastEmpty(headers) + 1;
		if (start > 0) {
			headers = Arrays.copyOfRange(headers
		}
		extraHeaders = Arrays.asList(headers);
