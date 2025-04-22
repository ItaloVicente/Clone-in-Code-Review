		handler.upload(req
	}

	private void upload(HttpServletRequest req
			throws IOException
		@SuppressWarnings("resource")
		SmartOutputStream out = new SmartOutputStream(req
			@Override
			public void flush() throws IOException {
				doFlush();
			}
		};
		Repository repo = null;
		try (UploadPack up = (UploadPack) req.getAttribute(ATTRIBUTE_HANDLER)) {
