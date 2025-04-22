				if (menu != null) {
					findElementsRecursive(menu, clazz, matcher, elements, searchFlags);
				}
				
				for (MHandler child : window.getHandlers()) {
					findElementsRecursive(child, clazz, matcher, elements, searchFlags);
				}
			}
		}
