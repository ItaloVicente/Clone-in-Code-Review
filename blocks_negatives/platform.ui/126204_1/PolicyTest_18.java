			Policy.setLog(new ILogger() {
				@Override
				public void log(IStatus status) {
					statusHolder[0] = status;
				}
			});
