				iconDescriptor = manager.getImageDescriptor();
			} else {
				menuEntry = new DisplayItem(menuItem.getLocalizedLabel(), manager);
				iconDescriptor = getIconDescriptor(menuItem);
				parent.addChild(menuEntry);
			}
