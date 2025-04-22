		userAgent = config.getString(HTTP
		String[] headers = config.getStringList(HTTP
		int start = findLastEmpty(headers) + 1;
		if (start > 0) {
			headers = Arrays.copyOfRange(headers
		}
		extraHeaders = Arrays.asList(headers);
