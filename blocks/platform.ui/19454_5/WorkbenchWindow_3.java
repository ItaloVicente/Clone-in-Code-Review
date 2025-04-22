	private void positionQuickAccess() {
		MToolControl spacerGlueControl = (MToolControl) modelService.find("Spacer Glue", model); //$NON-NLS-1$
		if (spacerGlueControl != null) {
			moveControl(spacerGlueControl.getParent(), spacerGlueControl);
		}

		MToolControl searchControl = (MToolControl) modelService.find("SearchField", model); //$NON-NLS-1$
		if (searchControl != null) {
			moveControl(searchControl.getParent(), searchControl);
		}

		MToolControl glueControl = (MToolControl) modelService.find("Search-PS Glue", model); //$NON-NLS-1$
		if (glueControl != null) {
			moveControl(glueControl.getParent(), glueControl);
		}

	}

	private void moveControl(MElementContainer<MUIElement> elementContainer, MUIElement element) {
		if (element == null || elementContainer == null)
			return;

		PositionInfo positionInfo = findMovePositionInfo(element);

		if (positionInfo != null) {
			List<MUIElement> elements = elementContainer.getChildren();

			if (elements.remove(element)) {

				switch (positionInfo.getPosition()) {
				case LAST:
					elements.add(element);
					break;

				case FIRST:
					elements.add(0, element);
					break;

				case INDEX:
					int index = positionInfo.getPositionReferenceAsInteger();
					if (index >= 0 && index < elements.size()) {
						elements.add(index, element);
					} else {
						elements.add(element);
					}
					break;

				case BEFORE:
				case AFTER:
					int idx = indexOfElementWithID(elements, positionInfo.getPositionReference());
					if (idx < 0) {
						elements.add(element);
					} else {
						if (positionInfo.getPosition() == Position.AFTER) {
							idx++;
						}

						if (idx < elements.size()) {
							elements.add(idx, element);
						} else {
							elements.add(element);
						}
					}
					break;

				default:
					throw new IllegalArgumentException(
							"Unknown position: " + positionInfo.getPosition()); //$NON-NLS-1$
				}
			}
		}
	}

	private int indexOfElementWithID(List<MUIElement> elements, String id) {
		if (elements == null || id == null)
			return -1;

		int index = 0;
		for (MUIElement element : elements) {
			if (id.equals(element.getElementId())) {
				return index;
			}
			index++;
		}

		return -1;
	}

	private PositionInfo findMovePositionInfo(MUIElement element) {
		if (element != null) {
			for (String tag : element.getTags()) {
				if (tag.startsWith(MOVE_TAG)) {
					return PositionInfo.parse(tag.substring(5));
				}
			}
		}

		return null;
	}

