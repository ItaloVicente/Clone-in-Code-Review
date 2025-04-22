			if (searchFlags == ANYWHERE) {

				if (menu != null) {
					findElementsRecursive(menu, clazz, matcher, elements, searchFlags);
				}
				if (MHandler.class.equals(clazz)) {
					for (MHandler child : window.getHandlers()) {
						findElementsRecursive(child, clazz, matcher, elements, searchFlags);
					}
