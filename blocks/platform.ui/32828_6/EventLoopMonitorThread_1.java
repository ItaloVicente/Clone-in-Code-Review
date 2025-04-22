			if (sampleInterval <= 0) {
				problems.append(NEW_LINE_AND_BULLET +
						NLS.bind(Messages.EventLoopMonitorThread_sample_interval_error_1,
								sampleInterval));
			} else if (sampleInterval >= longEventThreshold) {
				problems.append(NEW_LINE_AND_BULLET +
						NLS.bind(Messages.EventLoopMonitorThread_sample_interval_too_high_error_2,
								sampleInterval, longEventThreshold));
