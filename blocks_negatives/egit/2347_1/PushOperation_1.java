
		for (final URIish uri : specification.getURIs()) {
			final SubProgressMonitor subMonitor = new SubProgressMonitor(
					monitor, WORK_UNITS_PER_TRANSPORT,
					SubProgressMonitor.PREPEND_MAIN_LABEL_TO_SUBTASK);
			Transport transport = null;
			try {
				if (monitor.isCanceled()) {
					operationResult.addOperationResult(uri,
							CoreText.PushOperation_resultCancelled);
					continue;
