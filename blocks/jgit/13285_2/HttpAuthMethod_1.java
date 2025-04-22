	static HttpAuthMethod scanResponse(final HttpURLConnection conn) {
		final Map<String
		HttpAuthMethod authentication = NONE;

		for (final Entry<String
			if (HDR_WWW_AUTHENTICATE.equalsIgnoreCase(entry.getKey())) {
				if (entry.getValue() != null) {
					for (final String value : entry.getValue()) {
						if (value != null && value.length() != 0) {
							final String[] valuePart = value.split(
									SCHEMA_NAME_SEPARATOR

							if (Digest.NAME.equalsIgnoreCase(valuePart[0])) {
								final String param;
								if (valuePart.length == 1)
									param = EMPTY_STRING;
								else
									param = valuePart[1];

								authentication = new Digest(param);
								break;
							}

							if (Basic.NAME.equalsIgnoreCase(valuePart[0]))
								authentication = new Basic();
						}
					}
				}
				break;
			}
		}

		return authentication;
