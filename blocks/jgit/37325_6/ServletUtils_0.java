	public static String getEncodedPathInfo(HttpServletRequest req) {
		return getEncodedPathInfo(req.getContextPath()
				req.getRequestURI());
	}

	static String getEncodedPathInfo(String contextPath
			String requestUri) {
		String pathInfo = requestUri.substring(contextPath.length())
				.replaceAll("/{2
		if (!pathInfo.startsWith(servletPath))
			return null;
		pathInfo = pathInfo.substring(servletPath.length());
		if (pathInfo.isEmpty() && !servletPath.isEmpty())
			return null;
		return pathInfo;
	}

