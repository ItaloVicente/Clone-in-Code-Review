			logService = new LogService() {
				@Override
				public void log(int level, String message) {
					log(null, level, message, null);
				}

				@Override
				public void log(int level, String message, Throwable exception) {
					log(null, level, message, exception);
				}

				@Override
				public void log(ServiceReference sr, int level, String message) {
					log(sr, level, message, null);
				}

				@Override
				public void log(ServiceReference sr, int level, String message, Throwable exception) {
					if (level == LogService.LOG_ERROR) {
					} else if (level == LogService.LOG_WARNING) {
					} else if (level == LogService.LOG_INFO) {
					} else if (level == LogService.LOG_DEBUG) {
					} else {
					}
					System.err.println(message);
					if (exception != null) {
						exception.printStackTrace(System.err);
					}
				}
			};
