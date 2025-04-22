		this.disposeListener = new IDisposeListener() {
			@Override
			public void handleDispose(DisposeEvent staleEvent) {
				Binding.this.context.getValidationRealm().exec(new Runnable() {
					@Override
					public void run() {
						if (!isDisposed())
							dispose();
					}
				});
			}
		};
