			if (e.detail == SWT.TRAVERSE_TAB_NEXT)
				e.doit = advance(true);
			else if (e.detail == SWT.TRAVERSE_TAB_PREVIOUS)
				e.doit = advance(false);
			else if (e.detail != SWT.TRAVERSE_RETURN)
				e.doit = true;
