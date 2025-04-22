	protected void updateSelection(SelectionChangedEvent event) {
		ArrayList<MPartDescriptor> descs = new ArrayList<MPartDescriptor>();
		IStructuredSelection sel = (IStructuredSelection) event.getSelection();
		for (Iterator<?> i = sel.iterator(); i.hasNext();) {
			Object o = i.next();
			if (o instanceof MPartDescriptor) {
				descs.add((MPartDescriptor) o);
