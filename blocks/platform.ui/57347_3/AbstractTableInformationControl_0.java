		fShell.addTraverseListener(e -> {
			switch (e.detail) {
			case SWT.TRAVERSE_PAGE_NEXT:
				e.detail = SWT.TRAVERSE_NONE;
				e.doit = true;
				{
					int n1 = table.getItemCount();
					if (n1 == 0)
						return;

					int i1 = table.getSelectionIndex() + 1;
					if (i1 >= n1)
						i1 = 0;
					table.setSelection(i1);
				}
				break;

			case SWT.TRAVERSE_PAGE_PREVIOUS:
				e.detail = SWT.TRAVERSE_NONE;
				e.doit = true;
				{
					int n2 = table.getItemCount();
					if (n2 == 0)
						return;

					int i2 = table.getSelectionIndex() - 1;
					if (i2 < 0)
						i2 = n2 - 1;
					table.setSelection(i2);
