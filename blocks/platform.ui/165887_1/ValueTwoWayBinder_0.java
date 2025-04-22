
		@Override
		public ConfigStep<T, M> targetUpdatePolicy(ValueUpdatePolicy policy) {
			toTarget.setUpdatePolicy(policy.getPolicy());
			return this;
		}

		@Override
		public ConfigStep<T, M> modelUpdatePolicy(ValueUpdatePolicy policy) {
			toModel.setUpdatePolicy(policy.getPolicy());
			return this;
		}
