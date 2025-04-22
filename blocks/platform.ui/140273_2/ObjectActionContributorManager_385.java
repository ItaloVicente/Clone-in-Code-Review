		List elements = null;
		if (selection instanceof IStructuredSelection) {
			elements = ((IStructuredSelection) selection).toList();
		} else {
			elements = new ArrayList(1);
			elements.add(selection);
		}
