				.addUpstreamConfigSelectionListener(new UpstreamConfigSelectionListener() {
					@Override
					public void upstreamConfigSelected(
							UpstreamConfig newUpstreamConfig) {
						upstreamConfig = newUpstreamConfig;
						checkPage();
					}
