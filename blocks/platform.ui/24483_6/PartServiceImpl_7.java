
		ElementMatcher matcher = new ElementMatcher(null, element.getClass(), (String) null);

		for (MWindowElement wElement : workbenchWindow.getChildren()) {
			if (modelService.findElements(wElement, element.getClass(), EModelService.PRESENTATION,
					matcher).contains(element)) {
				return true;
			}
		}

		for (MWindow wElement : workbenchWindow.getWindows()) {
			if (modelService.findElements(wElement, element.getClass(), EModelService.PRESENTATION,
					matcher).contains(element)) {
				return true;
			}
		}

		return false;
