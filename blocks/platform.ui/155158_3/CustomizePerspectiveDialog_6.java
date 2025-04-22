
			DisplayItem menuEntry = new DisplayItem(text, contributionItem);
			menuEntry.setImageDescriptor(getIconDescriptor(menuItem));
			menuEntry.setActionSet(idToActionSet.get(getActionSetID(menuItem)));
			menuEntry.setCheckState(getMenuItemIsVisible(menuEntry));
			parent.addChild(menuEntry);
		}
		return null;
	}

	private ImageDescriptor getIconDescriptor(MUILabel item) {
		String iconURI = item.getIconURI();
		if (iconURI != null && iconURI.length() > 0) {
			return resUtils.imageDescriptorFromURI(URI.createURI(iconURI));
