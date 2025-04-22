	@Override
	public MonotonicClock getClock() {
		return new MonotonicClock() {
			@Override
			public ProposedTimestamp propose() {
				long t = getCurrentTime();
				return new ProposedTimestamp() {
					@Override
					public long read(TimeUnit unit) {
						return unit.convert(t
					}

					@Override
					public void blockUntil(Duration maxWait) {
					}
				};
			}
		};
	}

