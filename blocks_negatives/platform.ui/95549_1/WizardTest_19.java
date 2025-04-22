			Policy.setLog(new ILogger() {
				@Override
				public void log(IStatus status) {
					logged[0] = true;
				}
			});
