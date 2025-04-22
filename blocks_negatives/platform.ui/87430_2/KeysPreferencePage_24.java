		textTriggerSequence.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				if (!bindingService.isKeyFilterEnabled()) {
					bindingService.setKeyFilterEnabled(true);
				}
