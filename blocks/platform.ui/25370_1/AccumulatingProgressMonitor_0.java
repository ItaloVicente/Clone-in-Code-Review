		public Collector(String taskName, String subTask, double work,
				IProgressMonitor monitor) {
			this.taskName = taskName;
			this.subTask = subTask;
			this.worked = work;
			this.monitor = monitor;
		}

		public void setTaskName(String name) {
			this.taskName = name;
		}
