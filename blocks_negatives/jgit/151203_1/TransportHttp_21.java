		} else {
			if (!host.endsWith(cookieDomain)) {
				return false;
			}
			return host
					.charAt(host.length() - cookieDomain.length() - 1) == '.';
