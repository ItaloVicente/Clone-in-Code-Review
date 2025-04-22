		Listener listener = e -> {
			switch (e.type) {
			case SWT.MouseEnter:
				hover = true;
				redraw();
				break;
			case SWT.MouseExit:
				hover = false;
				redraw();
				break;
			case SWT.KeyDown:
				onKeyDown(e);
				break;
