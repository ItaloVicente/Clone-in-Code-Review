			throws InvocationTargetException, InterruptedException {
		this.monitor = progressMonitor;

		try {
			initialize();
		} catch (IOException e) {
			throw new InvocationTargetException(e, NLS.bind(DataTransferMessages.ZipExport_cannotOpen, e.getMessage()));
		}

		try {
			int totalWork = IProgressMonitor.UNKNOWN;
			try {
				if (resourcesToExport == null) {
