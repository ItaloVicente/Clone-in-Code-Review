		Listener listener = new Listener() {
			@Override
			public void handleEvent(Event e) {
				switch (e.type) {
				case SWT.FocusIn:
					if (!controlFocusTransfer)
						syncControlSegmentFocus((Control) e.widget);
					break;
				case SWT.Traverse:
					if (DEBUG_FOCUS)
					switch (e.detail) {
					case SWT.TRAVERSE_PAGE_NEXT:
					case SWT.TRAVERSE_PAGE_PREVIOUS:
					case SWT.TRAVERSE_ARROW_NEXT:
					case SWT.TRAVERSE_ARROW_PREVIOUS:
						e.doit = false;
						return;
					}
					Control c = (Control) e.widget;
					ControlSegment segment = (ControlSegment) c
							.getData(CONTROL_KEY);
					if (e.detail == SWT.TRAVERSE_TAB_NEXT)
						e.doit = advanceControl(c, segment, true);
					else if (e.detail == SWT.TRAVERSE_TAB_PREVIOUS)
						e.doit = advanceControl(c, segment, false);
					if (!e.doit)
						e.detail = SWT.TRAVERSE_NONE;
					break;
