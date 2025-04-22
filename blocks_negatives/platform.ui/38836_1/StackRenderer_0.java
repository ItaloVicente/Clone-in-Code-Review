		ctf.addListener(SWT.Activate, new org.eclipse.swt.widgets.Listener() {
			public void handleEvent(org.eclipse.swt.widgets.Event event) {
				CTabFolder ctf = (CTabFolder) event.widget;
				MElementContainer<MUIElement> stack = (MElementContainer<MUIElement>) ctf
						.getData(OWNING_ME);
				activateStack(stack);
			}
		});

