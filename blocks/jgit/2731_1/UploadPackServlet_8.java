	static class Factory implements Filter {
		private final UploadPackFactory<HttpServletRequest> receivePackFactory;

		Factory(UploadPackFactory<HttpServletRequest> receivePackFactory) {
			this.receivePackFactory = receivePackFactory;
		}
