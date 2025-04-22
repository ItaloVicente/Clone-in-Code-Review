
			DisplayItem menuEntry = new DisplayItem(text, contributionItem);
			menuEntry.setImageDescriptor(getIconDescriptor(menuItem));
			menuEntry.setActionSet(idToActionSet.get(getActionSetID(menuItem)));
			menuEntry.setCheckState(getMenuItemIsVisible(menuEntry));
			parent.addChild(menuEntry);
