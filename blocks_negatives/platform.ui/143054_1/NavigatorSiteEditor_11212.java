		textEditor.addListener(SWT.Traverse, new Listener() {
			@Override
			public void handleEvent(Event event) {
				switch (event.detail) {
					case SWT.TRAVERSE_ESCAPE :
						disposeTextWidget();
						event.doit = true;
						event.detail = SWT.TRAVERSE_NONE;
						break;
					case SWT.TRAVERSE_RETURN :
						saveChangesAndDispose(runnable);
						event.doit = true;
						event.detail = SWT.TRAVERSE_NONE;
						break;
				}
