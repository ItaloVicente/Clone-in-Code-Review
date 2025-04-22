	private Runnable updateJob = new Runnable() {
		@Override
		public void run() {
			doUpdate();
			updateScheduled = false;
		}
