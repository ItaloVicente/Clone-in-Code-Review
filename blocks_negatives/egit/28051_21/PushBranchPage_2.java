				.applyTo(branchNameText);
		branchNameText.setText(getSuggestedBranchName());

		upstreamConfigComponent = new UpstreamConfigComponent(inputPanel,
				SWT.NONE);
		upstreamConfigComponent.getContainer().setLayoutData(
				GridDataFactory.fillDefaults().grab(true, false).span(3, 1)
						.create());
		upstreamConfigComponent
				.addUpstreamConfigSelectionListener(new UpstreamConfigSelectionListener() {
					public void upstreamConfigSelected(
							UpstreamConfig newUpstreamConfig) {
						upstreamConfig = newUpstreamConfig;
						checkPage();
					}
				});
