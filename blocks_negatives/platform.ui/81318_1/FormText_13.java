		});
		addListener(SWT.Traverse, new Listener() {
			@Override
			public void handleEvent(Event e) {
				if (DEBUG_FOCUS)
				switch (e.detail) {
				case SWT.TRAVERSE_PAGE_NEXT:
				case SWT.TRAVERSE_PAGE_PREVIOUS:
				case SWT.TRAVERSE_ARROW_NEXT:
				case SWT.TRAVERSE_ARROW_PREVIOUS:
					e.doit = false;
					return;
				}
				if (!model.hasFocusSegments()) {
					e.doit = true;
					return;
				}
				if (e.detail == SWT.TRAVERSE_TAB_NEXT)
					e.doit = advance(true);
				else if (e.detail == SWT.TRAVERSE_TAB_PREVIOUS)
					e.doit = advance(false);
				else if (e.detail != SWT.TRAVERSE_RETURN)
					e.doit = true;
