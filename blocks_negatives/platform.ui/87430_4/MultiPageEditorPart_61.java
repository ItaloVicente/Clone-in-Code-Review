		newContainer.addTraverseListener(new TraverseListener() {
			@Override
			public void keyTraversed(TraverseEvent e) {
				switch (e.detail) {
					case SWT.TRAVERSE_PAGE_NEXT:
					case SWT.TRAVERSE_PAGE_PREVIOUS:
						int detail = e.detail;
						e.doit = true;
						e.detail = SWT.TRAVERSE_NONE;
						Control control = newContainer.getParent();
						do {
							if (control.traverse(detail))
								return;
							if (control.getListeners(SWT.Traverse).length != 0)
								return;
							if (control instanceof Shell)
								return;
							control = control.getParent();
						} while (control != null);
				}
