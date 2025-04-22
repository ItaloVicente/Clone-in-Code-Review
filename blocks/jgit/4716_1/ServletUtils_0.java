	public static void consumeRequestBody(HttpServletRequest req) {
		if (0 < req.getContentLength() || isChunked(req)) {
			try {
				consumeRequestBody(req.getInputStream());
			} catch (IOException e) {
			}
		}
	}

	private static boolean isChunked(HttpServletRequest req) {
		return "chunked".equals(req.getHeader("Transfer-Encoding"));
	}

	public static void consumeRequestBody(InputStream in) {
		if (in == null)
			return;
		try {
			while (0 < in.skip(2048) || 0 <= in.read()) {
			}
		} catch (IOException err) {
		} finally {
			try {
				in.close();
			} catch (IOException err) {
			}
		}
	}

