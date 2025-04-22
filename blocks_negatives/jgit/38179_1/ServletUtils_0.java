	/**
	 * Get the path info component of the request. The result is similar to
	 * {@link HttpServletRequest#getPathInfo()}, but URL-encoded characters are
	 * not decoded.
	 *
	 * @param req
	 *            the incoming request.
	 * @return the same value as {@link HttpServletRequest#getPathInfo()}, but
	 *         without decoding URL-encoded characters.
	 * @since 3.6
	 */
	public static String getEncodedPathInfo(HttpServletRequest req) {
		return getEncodedPathInfo(req.getContextPath(), req.getServletPath(),
				req.getRequestURI());
	}

	/**
	 * Get the path info component of the request. The result is similar to
	 * {@link HttpServletRequest#getPathInfo()}, but URL-encoded characters are
	 * not decoded.
	 *
	 * @param contextPath
	 *            the context path from the incoming request.
	 * @param servletPath
	 *            the servlet path from the incoming request.
	 * @param requestUri
	 *            the request URI from the incoming request.
	 * @return the same value as {@link HttpServletRequest#getPathInfo()}, but
	 *         without decoding URL-encoded characters.
	 */
	static String getEncodedPathInfo(String contextPath, String servletPath,
			String requestUri) {
		String pathInfo = requestUri.substring(contextPath.length())
				.replaceAll("/{2,}", "/");
		if (!pathInfo.startsWith(servletPath))
			return null;
		pathInfo = pathInfo.substring(servletPath.length());
		if (pathInfo.isEmpty() && !servletPath.isEmpty())
			return null;
		return pathInfo;
	}

