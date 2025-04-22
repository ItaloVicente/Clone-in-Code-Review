	/**
	 * Creates a collector object to accumulate work and subtask calls.
	 * 
	 * @param subTask
	 * @param work
	 */
	private void createCollector(String taskName, String subTask, double work) {
		collector = new Collector(taskName, subTask, work,
				getWrappedProgressMonitor());
		display.asyncExec(collector);
	}
