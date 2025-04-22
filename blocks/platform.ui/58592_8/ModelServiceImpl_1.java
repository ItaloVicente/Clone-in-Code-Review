	private void replacePlaceholder(MPlaceholder ph) {
		MPart part = createModelElement(MPart.class);
		part.setElementId(ph.getElementId());
		part.setIconURI("platform:/plugin/" + ph.getElementId() + "/icons/default.gif"); //$NON-NLS-1$ //$NON-NLS-2$
		String label = (String) ph.getTransientData().get(TAG_LABEL);
		if (label != null) {
			part.setLabel(label);
		} else {
			part.setLabel(getLabel(ph.getElementId()));
		}
		part.setContributionURI(COMPATIBILITY_VIEW_URI);
		part.setCloseable(true);
		MElementContainer<MUIElement> curParent = ph.getParent();
		int curIndex = curParent.getChildren().indexOf(ph);
		curParent.getChildren().remove(curIndex);
		curParent.getChildren().add(curIndex, part);
		if (curParent.getSelectedElement() == ph) {
			curParent.setSelectedElement(part);
		}
	}

	private String getLabel(String str) {
		int index = str.lastIndexOf('.');
		if (index == -1)
			return str;
		return str.substring(index + 1);
	}

