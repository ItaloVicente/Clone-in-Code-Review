
			if (searchFlags == ANYWHERE) {
				for (MHandler child : window.getHandlers()) {
					findElementsRecursive(child, matcher, elements, searchFlags);
				}
			}
