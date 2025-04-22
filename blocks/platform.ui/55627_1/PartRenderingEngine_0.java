			if (parent instanceof MElementContainer<?>) {
				@SuppressWarnings("unchecked")
				MElementContainer<MUIElement> container = (MElementContainer<MUIElement>) parent;
				if (container.getSelectedElement() == changedElement) {
					container.setSelectedElement(null);
				}
			}
