				} else if (container.getChildren().size() == 1 && container instanceof MPartSashContainer) {
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
