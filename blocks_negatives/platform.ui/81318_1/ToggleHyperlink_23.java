		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event e) {
				switch (e.type) {
					case SWT.MouseEnter:
						hover=true;
						redraw();
						break;
					case SWT.MouseExit:
						hover = false;
						redraw();
						break;
					case SWT.KeyDown:
						onKeyDown(e);
						break;
				}
