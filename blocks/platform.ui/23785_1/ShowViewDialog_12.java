	protected void updateSelection(SelectionChangedEvent event) {
		ArrayList descs = new ArrayList();
		IStructuredSelection sel = (IStructuredSelection) event.getSelection();
		for (Iterator i = sel.iterator(); i.hasNext();) {
			Object o = i.next();
			if (o instanceof MPartDescriptor) {
				descs.add(o);
