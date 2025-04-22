
							MElementContainer<?> lclContainer = container;
							if (lclContainer.getChildren().size() == 0) {
								MElementContainer<MUIElement> parent = container.getParent();
								if (parent != null && !lastStack) {
									container.setToBeRendered(false);
									parent.getChildren().remove(container);
								} else if (container instanceof MWindow) {
									MUIElement eParent = (MUIElement) ((EObject) container)
											.eContainer();
									if (eParent instanceof MPerspective) {
										((MPerspective) eParent).getWindows().remove(container);
									} else if (eParent instanceof MWindow) {
										((MWindow) eParent).getWindows().remove(container);
