			if (!(handler instanceof ResourceHandler) || ((ResourceHandler) handler).hasTopLevelWindows()) {
				handler.save();
			} else {
				Logger logger = new WorkbenchLogger(PLUGIN_ID);
				logger.error(
						new Exception(), // log a stack trace for debugging
			}
