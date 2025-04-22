				.applyTo(remoteBranchNameText);
		remoteBranchNameText.setText(getSuggestedBranchName());

		if (this.ref != null) {
			upstreamConfigComponent = new UpstreamConfigComponent(inputPanel,
					SWT.NONE);
			upstreamConfigComponent.getContainer().setLayoutData(
					GridDataFactory.fillDefaults().grab(true, false).span(3, 1)
							.indent(SWT.DEFAULT, 20).create());
			upstreamConfigComponent
					.addUpstreamConfigSelectionListener(new UpstreamConfigSelectionListener() {
						public void upstreamConfigSelected(
								UpstreamConfig newUpstreamConfig) {
							upstreamConfig = newUpstreamConfig;
							checkPage();
						}
					});
			setDefaultUpstreamConfig();
		}
