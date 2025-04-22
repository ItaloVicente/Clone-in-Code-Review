
		@Override
		public <F> ValueOneWayConvertStep<F, ?> fromComputedValue(Supplier<F> from) {
			return from(ComputedValue.create(from));
		}

		@Override
		public <F> ListOneWayConvertStep<F, ?> fromComputedList(Supplier<List<F>> from) {
			return from(ComputedList.create(from));
		}

		@Override
		public <F> SetOneWayConvertStep<F, ?> fromComputedSet(Supplier<Set<F>> from) {
			return from(ComputedSet.create(from));
		}
