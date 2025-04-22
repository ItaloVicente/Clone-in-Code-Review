		ExtendedLogReaderService log = appContext.get(ExtendedLogReaderService.class);
		LogFilter logFilter = (bundle, loggerName, logLevel) -> {
			return "org.eclipse.e4.ui.internal.workbench.ModelAssembler".equals(loggerName);
		};

		log.addLogListener(logListener, logFilter);
