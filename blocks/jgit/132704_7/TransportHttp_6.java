		StringBuilder cookieHeaderValue = new StringBuilder();
		for (HttpCookie cookie : relevantCookies) {
			if (!cookie.hasExpired()) {
				if (cookieHeaderValue.length() > 0) {
					cookieHeaderValue.append(';');
				}
				cookieHeaderValue.append(cookie.toString());
			}
		}
		if (cookieHeaderValue.length() >= 0) {
			conn.setRequestProperty(HDR_COOKIE
		}

