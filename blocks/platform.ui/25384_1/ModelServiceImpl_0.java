				} else {
					MElementContainer<MUIElement> container = (MElementContainer<MUIElement>) searchRoot;
					for (MUIElement child : container.getChildren()) {
						findElementsRecursive(child, clazz, matcher, elements, searchFlags);
					}
				}
