
		String getName() {
			String result = update.getRemoteName();
			if (StringUtils.isEmptyOrNull(result)) {
				result = update.getLocalName();
			}
			return result;
		}

		@Override
		public boolean isContainer() {
			switch (update.getResult()) {
			case FORCED:
				return !isPruned();
			case FAST_FORWARD:
				return true;
			default:
				return false;
			}
		}

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
