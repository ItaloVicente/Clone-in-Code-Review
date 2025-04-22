							createGui((MUIElement) element, parent,
									parentContext);

							if (parent instanceof MPartStack) {
								MPartStack stack = (MPartStack) parent;
								List<MStackElement> children = stack
										.getChildren();
								MStackElement stackElement = (MStackElement) element;
								if (children.size() == 1
										&& stackElement.isVisible()
										&& stackElement.isToBeRendered()) {
									stack.setSelectedElement(stackElement);
								}
