		@Override
		public ConfigStep<T, M> convertOnly() {
			toTarget.setUpdatePolicy(UpdateValueStrategy.POLICY_CONVERT);
			return this;
		}

