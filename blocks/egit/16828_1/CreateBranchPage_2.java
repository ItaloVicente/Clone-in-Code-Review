		upstreamConfigComponent = new UpstreamConfigComponent(
				main, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, false).span(3, 1)
				.applyTo(upstreamConfigComponent.getContainer());

		upstreamConfigComponent
				.addUpstreamConfigSelectionListener(new UpstreamConfigSelectionListener() {
					public void upstreamConfigSelected(
							UpstreamConfig newUpstreamConfig) {
						upstreamConfig = newUpstreamConfig;
						checkPage();
					}
				});
