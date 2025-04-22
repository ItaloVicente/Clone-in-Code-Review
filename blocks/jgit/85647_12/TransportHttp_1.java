	protected HttpConnection httpOpen(String method
			AcceptEncoding acceptEncoding) throws IOException {
		if (method == null || u == null || acceptEncoding == null) {
			throw new NullPointerException();
		}

