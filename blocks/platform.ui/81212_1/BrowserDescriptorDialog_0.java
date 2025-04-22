			text.addTraverseListener(event -> {
				switch (event.detail) {
				case SWT.TRAVERSE_RETURN:
				case SWT.TRAVERSE_TAB_NEXT:
				case SWT.TRAVERSE_TAB_PREVIOUS:
					event.doit = true;
					break;
