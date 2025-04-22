
	@Override
	public Object[] filter(Viewer viewer, Object parent, Object[] elements) {
		int size = elements.length;
		ArrayList<Object> out = new ArrayList<Object>(size);

		for (int i = 0; i < size; ++i) {
			Object element = elements[i];
			if (element instanceof WizardCollectionElement) {
				Object wizardCollection = WizardCollectionElementFilter.filter(viewer, this,
						(WizardCollectionElement) element);
				if (wizardCollection != null) {
					out.add(wizardCollection);
				}
			} else if (select(viewer, parent, element)) {
				out.add(element);
			}
		}
		return out.toArray();
	}
