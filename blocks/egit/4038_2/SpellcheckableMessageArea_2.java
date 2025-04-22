			public IContentAssistant getContentAssistant(ISourceViewer viewer) {
				if (!viewer.isEditable())
					return null;
				IContentAssistant assistant = createContentAssistant(viewer);
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
				return assistant;
			}

