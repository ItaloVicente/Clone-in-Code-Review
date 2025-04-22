		this.addTraverseListener(new TraverseListener() {

			@Override
			public void keyTraversed(TraverseEvent e) {
				if (e.detail == SWT.TRAVERSE_ARROW_PREVIOUS
					|| e.detail == SWT.TRAVERSE_ARROW_NEXT) {
					int nMax = elements.length - 1;
					int nCurrent = getSelectionIndex();
					if (e.detail == SWT.TRAVERSE_ARROW_PREVIOUS) {
						nCurrent -= 1;
						nCurrent = Math.max(0, nCurrent);
					} else if (e.detail == SWT.TRAVERSE_ARROW_NEXT) {
						nCurrent += 1;
						nCurrent = Math.min(nCurrent, nMax);
					}
					select(nCurrent);
					redraw();
				} else {
					e.doit = true;
