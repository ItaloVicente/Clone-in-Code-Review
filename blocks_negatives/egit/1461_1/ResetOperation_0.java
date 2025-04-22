		if (type != ResetType.SOFT) {
			if (type == ResetType.MIXED)
				resetIndex();
			else
				readIndex();
			writeIndex();
		}
		monitor.worked(1);

		if (type == ResetType.HARD) {
