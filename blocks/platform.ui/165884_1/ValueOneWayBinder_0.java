		@Override
		public ConfigStep<T, M> manualUpdate() {
			destination.setUpdatePolicy(UpdateValueStrategy.POLICY_ON_REQUEST);
			return this;
		}

