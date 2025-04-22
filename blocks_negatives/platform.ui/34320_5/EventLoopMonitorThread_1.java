			if (!(longEventThreshold > 0)) {
				problems.append(NEW_LINE_AND_BULLET +
						NLS.bind(Messages.EventLoopMonitorThread_logging_threshold_error_1,
								longEventThreshold));
			}
			if (sampleInterval <= 0) {
				problems.append(NEW_LINE_AND_BULLET +
						NLS.bind(Messages.EventLoopMonitorThread_sample_interval_error_1,
								sampleInterval));
			} else if (sampleInterval >= longEventThreshold) {
