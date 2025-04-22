
	public void setProgressMonitor(ProgressMonitor monitor) {
		if (monitor == null) {
			this.monitor = NullProgressMonitor.INSTANCE;
		} else {
			this.monitor = monitor;
		}
	}
