	private void cleanLegacyQuickAccessContribution() {
		for (String quickAccessElementId : QUICK_ACCESS_ELEMENT_IDS) {
			MToolControl legacyElement = (MToolControl) modelService.find(quickAccessElementId,
					model);
			if (legacyElement != null) {
				EcoreUtil.remove((EObject) legacyElement);
			}
		}
	}

	private void positionQuickAccess() {
		for (String quickAccessElementId : QUICK_ACCESS_ELEMENT_IDS) {
			MToolControl quickAccessElement = (MToolControl) modelService.find(
					quickAccessElementId, model);
			if (quickAccessElement != null) {
				moveControl(quickAccessElement.getParent(), quickAccessElement);
			}
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
					WorkbenchPlugin.log("Can't position control '" + element.getElementId() //$NON-NLS-1$
							+ "' because of the unknown position type '" //$NON-NLS-1$
							+ positionInfo.getPosition() + "'!"); //$NON-NLS-1$
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
					return PositionInfo.parse(tag.substring(MOVE_TAG.length()));
				}
			}
		}

		return null;
	}

