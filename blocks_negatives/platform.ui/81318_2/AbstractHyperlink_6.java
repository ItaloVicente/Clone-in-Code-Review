		addListener(SWT.Traverse, new Listener() {
			@Override
			public void handleEvent(Event e) {
				switch (e.detail) {
				case SWT.TRAVERSE_PAGE_NEXT:
				case SWT.TRAVERSE_PAGE_PREVIOUS:
				case SWT.TRAVERSE_ARROW_NEXT:
				case SWT.TRAVERSE_ARROW_PREVIOUS:
				case SWT.TRAVERSE_RETURN:
					e.doit = false;
					return;
				}
				e.doit = true;
			}
		});
		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event e) {
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
				}
