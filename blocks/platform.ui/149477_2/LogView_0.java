		this.logReaderServiceTracker = new ServiceTracker<LogReaderService, LogReaderService>(context,
				LogReaderService.class, null) {
			@Override
			public LogReaderService addingService(ServiceReference<LogReaderService> reference) {
				LogReaderService service = context.getService(reference);
				if (service != null) {
					service.addLogListener(LogView.this);
				}
				return service;
			}

			@Override
			public void removedService(ServiceReference<LogReaderService> reference, LogReaderService service) {
				service.removeLogListener(LogView.this);
			}
		};
		this.logReaderServiceTracker.open();
