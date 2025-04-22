	@Override
	public void applyStyles(Object element, boolean applyStylesToChildNodes,
			boolean computeDefaultStyle) {
		super.applyStyles(element, applyStylesToChildNodes, computeDefaultStyle);

		if (needsRefreshListeners(element)) {
			initRefreshListeners((Widget) element);
		}
	}

	private boolean needsRefreshListeners(Object element) {
		return element instanceof Tree;
	}

	private void initRefreshListeners(Widget widget) {
		if (widget.getData(HAS_REFRESH_LISTENERS) != null) {
			return; // already initialized;
		}
		widget.setData(HAS_REFRESH_LISTENERS, true);

		Listener focusInListener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				event.widget.setData(NEEDS_REDRAW, true);
			}
		};
		widget.addListener(SWT.FocusIn, focusInListener);

		Listener selectionChangedListener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (event.widget.getData(NEEDS_REDRAW) != null) {
					event.widget.setData(NEEDS_REDRAW, null);
					if (event.widget instanceof Control) {
						((Control) event.widget).redraw();
					}
				}
			}
		};
		widget.addListener(SWT.Selection, selectionChangedListener);
	}
