				} else {
					MElementContainer<MUIElement> parent = changedObj.getParent();
					boolean makeInvisible = true;
					for (MUIElement kid : parent.getChildren()) {
						if (kid.isToBeRendered() && kid.isVisible()) {
							makeInvisible = false;
							break;
						}
					}
					if (makeInvisible)
						parent.setVisible(false);
