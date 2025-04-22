			}
		});
		control.addListener(SWT.Activate, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (event.type == SWT.Activate) {
					handlerActivation(actions, service, handlerActivations,
							expression);
