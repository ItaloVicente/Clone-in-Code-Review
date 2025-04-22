				boolean isSelected = parent.getSelectedElement() == element;
				if (!isSelected) {
					return false;
				}
				if (isMinimized(parent) || isMinimized(part)) {
					return false;
				}
				return true;
