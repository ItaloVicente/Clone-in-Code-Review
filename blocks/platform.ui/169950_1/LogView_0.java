		}
		FrameworkLogEntry betterInput = null;
		if (input instanceof ExtendedLogEntry) {
			ExtendedLogEntry logEntry = (ExtendedLogEntry) input;
			Object context = logEntry.getContext();
			if (context instanceof FrameworkLogEntry) {
				betterInput = (FrameworkLogEntry) context;
			}
		}
