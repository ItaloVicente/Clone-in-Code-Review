			} else if ((searchFlags & IN_ANY_PERSPECTIVE) != 0) {
				el.addAll(findElementByClass(searchRoot, MPerspective.class));
			} else if ((searchFlags & IN_ACTIVE_PERSPECTIVE) != 0) {
				for (MApplicationElement e : findElementByClass(searchRoot, MPerspectiveStack.class)) {
					MPerspective active = ((MPerspectiveStack) e).getSelectedElement();
					if (active != null) {
						el.add(active);
						break;
					}
				}
			} else if ((searchFlags & IN_SHARED_AREA) != 0) {
				el.addAll(findElementByClass(searchRoot, MArea.class));
			} else if ((searchFlags & IN_PART) != 0) {
				el.addAll(findElementByClass(searchRoot, MPart.class));
