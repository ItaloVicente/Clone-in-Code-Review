			int detail = event.detail;
			if (detail == SWT.TRAVERSE_ARROW_NEXT) {
				indexListener.selectNext();
			}
			if (detail == SWT.TRAVERSE_ARROW_PREVIOUS) {
				indexListener.selectPrevious();
			}

