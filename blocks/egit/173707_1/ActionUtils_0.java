				handlerActivation(actions, service, handlerActivations,
						expression);
			}

		});
		control.addListener(SWT.Deactivate, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (event.type == SWT.Deactivate) {
					handlerDeactivation(service, handlerActivations);
