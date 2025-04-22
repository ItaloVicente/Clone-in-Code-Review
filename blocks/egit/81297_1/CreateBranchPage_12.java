	private void updateUpstreamComponent() {
		upstreamConfigComponent.setUpstreamConfig(upstreamConfig);

		boolean showUpstreamConfig = sourceRefName.startsWith(Constants.R_HEADS)
				|| sourceRefName.startsWith(Constants.R_REMOTES);
		Composite container = upstreamConfigComponent.getContainer();
		GridData gd = (GridData) container.getLayoutData();
		if (gd.exclude == showUpstreamConfig) {
			gd.exclude = !showUpstreamConfig;
			container.setVisible(showUpstreamConfig);
			container.getParent().layout(true);
			ensurePreferredHeight(getShell());
		}
	}

