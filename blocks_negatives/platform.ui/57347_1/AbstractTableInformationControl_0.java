		fShell.addTraverseListener(new TraverseListener() {
			@Override
			public void keyTraversed(TraverseEvent e) {
				switch (e.detail) {
				case SWT.TRAVERSE_PAGE_NEXT:
					e.detail = SWT.TRAVERSE_NONE;
					e.doit = true;
					{
						int n = table.getItemCount();
						if (n == 0)
							return;

						int i = table.getSelectionIndex() + 1;
						if (i >= n)
							i = 0;
						table.setSelection(i);
					}
					break;

				case SWT.TRAVERSE_PAGE_PREVIOUS:
					e.detail = SWT.TRAVERSE_NONE;
					e.doit = true;
					{
						int n = table.getItemCount();
						if (n == 0)
							return;

						int i = table.getSelectionIndex() - 1;
						if (i < 0)
							i = n - 1;
						table.setSelection(i);
					}
					break;
