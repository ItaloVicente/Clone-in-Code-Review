
		try {
			bot.waitUntil(sfh, 120000);
		} finally {
			Job.getJobManager().removeJobChangeListener(sfh);
		}
	}

	private static class SynchronizeFinishHook extends JobChangeAdapter
			implements ICondition {
		private boolean state = false;

		public void done(IJobChangeEvent event) {
			if (event.getJob().belongsTo(
					ISynchronizeManager.FAMILY_SYNCHRONIZE_OPERATION))
				state = true;
		}

		public boolean test() throws Exception {
			return state;
		}

		public void init(SWTBot swtBot) {
		}

		public String getFailureMessage() {
			return null;
		}

