	@Override
	protected Object[] internalFilter(Viewer viewer, Object parent, Object[] elements) {
		int size = elements.length;
		ArrayList<Object> out = new ArrayList<Object>(size);
		for (int i = 0; i < size; ++i) {
			Object element = elements[i];
			if (element instanceof WizardCollectionElement) {
				WizardCollectionElement wcElem = filterWizardCollectionElement(viewer,
						(WizardCollectionElement) element);
				if (wcElem.getWizardAdaptableList().size() > 0) {
					out.add(wcElem);
				}
			} else if (select(viewer, parent, element)) {
				out.add(element);
			}
		}
		return out.toArray();
	}

	private WizardCollectionElement filterWizardCollectionElement(Viewer viewer,
			WizardCollectionElement inputCollection) {
		WizardCollectionElement modifiedCollection = null;
		for (Object child : inputCollection.getWizardAdaptableList().getChildren()) {
			if (!isLeafMatch(viewer, child)) {
				if (modifiedCollection == null) {
					modifiedCollection = (WizardCollectionElement) inputCollection.clone();
				}
				modifiedCollection.getWizardAdaptableList().remove((IAdaptable) child);
			}
		}

		return modifiedCollection != null ? modifiedCollection : inputCollection;
	}
