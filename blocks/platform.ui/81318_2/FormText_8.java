		Listener listener = e -> {
			switch (e.type) {
			case SWT.FocusIn:
				if (!controlFocusTransfer)
					syncControlSegmentFocus((Control) e.widget);
				break;
			case SWT.Traverse:
				if (DEBUG_FOCUS)
					System.out.println("Control traversal: " + e); //$NON-NLS-1$
				switch (e.detail) {
				case SWT.TRAVERSE_PAGE_NEXT:
				case SWT.TRAVERSE_PAGE_PREVIOUS:
				case SWT.TRAVERSE_ARROW_NEXT:
				case SWT.TRAVERSE_ARROW_PREVIOUS:
					e.doit = false;
					return;
