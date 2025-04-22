
			if (searchFlags == ANYWHERE) {
				for (MHandler child : window.getHandlers()) {
					findElementsRecursive(child, clazz, matcher, elements, searchFlags);
				}
			}
