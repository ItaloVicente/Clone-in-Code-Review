				Control c = (Control) e.widget;
				ControlSegment segment = (ControlSegment) c.getData(CONTROL_KEY);
				if (e.detail == SWT.TRAVERSE_TAB_NEXT)
					e.doit = advanceControl(c, segment, true);
				else if (e.detail == SWT.TRAVERSE_TAB_PREVIOUS)
					e.doit = advanceControl(c, segment, false);
				if (!e.doit)
					e.detail = SWT.TRAVERSE_NONE;
				break;
