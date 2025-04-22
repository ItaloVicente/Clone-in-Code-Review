		fBindingText.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				if (!fBindingService.isKeyFilterEnabled()) {
					fBindingService.setKeyFilterEnabled(true);
				}
