		int accelerator = actionToolbarProvider.getActionAccelerators()
				.get(action).intValue();
		if (accelerator == SWT.DEL) {
			setText(text + '\t'
					+ SWTKeySupport.getKeyFormatterForPlatform()
							.format(SWTKeySupport.convertAcceleratorToKeyStroke(
									accelerator)));
		}
		setAccelerator(accelerator);
