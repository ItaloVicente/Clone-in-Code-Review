		/** Milliseconds after which an ongoing long event should logged as a deadlock */
		public long deadlockThreshold;
		/**
		 * If true, includes call stacks of all threads into the logged message. Otherwise, only
		 * the stack of the watched thread is included.
		 */
		public boolean dumpAllThreads;
		/**
		 * If true, log freeze events to the Eclipse error log on the local machine.
		 */
