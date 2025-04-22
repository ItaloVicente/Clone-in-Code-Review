		ProgressMonitor checking = NullProgressMonitor.INSTANCE;
		if (sideBand) {
			SideBandProgressMonitor m = new SideBandProgressMonitor(msgOut);
			m.setDelayStart(750
			checking = m;
		}
