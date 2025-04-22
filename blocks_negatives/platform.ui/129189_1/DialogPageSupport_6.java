				.addValueChangeListener(new IValueChangeListener() {
					@Override
					public void handleValueChange(ValueChangeEvent event) {
						statusProviderChanged();
					}
				});
		dialogPage.getShell().addListener(SWT.Dispose, new Listener() {
			@Override
			public void handleEvent(Event event) {
				dispose();
			}
		});
