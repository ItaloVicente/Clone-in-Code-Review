			if (initialSampleDelay <= 0) {
				problems.append(NEW_LINE_AND_BULLET +
						NLS.bind(Messages.EventLoopMonitorThread_sample_interval_error_1,
								initialSampleDelay));
			} else if (initialSampleDelay >= longEventThreshold) {
				problems.append(NEW_LINE_AND_BULLET +
						NLS.bind(Messages.EventLoopMonitorThread_initial_sample_delay_too_high_error_2,
								initialSampleDelay, longEventThreshold));
