				boolean isSelected = parent.getSelectedElement() == element;
				if (!isSelected) {
					return false;
				}
				if (parent.getTags().contains(IPresentationEngine.MINIMIZED)) {
					return false;
				}
				return true;
