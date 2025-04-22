		Policy.setLog(new ILogger() {
			@Override
			public void log(IStatus status) {
				log.add(status);
			}
		});
