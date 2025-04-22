			if (searchRoot instanceof MWindow ) {
				if((searchFlags & IN_SHARED_ELEMENTS) != 0) {
					List<MUIElement> sharedElements = ((MWindow) searchRoot).getSharedElements();
					for (MUIElement muiElement : sharedElements) {
						findElementsRecursive(muiElement, clazz, matcher, elements, searchFlags);
					}
				}

				if( (searchFlags & OUTSIDE_PERSPECTIVE) == 0
