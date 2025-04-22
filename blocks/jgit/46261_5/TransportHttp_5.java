			followRedirects = rc.getEnum(HttpRedirectMode.values()
					null
			int redirectLimit = rc.getInt("http"
					MAX_REDIRECTS);
			if (redirectLimit < 0) {
				redirectLimit = MAX_REDIRECTS;
			}
			maxRedirects = redirectLimit;
