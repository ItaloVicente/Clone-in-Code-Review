			@Override
			public boolean shouldSchedule() {
				return super.shouldSchedule() && !isDisposed();
			}

			@Override
			public boolean shouldRun() {
				return super.shouldRun() && !isDisposed();
			}

