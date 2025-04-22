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
								}
							}
						} else if (container.getChildren().size() == 1
								&& container instanceof MPartSashContainer) {
							MUIElement theChild = container.getChildren().get(0);
							MElementContainer<MUIElement> parentContainer = container.getParent();
							if (parentContainer != null) {
								int index = parentContainer.getChildren().indexOf(container);

								if (theChild instanceof MPartSashContainer) {
									if (container.getWidget() instanceof Composite) {
										Composite theComp = (Composite) container.getWidget();
										Object tmp = theChild.getWidget();
										theChild.setWidget(theComp);
										theComp.setLayout(new SashLayout(theComp, theChild));
										theComp.setData(AbstractPartRenderer.OWNING_ME, theChild);
										container.setWidget(tmp);
									}
								}

								theChild.setContainerData(container.getContainerData());
								container.getChildren().remove(theChild);
								parentContainer.getChildren().add(index, theChild);
								container.setToBeRendered(false);
								parentContainer.getChildren().remove(container);
