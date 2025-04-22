			upstreamConfigComponent.setUpstreamConfig(upstreamConfig);

			String source = branchCombo.getText();
			boolean showUpstreamConfig = source.startsWith(Constants.R_HEADS)
					|| source.startsWith(Constants.R_REMOTES);
			Composite container = upstreamConfigComponent.getContainer();
			GridData gd = (GridData) container.getLayoutData();
			if (gd.exclude == showUpstreamConfig) {
				gd.exclude = !showUpstreamConfig;
				container.setVisible(showUpstreamConfig);
				container.getParent().layout(true);
