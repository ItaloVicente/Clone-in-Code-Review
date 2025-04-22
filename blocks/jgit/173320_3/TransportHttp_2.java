	private HttpAuthMethod authFromUri(URIish u) {
		String user = u.getUser();
		String pass = u.getPass();
		if (user != null && pass != null) {
			try {
				user = URLDecoder.decode(user.replace("+"
						StandardCharsets.UTF_8.name());
				pass = URLDecoder.decode(pass.replace("+"
						StandardCharsets.UTF_8.name());
				HttpAuthMethod basic = HttpAuthMethod.Type.BASIC.method(null);
				basic.authorize(user
				return basic;
			} catch (IllegalArgumentException
					| UnsupportedEncodingException e) {
				LOG.warn(JGitText.get().httpUserInfoDecodeError
			}
		}
		return HttpAuthMethod.Type.NONE.method(null);
	}

