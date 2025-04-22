	@Override
	public Object[] filter(Viewer viewer, Object parent, Object[] elements) {
		ArrayList<Object> result = new ArrayList<Object>();

		for (Object elem : super.filter(viewer, parent, elements)) {
			if (elem instanceof WizardCollectionElement) {
				Object wizardCollection = WizardCollectionElementFilter.filter(viewer, this,
						(WizardCollectionElement) elem);
				if (wizardCollection != null) {
					result.add(wizardCollection);
				}
			} else {
				result.add(elem);
			}
		}

		return result.toArray();
	}
