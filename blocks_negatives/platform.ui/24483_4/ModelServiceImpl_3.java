		if (searchRoot instanceof MElementContainer<?>) {
			if (searchRoot instanceof MPerspectiveStack) {
				if ((searchFlags & IN_ANY_PERSPECTIVE ) != 0) {
					MElementContainer<MUIElement> container = (MElementContainer<MUIElement>) searchRoot;
					for (MUIElement child : container.getChildren()) {
						findElementsRecursive(child, clazz, matcher, elements, searchFlags);
					}
				} else if ((searchFlags & IN_ACTIVE_PERSPECTIVE) != 0) {
					MPerspective active = ((MPerspectiveStack) searchRoot).getSelectedElement();
					if (active != null) {
						findElementsRecursive(active, clazz, matcher, elements, searchFlags);
					}
				} else if ((searchFlags & IN_SHARED_AREA) != 0 && searchRoot instanceof MUIElement) {
					List<MArea> areas = findElements((MUIElement) searchRoot, null, MArea.class,null);
					for (MArea area : areas) {
						findElementsRecursive(area, clazz, matcher, elements, searchFlags);
					}
				} else if ((searchFlags & IN_PART) != 0) {
					 List<MPart> parts = findElements((MUIElement) searchRoot, null, MPart.class, null);
					 for (MPart part : parts) {
						for (MHandler handler : part.getHandlers()) {
							findElementsRecursive(handler, clazz, matcher, elements, searchFlags);
						}
					 }
				 }
			} else {
				MElementContainer<MUIElement> container = (MElementContainer<MUIElement>) searchRoot;
				for (MUIElement child : container.getChildren()) {
					findElementsRecursive(child, clazz, matcher, elements, searchFlags);
