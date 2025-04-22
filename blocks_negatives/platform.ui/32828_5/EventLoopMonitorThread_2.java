			StringBuilder error = new StringBuilder();
			if (!(this.loggingThreshold > 0)) {
				error.append(NEW_LINE_AND_BULLET + NLS.bind(
						Messages.EventLoopMonitorThread_logging_threshold_error_1, this.loggingThreshold));
			}
			if (!(this.minimumPollingDelay > 0)) {
				error.append(NEW_LINE_AND_BULLET + NLS.bind(
						Messages.EventLoopMonitorThread_sample_interval_error_1, this.minimumPollingDelay));
