		private static abstract class InterruptibleJob extends Job {

			public InterruptibleJob(String name) {
				super(name);
			}

			public void interrupt() {
				Thread thread = getThread();
				if (thread != null) {
					thread.interrupt();
