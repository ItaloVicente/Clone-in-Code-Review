						for (int j = 0; j < mel.size(); j++) {
							MMenuElement menuElement = mel.get(j);
							if (menuElement.getElementId() == null
									|| menuElement.getElementId().length() < 1) {
								menuElement.setElementId(currentMenuElement
										.getElementId() + "." + j); //$NON-NLS-1$
							}
							menuModel.getChildren()
									.add(position++, menuElement);
							renderer.modelProcessSwitch(menuManager,
									menuElement);
