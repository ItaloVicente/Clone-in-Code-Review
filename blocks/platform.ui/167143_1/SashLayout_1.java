		};

		host.getDisplay().addFilter(SWT.MouseMove, mouseMoveListener);
		host.getDisplay().addFilter(SWT.MouseExit, mouseExitListener);
		host.getDisplay().addFilter(SWT.MouseUp, mouseUpListener);
		host.getDisplay().addFilter(SWT.MouseDown, mouseDownListener);

		host.addDisposeListener(e -> {
			host.getDisplay().removeFilter(SWT.MouseMove, mouseMoveListener);
			host.getDisplay().removeFilter(SWT.MouseExit, mouseExitListener);
			host.getDisplay().removeFilter(SWT.MouseUp, mouseUpListener);
			host.getDisplay().removeFilter(SWT.MouseDown, mouseDownListener);
