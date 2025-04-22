	private <T extends MUIElement> List<T> findValidElementsToMinimize(
			MUIElement elementToMaximize, MWindow currentWindow, MUIElement searchRoot, String id,
			Class<T> clazz, int searchFlag, boolean allowSharedArea) {
		List<T> elementsToMinimize = new ArrayList<T>();
		List<T> elements = modelService.findElements(searchRoot, id, clazz, null, searchFlag);
		for (T element : elements) {
			if (element == elementToMaximize || !element.isToBeRendered())
				continue;

			if (getWindowFor(element) != currentWindow)
				continue;

			int loc = modelService.getElementLocation(element);
			boolean inSharedArea = loc == EModelService.IN_SHARED_AREA;
			boolean validLocation = allowSharedArea || !inSharedArea;
			if (validLocation && element.getWidget() != null && element.isVisible()
					&& !element.getTags().contains(MINIMIZED)) {
				elementsToMinimize.add(element);
			}
		}
		return elementsToMinimize;
	}

