		int accelerator = actionToolbarProvider.getActionAccelerators()
				.get(action).intValue();
		setAccelerator(accelerator);
		if (accelerator == SWT.DEL) {
			setText(text + '\t'
					+ SWTKeySupport.getKeyFormatterForPlatform()
							.format(
					SWTKeySupport.convertAcceleratorToKeyStroke(accelerator)));
		}
