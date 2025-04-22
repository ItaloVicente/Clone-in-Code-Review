				if (assistant != null) {
					final IHandlerActivation activation = installContentAssistActionHandler();
					viewer.getTextWidget().addDisposeListener(
							new DisposeListener() {

								public void widgetDisposed(DisposeEvent e) {
									getHandlerService().deactivateHandler(
											activation);
								}
							});
				}
