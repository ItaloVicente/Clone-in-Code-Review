				if (selectedPart != null && selectedPart.isToBeRendered()
						&& getParts().contains(selectedPart)) {
					if (placeholder == null) {
						if (selectedPart.getParent().getRenderer() != null) {
							engine.createGui(selectedPart);
							firePartVisible(selectedPart);
							firePartBroughtToTop(selectedPart);
						}
					} else if (placeholder.getParent().getRenderer() != null) {
						engine.createGui(placeholder);
