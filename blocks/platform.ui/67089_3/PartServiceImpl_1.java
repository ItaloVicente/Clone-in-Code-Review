			if (!element.isVisible()) {
				return false;
			}
			if (isMinimized(parent) || isMinimized(part)) {
				return false;
			}
			return true;
