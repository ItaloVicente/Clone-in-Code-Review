			if (!super.checkState()) {
				return false;
			}

			String preferenceName = getPreferenceName();
			if (preferenceName.equals(PreferenceConstants.SAMPLE_INTERVAL_MILLIS)) {
				if (longEventThreshold.isValid() &&
						getIntValue() >= longEventThreshold.getIntValue()) {
					showMessage(Messages.MonitoringPreferencePage_sample_interval_too_high_error);
					return false;
				}
			} else if (preferenceName.equals(PreferenceConstants.INITIAL_SAMPLE_DELAY_MILLIS)) {
				if (longEventThreshold.isValid() &&
						getIntValue() >= longEventThreshold.getIntValue()) {
					showMessage(Messages.MonitoringPreferencePage_initial_sample_delay_too_high_error);
					return false;
				}
			} else if (preferenceName.equals(PreferenceConstants.DEADLOCK_REPORTING_THRESHOLD_MILLIS)) {
				if (longEventThreshold.isValid() &&
						getIntValue() <= longEventThreshold.getIntValue()) {
					showMessage(Messages.MonitoringPreferencePage_deadlock_threshold_too_low_error);
