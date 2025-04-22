			String uriSpecificUserAgent = config.getString(HTTP
					USER_AGENT);
			if (uriSpecificUserAgent != null) {
				userAgent = uriSpecificUserAgent;
			}
			String[] uriSpecificExtraHeaders = config.getStringList(HTTP
					EXTRA_HEADER);
			if (uriSpecificExtraHeaders.length > 0) {
				extraHeaders = Arrays.asList(uriSpecificExtraHeaders);
			}
