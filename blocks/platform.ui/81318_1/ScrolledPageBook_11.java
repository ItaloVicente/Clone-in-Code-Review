		this.addListener(SWT.Traverse, e -> {
			switch (e.detail) {
			case SWT.TRAVERSE_ESCAPE:
			case SWT.TRAVERSE_RETURN:
			case SWT.TRAVERSE_TAB_NEXT:
			case SWT.TRAVERSE_TAB_PREVIOUS:
				e.doit = true;
				break;
