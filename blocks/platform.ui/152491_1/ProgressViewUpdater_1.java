	class FinishedJobsListener implements KeptJobsListener {
		@Override
		public void finished(JobTreeElement jte) {
			currentInfo.keptFinished(jte);
			throttledUpdate.throttledExec();
		}

		@Override
		public void removed(JobTreeElement jte) {
			if (jte == null) {
				currentInfo.updateAll = true;
			} else {
				currentInfo.keptRemoved(jte);
			}
			throttledUpdate.throttledExec();
		}
	}

