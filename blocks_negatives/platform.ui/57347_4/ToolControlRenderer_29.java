			hideItem.addListener(SWT.Selection, new Listener() {
				@Override
				public void handleEvent(org.eclipse.swt.widgets.Event event) {
					toolControl.getTags().add(
							IPresentationEngine.HIDDEN_EXPLICITLY);
				}
			});
