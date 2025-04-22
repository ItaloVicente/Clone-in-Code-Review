	public static boolean isPublishSubscribe(String pathOrUri) {
		return pathOrUri != null && pathOrUri.equals(PUBLISH_SUBSCRIBE_PATH);
	}

	public static boolean isPublishSubscribe(HttpServletRequest req) {
		String uri = req.getRequestURI();
		return isPublishSubscribe(uri)
				&& PUBLISH_SUBSCRIBE_REQUEST_TYPE.equals(req.getContentType());
	}

