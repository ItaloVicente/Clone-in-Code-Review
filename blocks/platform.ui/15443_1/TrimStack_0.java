			} else if (minimizedElement instanceof MPlaceholder
					&& ((MPlaceholder) minimizedElement).getRef() instanceof MArea) {
				MArea area = (MArea) ((MPlaceholder) minimizedElement).getRef();

				MPart partToActivate = null;
				MElementContainer<MPartSashContainerElement> curContainer = area;
				while (partToActivate == null && curContainer.getSelectedElement() != null) {
					if (curContainer.getSelectedElement() instanceof MPart) {
						partToActivate = (MPart) curContainer.getSelectedElement();
					} else if (curContainer.getSelectedElement() instanceof MPlaceholder) {
						MPlaceholder ph = (MPlaceholder) curContainer.getSelectedElement();
						if (ph.getRef() instanceof MPart) {
							partToActivate = (MPart) ph.getRef();
						}
					} else if (curContainer.getSelectedElement() instanceof MElementContainer<?>) {
						curContainer = (MElementContainer<MPartSashContainerElement>) curContainer
								.getSelectedElement();
					}
				}

				if (partToActivate == null) {
					List<MPart> parts = modelService.findElements(area, null, MPart.class, null);
					if (parts.size() > 0)
						partToActivate = parts.get(0);
				}

				if (partToActivate != null) {
					partService.activate(partToActivate);
				}
