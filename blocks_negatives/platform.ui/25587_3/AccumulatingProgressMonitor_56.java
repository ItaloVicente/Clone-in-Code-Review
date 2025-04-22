		/**
		 * Create a new collector.
		 * 
		 * @param taskName
		 * @param subTask
		 * @param work
		 * @param monitor
		 */
		public Collector(String taskName, String subTask, double work,
				IProgressMonitor monitor) {
			this.taskName = taskName;
			this.subTask = subTask;
			this.worked = work;
			this.monitor = monitor;
		}

		/**
		 * Set the task name
		 * 
		 * @param name
		 */
		public void setTaskName(String name) {
			this.taskName = name;
		}
