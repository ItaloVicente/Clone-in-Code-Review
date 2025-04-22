	public static String getEncodedPathInfo(HttpServletRequest req) {
		String servletPath = req.getServletPath();
		int servletPathLength = servletPath.length();
		String requestUri = req.getRequestURI();
		String pathInfo = requestUri.substring(req.getContextPath().length())
				.replaceAll("[/]{2
		if (pathInfo.startsWith(servletPath)) {
			pathInfo = pathInfo.substring(servletPathLength);
			if (pathInfo.isEmpty() && servletPathLength > 0) {
				pathInfo = null;
			}
		} else {
			pathInfo = null;
		}
		return pathInfo;
	}

