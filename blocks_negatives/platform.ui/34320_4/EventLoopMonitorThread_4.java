
		loadLoggerExtensions();

		if (!logToErrorLog && externalLoggers.isEmpty()) {
			MonitoringPlugin.logWarning(Messages.EventLoopMonitorThread_logging_disabled_error);
		}
