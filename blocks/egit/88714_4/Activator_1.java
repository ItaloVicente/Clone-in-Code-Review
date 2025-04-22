		@Override
		public boolean shouldSchedule() {
			return doReschedule;
		}

		@Override
		public boolean shouldRun() {
			return doReschedule;
		}
