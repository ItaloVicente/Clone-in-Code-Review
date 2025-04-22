
		if (receivePackFactory == null)
			receivePackFactory = new ReceivePackFactory() {
				public ReceivePack create(HttpServletRequest req
						throws ServiceNotEnabledException {
					throw new ServiceNotEnabledException();
				}
			};

