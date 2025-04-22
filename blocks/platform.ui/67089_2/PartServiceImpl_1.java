			if (!element.isVisible()) {
				return false;
			}
			if (parent.getTags().contains(IPresentationEngine.MINIMIZED)) {
				return false;
			}
			return true;
