			if (deadlockThreshold <= 0) {
				problems.append(NEW_LINE_AND_BULLET
						+ NLS.bind(Messages.EventLoopMonitorThread_deadlock_error_1,
								deadlockThreshold));
			} else if (deadlockThreshold <= longEventThreshold) {
				problems.append(NEW_LINE_AND_BULLET +
						NLS.bind(Messages.EventLoopMonitorThread_deadlock_threshold_too_low_error_2,
								deadlockThreshold, longEventThreshold));
