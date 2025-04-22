	ReceivePackFactory<?> DISABLED = new ReceivePackFactory<Object>() {
		@Override
		public ReceivePack create(Object req, Repository db)
				throws ServiceNotEnabledException {
			throw new ServiceNotEnabledException();
		}
	};
