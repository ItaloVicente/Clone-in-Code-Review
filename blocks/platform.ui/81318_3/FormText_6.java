		addListener(SWT.Traverse, e -> {
			if (DEBUG_FOCUS)
				System.out.println("Traversal: " + e); //$NON-NLS-1$
			switch (e.detail) {
			case SWT.TRAVERSE_PAGE_NEXT:
			case SWT.TRAVERSE_PAGE_PREVIOUS:
			case SWT.TRAVERSE_ARROW_NEXT:
			case SWT.TRAVERSE_ARROW_PREVIOUS:
				e.doit = false;
				return;
