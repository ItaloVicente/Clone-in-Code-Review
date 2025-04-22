			if (maxStackSamples <= 0) {
				problems.append(NEW_LINE_AND_BULLET +
						NLS.bind(Messages.EventLoopMonitorThread_max_log_count_error_1,
								maxStackSamples));
			}
			if (initialSampleDelay <= 0) {
				problems.append(NEW_LINE_AND_BULLET +
						NLS.bind(Messages.EventLoopMonitorThread_sample_interval_error_1,
								initialSampleDelay));
			} else if (initialSampleDelay >= longEventThreshold) {
