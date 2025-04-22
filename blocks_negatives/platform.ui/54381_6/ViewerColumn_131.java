		this.listener = new ILabelProviderListener() {

			@Override
			public void labelProviderChanged(LabelProviderChangedEvent event) {
				viewer.handleLabelProviderChanged(event);
			}

		};
		columnOwner.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				handleDispose(viewer);
			}
		});
