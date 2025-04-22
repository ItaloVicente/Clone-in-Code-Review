	public void writePack(ProgressMonitor compressMonitor
			ProgressMonitor writeMonitor
			throws IOException {
		if (compressMonitor == null)
			compressMonitor = NullProgressMonitor.INSTANCE;
		if (writeMonitor == null)
			writeMonitor = NullProgressMonitor.INSTANCE;

