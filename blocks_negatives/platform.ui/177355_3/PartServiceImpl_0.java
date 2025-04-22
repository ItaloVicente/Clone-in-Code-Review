			if (newActivePart == null) {
				modelService.bringToTop(perspective);
				perspective.getContext().activate();
			} else {
				if ((modelService.getElementLocation(newActivePart) & EModelService.IN_SHARED_AREA) != 0) {
					if (newActivePart.getParent() != null
							&& newActivePart.getParent().getSelectedElement() != newActivePart) {
						newActivePart = (MPart) newActivePart.getParent().getSelectedElement();
