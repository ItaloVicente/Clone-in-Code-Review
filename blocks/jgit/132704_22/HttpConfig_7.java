			String urlSpecificCookieFile = config.getString(HTTP
					COOKIE_FILE_KEY);
			if (urlSpecificCookieFile != null) {
				cookieFile = urlSpecificCookieFile;
			}
			saveCookies = config.getBoolean(HTTP
					saveCookies);
