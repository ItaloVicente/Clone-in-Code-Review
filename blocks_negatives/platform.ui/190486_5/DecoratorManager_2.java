		SafeRunner.run(new SafeRunnable() {
			@Override
			public void run() {
				listener.labelProviderChanged(event);
			}
		});

