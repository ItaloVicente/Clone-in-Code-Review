		Listener listener = e -> {
			switch (e.type) {
			case SWT.FocusIn:
				hasFocus = true;
				handleEnter(e);
				break;
			case SWT.FocusOut:
				hasFocus = false;
				handleExit(e);
				break;
			case SWT.DefaultSelection:
				handleActivate(e);
				break;
			case SWT.MouseEnter:
				handleEnter(e);
				break;
			case SWT.MouseExit:
				handleExit(e);
				break;
			case SWT.MouseDown:
				handleMouseDown(e);
				break;
			case SWT.MouseUp:
				handleMouseUp(e);
				break;
			case SWT.MouseMove:
				handleMouseMove(e);
				break;
