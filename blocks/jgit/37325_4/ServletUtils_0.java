	public static String getEncodedPathInfo(HttpServletRequest req) {
		return getEncodedPathInfo(req.getServletPath()
				req.getContextPath());
	}

	static String getEncodedPathInfo(String servletPath
			String requestUri
		String pathInfo = requestUri.substring(contextPath.length())
				.replaceAll("/{2
		if (pathInfo.startsWith(servletPath)) {
			int servletPathLength = servletPath.length();
			pathInfo = pathInfo.substring(servletPathLength);
			if (pathInfo.isEmpty() && servletPathLength > 0) {
				return null;
			}
		} else {
			return null;
		}
		return pathInfo;
	}

