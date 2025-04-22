		this.upstreamConfigComponent = new BranchRebaseModeCombo(res);
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1)
				.align(SWT.BEGINNING, SWT.CENTER)
				.applyTo(upstreamConfigComponent.getViewer().getCombo());
		this.upstreamConfigComponent.getViewer().addSelectionChangedListener(
				(event) -> upstreamConfig = upstreamConfigComponent
						.getRebaseMode());
		if (upstreamConfig != null) {
			upstreamConfigComponent.setRebaseMode(upstreamConfig);
		}
