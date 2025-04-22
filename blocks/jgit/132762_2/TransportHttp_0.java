	private static @Nullable HttpAuthMethod extractAuthMethodFromUrl(URL url) {
		if (url.getUserInfo() != null) {
			HttpAuthMethod basicAuthentication = HttpAuthMethod.Type.BASIC
					.method(null);
			String[] userInfoArray = url.getUserInfo().split(":"
			if (userInfoArray != null && userInfoArray.length == 2) {
				try {
					basicAuthentication.authorize(
							URLDecoder.decode(userInfoArray[0]
									StandardCharsets.US_ASCII.name())
							URLDecoder.decode(userInfoArray[1]
									StandardCharsets.US_ASCII.name()));
					return basicAuthentication;
				} catch (UnsupportedEncodingException e) {
					LOG.warn(
							"Could not decode the user info information from the given url"
							e);
				}
			} else {
				LOG.warn(
			}
		}
		return null;
	}

