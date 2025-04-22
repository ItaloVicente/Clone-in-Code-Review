		if ("gtk".equals(SWT.getPlatform())) { //$NON-NLS-1$
			viewer.getControl().getDisplay().asyncExec(
					() -> viewer.editElement(sel.getFirstElement(), 0));
		} else {
			viewer.editElement(sel.getFirstElement(), 0);
		}
