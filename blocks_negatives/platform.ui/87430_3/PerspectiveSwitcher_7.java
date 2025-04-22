		psItem.addListener(SWT.MenuDetect, new Listener() {
			@Override
			public void handleEvent(org.eclipse.swt.widgets.Event event) {
				MPerspective persp = (MPerspective) event.widget.getData();
				openMenuFor(psItem, persp);
			}
