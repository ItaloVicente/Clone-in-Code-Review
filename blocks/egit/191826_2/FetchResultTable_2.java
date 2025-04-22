		@Override
		public ISchedulingRule getRule(Object object) {
			return this;
		}

		@Override
		public boolean contains(ISchedulingRule rule) {
			return this == rule;
		}

		@Override
		public boolean isConflicting(ISchedulingRule rule) {
			return this == rule;
		}
	}

	private class FetchResultAdapter extends WorkbenchAdapter
			implements IDeferredWorkbenchAdapter, ISchedulingRule {
