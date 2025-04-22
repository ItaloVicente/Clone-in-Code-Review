			MElementContainer<?> searchContainer = (MElementContainer<?>) searchRoot;
			MPerspectiveStack primaryStack = null;
			if (searchRoot instanceof MWindow && (searchFlags & OUTSIDE_PERSPECTIVE) == 0
					&& (primaryStack = getPrimaryPerspectiveStack((MWindow) searchRoot)) != null) {
				searchContainer = primaryStack;
			}
			if (searchContainer instanceof MPerspectiveStack) {
