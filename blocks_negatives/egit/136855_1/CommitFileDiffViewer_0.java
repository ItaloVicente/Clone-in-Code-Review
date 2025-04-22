		final IStructuredContentProvider cp;
		final Object in = getInput();
		if (in == null)
			return;

		cp = ((IStructuredContentProvider) getContentProvider());
		final Object[] el = cp.getElements(in);
		if (el == null || el.length == 0)
			return;
		setSelection(new StructuredSelection(el));
