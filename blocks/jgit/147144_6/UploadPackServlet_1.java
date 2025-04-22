	private final UploadPackErrorHandler handler;

	UploadPackServlet(@Nullable UploadPackErrorHandler handler) {
		this.handler = handler != null ? handler
				: this::defaultUploadPackHandler;
	}

