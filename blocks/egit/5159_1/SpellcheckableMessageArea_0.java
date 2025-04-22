		if (isEditable(sourceViewer)) {
			final IHandlerActivation handlerActivation = installQuickFixActionHandler();
			getTextWidget().addDisposeListener(new DisposeListener() {

				public void widgetDisposed(DisposeEvent e) {
					getHandlerService().deactivateHandler(handlerActivation);
				}
			});
		}
