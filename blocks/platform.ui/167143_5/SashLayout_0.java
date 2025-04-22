		Listener mouseMoveListener = this::onMouseMove;
		Listener mouseUpListener = this::onMouseUp;
		Listener mouseDownListener = this::onMouseDown;
		Listener dragDetectListener = this::onDragDetect;
		Listener activateListener = this::onActivate;

		host.getDisplay().addFilter(SWT.MouseMove, mouseMoveListener);
		host.getDisplay().addFilter(SWT.MouseUp, mouseUpListener);
		host.getDisplay().addFilter(SWT.MouseDown, mouseDownListener);
		host.getDisplay().addFilter(SWT.DragDetect, dragDetectListener);
		host.getDisplay().addFilter(SWT.Activate, activateListener);

		host.addDisposeListener(e -> {
			host.getDisplay().removeFilter(SWT.MouseMove, mouseMoveListener);
			host.getDisplay().removeFilter(SWT.MouseUp, mouseUpListener);
			host.getDisplay().removeFilter(SWT.MouseDown, mouseDownListener);
			host.getDisplay().removeFilter(SWT.DragDetect, dragDetectListener);
			host.getDisplay().removeFilter(SWT.Activate, activateListener);
