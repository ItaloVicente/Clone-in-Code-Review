		addPaintListener(e -> paint(e));
		addListener(SWT.Traverse, e -> {
			switch (e.detail) {
			case SWT.TRAVERSE_PAGE_NEXT:
			case SWT.TRAVERSE_PAGE_PREVIOUS:
			case SWT.TRAVERSE_ARROW_NEXT:
			case SWT.TRAVERSE_ARROW_PREVIOUS:
			case SWT.TRAVERSE_RETURN:
				e.doit = false;
				return;
