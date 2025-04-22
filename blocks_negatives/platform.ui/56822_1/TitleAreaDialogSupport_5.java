				.addValueChangeListener(new IValueChangeListener() {
					@Override
					public void handleValueChange(ValueChangeEvent event) {
						statusProviderChanged();
					}
				});
		dialog.getShell().addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				dispose();
			}
		});
