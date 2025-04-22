					for (MUIElement child : children) {
						if (child != toBeRemoved && child.isToBeRendered()) {
							parent.setSelectedElement(child);
							break;
						}
					}
