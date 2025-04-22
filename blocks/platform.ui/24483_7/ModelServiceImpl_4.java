				}
			}
			if ((searchFlags & IN_ANY_PERSPECTIVE) != 0) {
				el.addAll(findElementsByClass(searchRoot, MPerspective.class));
			} else if ((searchFlags & IN_ACTIVE_PERSPECTIVE) != 0) {
				for (MApplicationElement e : findElementsByClass(searchRoot, MPerspectiveStack.class)) {
					MPerspective active = ((MPerspectiveStack) e).getSelectedElement();
