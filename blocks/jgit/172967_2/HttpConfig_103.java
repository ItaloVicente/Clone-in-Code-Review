			String uriSpecificUserAgent = config.getString(HTTP
					USER_AGENT);
			if (uriSpecificUserAgent != null) {
				userAgent = UserAgent.clean(uriSpecificUserAgent);
			}
			String[] uriSpecificExtraHeaders = config.getStringList(HTTP
					EXTRA_HEADER);
			if (uriSpecificExtraHeaders.length > 0) {
				start = findLastEmpty(uriSpecificExtraHeaders) + 1;
				if (start > 0) {
					uriSpecificExtraHeaders = Arrays.copyOfRange(
							uriSpecificExtraHeaders
							uriSpecificExtraHeaders.length);
				}
				extraHeaders = Arrays.asList(uriSpecificExtraHeaders);
			}
