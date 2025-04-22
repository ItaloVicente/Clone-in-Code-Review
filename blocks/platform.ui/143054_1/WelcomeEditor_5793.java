		styledText.addTraverseListener(e -> {
			StyledText text = (StyledText) e.widget;

			switch (e.detail) {
			case SWT.TRAVERSE_ESCAPE:
				e.doit = true;
				break;
			case SWT.TRAVERSE_TAB_NEXT:
				if ((e.stateMask & SWT.CTRL) != 0) {
					if (e.widget == lastText) {
