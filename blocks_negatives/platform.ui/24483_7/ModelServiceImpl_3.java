		if (searchRoot instanceof MElementContainer<?>) {
			if (searchRoot instanceof MPerspectiveStack) {
				if ((searchFlags & IN_ANY_PERSPECTIVE ) != 0) {
					MElementContainer<MUIElement> container = (MElementContainer<MUIElement>) searchRoot;
					for (MUIElement child : container.getChildren()) {
						findElementsRecursive(child, clazz, matcher, elements, searchFlags);
