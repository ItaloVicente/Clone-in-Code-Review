	private void appendResizeEventListener(CTabFolder folder) {
		if (hasResizeEventListener(folder)) {
			return;
		}

		final Listener resizeListener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				CTabFolder folder = (CTabFolder) event.widget;
				for (Control child : folder.getChildren()) {
					if (isReskinRequired(child)) {
						child.reskin(SWT.NONE);
					}
				}
			}
		};

		folder.addListener(SWT.Resize, resizeListener);
		folder.setData(RESIZE_LISTENER, resizeListener);
		folder.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				e.widget.removeListener(SWT.Resize, resizeListener);
			}
		});
	}

	private void removeResizeEventListener(CTabFolder folder) {
		Object obj = folder.getData(RESIZE_LISTENER);
		if (obj instanceof Listener) {
			folder.removeListener(SWT.Resize, (Listener) obj);
			folder.setData(RESIZE_LISTENER, null);
		}
	}

	private boolean hasResizeEventListener(CTabFolder folder) {
		return folder.getData(RESIZE_LISTENER) instanceof Listener;
	}

	private boolean isReskinRequired(Control control) {
		if (control instanceof Composite) {
			Composite composite = (Composite) control;
			return composite.isVisible() && composite.getChildren().length > 0;
		}
		return false;
	}
