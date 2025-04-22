	UploadPackFactory<?> DISABLED = new UploadPackFactory<Object>() {
		@Override
		public UploadPack create(Object req, Repository db)
				throws ServiceNotEnabledException {
			throw new ServiceNotEnabledException();
		}
