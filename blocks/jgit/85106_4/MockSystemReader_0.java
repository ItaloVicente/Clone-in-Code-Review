	@Override
	public Clock getClock() {
		return new Clock() {
			@Override
			public ProposedTimestamp propose() {
				long t = getCurrentTime();
				return new ProposedTimestamp() {
					@Override
					public long read(TimeUnit unit) {
						return unit.convert(t
					}

					@Override
					public void blockUntil(long timeout
							throws InterruptedException
					}
				};
			}
		};
	}

