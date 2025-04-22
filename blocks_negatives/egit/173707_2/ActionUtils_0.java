		control.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				if (!handlerActivations.isEmpty()) {
					service.deactivateHandlers(handlerActivations);
					handlerActivations.clear();
				}
			}

