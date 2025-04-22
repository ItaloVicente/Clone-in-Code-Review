		this.upstreamConfigComponent = new BranchRebaseModeCombo(res);
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1)
				.align(SWT.BEGINNING, SWT.CENTER)
				.applyTo(upstreamConfigComponent.getViewer().getCombo());
		this.upstreamConfigComponent.getViewer()
				.addSelectionChangedListener(new ISelectionChangedListener() {

					@Override
					public void selectionChanged(SelectionChangedEvent event) {
						upstreamConfig = upstreamConfigComponent
								.getRebaseMode();
					}
