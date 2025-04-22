			if ((modelService.getElementLocation(newActivePart) & EModelService.IN_SHARED_AREA) != 0) {
				MElementContainer<MUIElement> parent = newActivePart.getParent();
				MUIElement selectedElement = parent != null ? parent.getSelectedElement() : null;
				if (selectedElement == null) {
					newActivePart = null;
				} else if (selectedElement != newActivePart) {
					if (selectedElement instanceof MPart) {
						newActivePart = (MPart) selectedElement;
					} else if (selectedElement instanceof MPlaceholder) {
						MUIElement ref = ((MPlaceholder) selectedElement).getRef();
						if (ref instanceof MPart) {
							newActivePart = (MPart) ref;
						} else {
							newActivePart = null;
						}
