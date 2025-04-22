			if (persp != null) {
				MPlaceholder eaPlaceholder = (MPlaceholder) modelService
						.find(ID_EDITOR_AREA, persp);
				if (element != eaPlaceholder && eaPlaceholder != null
						&& eaPlaceholder.getWidget() != null && eaPlaceholder.isVisible()) {
					elementsToMinimize.add(eaPlaceholder);
				}
			}
