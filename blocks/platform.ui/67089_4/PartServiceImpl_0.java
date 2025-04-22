			if (!element.isVisible()) {
				return false;
			}
			if (isMinimized(parent) || isMinimized(element)) {
				return false;
			}
			return true;
