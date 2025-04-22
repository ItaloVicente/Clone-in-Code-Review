	public void execute(IProgressMonitor actMonitor) {
		if (operationResult != null)
			throw new IllegalStateException(CoreText.OperationAlreadyExecuted);

		List<URIish> urisToPush = new ArrayList<URIish>();
		urisToPush.addAll(rc.getPushURIs());
		if (urisToPush.isEmpty() && !rc.getURIs().isEmpty())
			urisToPush.add(rc.getURIs().get(0));

		IProgressMonitor monitor;
		if (actMonitor == null)
			monitor = new NullProgressMonitor();
		else
			monitor = actMonitor;

		final int totalWork = urisToPush.size() * WORK_UNITS_PER_TRANSPORT;
		if (dryRun)
			monitor.beginTask(CoreText.PushOperation_taskNameDryRun, totalWork);
		else
			monitor.beginTask(CoreText.PushOperation_taskNameNormalRun,
					totalWork);

		operationResult = new PushOperationResult();

		for (final URIish uri : urisToPush) {
			final SubProgressMonitor subMonitor = new SubProgressMonitor(
					monitor, WORK_UNITS_PER_TRANSPORT,
					SubProgressMonitor.PREPEND_MAIN_LABEL_TO_SUBTASK);
			Transport transport = null;
			try {
				if (monitor.isCanceled()) {
					operationResult.addOperationResult(uri,
							CoreText.PushOperation_resultCancelled);
					continue;
