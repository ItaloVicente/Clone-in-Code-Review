			upstreamConfigComponent
					.addUpstreamConfigSelectionListener(new UpstreamConfigSelectionListener() {
						@Override
						public void upstreamConfigSelected(
										BranchRebaseMode newUpstreamConfig) {
							upstreamConfig = newUpstreamConfig;
							checkPage();
						}
					});
